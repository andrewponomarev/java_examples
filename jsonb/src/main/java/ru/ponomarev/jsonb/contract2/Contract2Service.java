package ru.ponomarev.jsonb.contract2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class Contract2Service {

    private final Contract2Repository repository;

    public Contract createContractWithStringParam(String s) {
        Contract contract = new Contract();
        contract.setNamedStringParam(s);
        return repository.save(contract);
    }

    @Transactional
    public Contract longUpdateStringParam(String id, String newValue) {
        Contract contract = get(id);
        try {
            System.out.println("sleep ");
            Thread.sleep(10000);
            System.out.println("awake ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contract.setNamedStringParam(newValue);
        return repository.save(contract);
    }

    @Transactional
    public Contract updateStringParam(String id, String newValue) {
        Contract contract = get(id);
        System.out.println("get for param ");
        contract.setNamedStringParam(newValue);
        System.out.println("setStringParam ");
        return repository.save(contract);
    }

    @Transactional
    public Contract updateContractValue(String id, String newValue) {
        Contract contract = get(id);
        contract.setValue(newValue);
        return repository.save(contract);
    }

    @Transactional
    public Contract longUpdateContractValue(String id, String newValue) {
        Contract contract = get(id);
        System.out.println("get for value ");
        try {
            System.out.println("sleep ");
            Thread.sleep(10000);
            System.out.println("awake ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contract.setValue(newValue);
        System.out.println("setValue ");
        return repository.save(contract);
    }

    public Contract get(String id) {
        return repository.get(id);
    }
}
