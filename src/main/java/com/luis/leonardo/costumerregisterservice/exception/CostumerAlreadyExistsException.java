package com.luis.leonardo.costumerregisterservice.exception;

import com.luis.leonardo.costumerregisterservice.entity.Costumer;

public class CostumerAlreadyExistsException extends Exception {

    private Costumer costumer;

    public CostumerAlreadyExistsException(Costumer costumer) {
        this.costumer = costumer;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }
}
