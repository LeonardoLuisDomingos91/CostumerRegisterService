package com.luis.leonardo.costumerregisterservice.repository;


import com.luis.leonardo.costumerregisterservice.entity.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CostumerRepository extends JpaRepository<Costumer, Long> {

    List<Costumer> findByCpf(String cpf);
}
