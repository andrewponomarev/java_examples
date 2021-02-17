package ru.ponomarev.jsonb.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContractRepository {

    private final CrudContractRepository crudContractRepository;

    Contract save(Contract contract) {
        return crudContractRepository.save(contract);
    }

    Contract get(String id) {
        return crudContractRepository.findById(id).get();
    }

}
