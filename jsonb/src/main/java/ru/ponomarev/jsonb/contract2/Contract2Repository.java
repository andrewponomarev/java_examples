package ru.ponomarev.jsonb.contract2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class Contract2Repository {

    private final CrudContract2Repository crudContractRepository;

    Contract2 save(Contract2 contract) {
        return crudContractRepository.save(contract);
    }

    Contract2 get(String id) {
        //return crudContractRepository.findById(id).get();
        return crudContractRepository.findOneForUpdate(id);
    }

    Contract2 saveAndFlush(Contract2 contract) {
        return crudContractRepository.saveAndFlush(contract);
    }

}
