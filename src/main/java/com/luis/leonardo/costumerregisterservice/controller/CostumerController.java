package com.luis.leonardo.costumerregisterservice.controller;



import com.luis.leonardo.costumerregisterservice.dto.CostumerDTO;
import com.luis.leonardo.costumerregisterservice.entity.Costumer;
import com.luis.leonardo.costumerregisterservice.exception.CostumerAlreadyExistsException;
import com.luis.leonardo.costumerregisterservice.form.CostumerForm;
import com.luis.leonardo.costumerregisterservice.security.AuthenticationInterceptor;
import com.luis.leonardo.costumerregisterservice.service.CostumerService;
import com.luis.leonardo.costumerregisterservice.util.AuthenticatedUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/costumer")
public class CostumerController {

    @Autowired
    CostumerService service;

    // Retorna todos os clientes da base de dados
    @GetMapping
    public List<CostumerDTO> findCostumers(String nome, String sobrenome){
        return  CostumerDTO.convert(service.findAll());
    }

    //Obtém um cliente por Id
    @RequestMapping(method = RequestMethod.GET, value = "/{costumerId}")
    public ResponseEntity<CostumerDTO> findById(@PathVariable final long costumerId){

        // Obtendo o cliente com tal ID na base de dados
        Optional<Costumer> costumer = service.findById(costumerId);

        // Verificando se tal cliente foi encontrado e caso sim, retornando o mesmo
        if(costumer.isPresent()){
            CostumerDTO costumerDTO = new CostumerDTO(costumer.get());
            return ResponseEntity.ok(costumerDTO);
        }

        // Caso não tenha sido encontrado, retorna-se um status 404
        return ResponseEntity.notFound().build();
    }


    // Persiste um novo cliente na base de dados
    @PostMapping
    public ResponseEntity<CostumerDTO> insert(@RequestBody @Valid CostumerForm form,
                                              UriComponentsBuilder uriBuilder) {
        try{
            Costumer costumer = form.convertFormIntoCostumer();

            // Verifica se já existe um cliente com o CPF na base de dados
            service.verifyCostumerAlreadyExistsByCpf(costumer);

            service.save(costumer);

            URI uri = uriBuilder.path("/costumer/{id}").buildAndExpand(costumer.getId()).toUri();

            return ResponseEntity.created(uri).body(new CostumerDTO(costumer));
        }catch(CostumerAlreadyExistsException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new CostumerDTO(exception.getCostumer()));
        }
    }

    // Eclui fisicamente um cliente da base de dados
    @DeleteMapping("/{costumerId}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("costumerId") Long costumerId){

        // Verifica se o mesmo é administrador pois só o ADMIN pode realizar essa operação
        Boolean isAuthorized =
                AuthenticatedUserUtil.
                        doesUserHaveAuthorizationToAccessTheResource(new String[]{"ADMIN"});


        if(!isAuthorized){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(AuthenticatedUserUtil.buildErrorMessage(new String[]{"ADMIN"}));
        }

        service.delete(costumerId);
        return ResponseEntity.ok(null);
    }

    // Atualiza um cliente
    // A anotação Transactional está aqui ara informar ao Spring Data que algo será alterado na base de dados
    @PutMapping("/{id}")
    @javax.transaction.Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid CostumerForm form, UriComponentsBuilder uriBuilder) {

        if(id == null){
            return ResponseEntity.badRequest().build();
        }

        // Verifica se o mesmo é administrador pois só o mesmo pode realizar essa operação
        Boolean isAuthorized =
                AuthenticatedUserUtil.
                        doesUserHaveAuthorizationToAccessTheResource(new String[]{"ADMIN", "GERENTE"});


        if(!isAuthorized){
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(AuthenticatedUserUtil.buildErrorMessage(new String[]{"ADMIN", "GERENTE"}));
        }

        Costumer costumer = this.service.update(id, form);

        return ResponseEntity.ok(new CostumerDTO(costumer));
    }


}
