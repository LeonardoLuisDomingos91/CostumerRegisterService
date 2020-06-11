package com.luis.leonardo.costumerregisterservice.dto;





import com.luis.leonardo.costumerregisterservice.entity.Costumer;

import java.util.ArrayList;
import java.util.List;

public class CostumerDTO {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String extraInformation;

    public CostumerDTO(Costumer costumer) {
        this.id = costumer.getId();
        this.name = costumer.getName();
        this.lastName = costumer.getLastName();
        this.cpf = costumer.getCpf();
        this.extraInformation = costumer.getExtraInformation();
    }

    public static List<CostumerDTO> convert(List<Costumer> costumers) {

        List<CostumerDTO> costumersConverted = new ArrayList<CostumerDTO>();

        costumers.forEach(costumer -> {
            CostumerDTO costumerDTO = new CostumerDTO(costumer);
            costumersConverted.add(costumerDTO);
        });

        return costumersConverted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getExtraInformation() {
        return extraInformation;
    }

    public void setExtraInformation(String info) {
        this.extraInformation = info;
    }
}
