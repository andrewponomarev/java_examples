package ru.ponomarev.jsonb.contract2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class Contract2Repository {

    private final CrudContract2Repository crudContractRepository;

    Contract save(Contract contract) {
        return crudContractRepository.save(contract);
    }

    Contract get(String id) {
        //return crudContractRepository.findById(id).get();
        return crudContractRepository.findOneForUpdate(id);
    }

    Contract saveAndFlush(Contract contract) {
        return crudContractRepository.saveAndFlush(contract);
    }

}
