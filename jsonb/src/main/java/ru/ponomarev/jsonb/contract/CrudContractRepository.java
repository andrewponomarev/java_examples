package ru.ponomarev.jsonb.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ponomarev.jsonb.contract.Contract;

public interface CrudContractRepository extends JpaRepository<Contract, String> {
}
