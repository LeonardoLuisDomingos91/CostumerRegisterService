package com.luis.leonardo.costumerregisterservice.service;

import com.luis.leonardo.costumerregisterservice.entity.Costumer;
import com.luis.leonardo.costumerregisterservice.exception.CostumerAlreadyExistsException;
import com.luis.leonardo.costumerregisterservice.form.CostumerForm;
import com.luis.leonardo.costumerregisterservice.repository.CostumerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CostumerService {

    @Autowired
    CostumerRepository repository;

    public void save(Costumer costumer){
        repository.save(costumer);
    }

    public List<Costumer> findAll(){
        return repository.findAll();
    }

    public Optional<Costumer> findById(long costumerId) {
        return repository.findById(costumerId);
    }

    public void verifyCostumerAlreadyExistsByCpf(Costumer costumer) throws CostumerAlreadyExistsException {
        List<Costumer> costumers = repository.findByCpf(costumer.getCpf());

        if(costumers.size() > 0){
            throw new CostumerAlreadyExistsException(costumers.get(0));
        }
    }

    public void delete(Long costumerId) {
        repository.deleteById(costumerId);
    }

    public Costumer update(Long id, CostumerForm form) {
        Costumer costumer = repository.getOne(id);

        costumer.setName(form.getName());
        costumer.setLastName(form.getLastName());
        costumer.setCpf(form.getCpf());
        costumer.setExtraInformation(form.getExtraInformation());

        return costumer;
    }

}
