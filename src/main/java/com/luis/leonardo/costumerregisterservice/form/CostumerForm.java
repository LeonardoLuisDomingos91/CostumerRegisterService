package com.luis.leonardo.costumerregisterservice.form;

import com.luis.leonardo.costumerregisterservice.entity.Costumer;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CostumerForm {

    private Long id;

    // Tal anotação diz que para o que foi recebido na request ser válido, o atributo nao deve ser
    // Nulo nem vazio
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String cpf;

    private String extraInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }

    public Costumer convertFormIntoCostumer(){
        Costumer convertedCostumer = new Costumer();

        convertedCostumer.setId(this.id);
        convertedCostumer.setName(this.name);
        convertedCostumer.setLastName(this.lastName);
        convertedCostumer.setCpf(this.cpf);
        convertedCostumer.setExtraInformation(this.extraInformation);

        return convertedCostumer;
    }

}
