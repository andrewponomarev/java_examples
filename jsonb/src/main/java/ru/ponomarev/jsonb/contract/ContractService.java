package ru.ponomarev.jsonb.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;

//    public Contract createContractWithStringParam(String s) {
//        Contract contract = new Contract();
//        contract.setNamedStringParam(s);
//        return repository.save(contract);
//    }

//    @Transactional
//    public Contract longUpdateStringParam(String id, String newValue) {
//        Contract contract = get(id);
//        try {
//            System.out.println("sleep ");
//            Thread.sleep(5000);
//            System.out.println("awake ");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        contract.setNamedStringParam(newValue);
//        return repository.save(contract);
//    }
//
//    @Transactional
//    public Contract updateStringParam(String id, String newValue) {
//        Contract contract = get(id);
//        contract.setNamedStringParam(newValue);
//        return repository.save(contract);
//    }
//
//    @Transactional
//    public Contract updateContractValue(String id, String newValue) {
//        Contract contract = get(id);
//        contract.setValue(newValue);
//        return repository.save(contract);
//    }
//
//    @Transactional
//    public Contract longUpdateContractValue(String id, String newValue) {
//        Contract contract = get(id);
//        try {
//            System.out.println("sleep ");
//            Thread.sleep(5000);
//            System.out.println("awake ");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        contract.setValue(newValue);
//        return repository.save(contract);
//    }
//
//    public Contract get(String id) {
//        return repository.get(id);
//    }
}
