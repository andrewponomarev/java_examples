package ru.ponomarev.jsonb.contract2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@RequiredArgsConstructor
public class Contract2Repository {

    private final CrudContract2Repository crudContractRepository;

    Contract save(Contract contract) {
        return crudContractRepository.save(contract);
    }

    @Transactional
    public Contract get(String id) {
        //return crudContractRepository.findById(id).get();
        return crudContractRepository.findOneForUpdate(id);
    }

    public Contract saveAndFlush(Contract contract) {
        return crudContractRepository.saveAndFlush(contract);
    }

}
