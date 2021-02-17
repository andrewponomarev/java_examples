package ru.ponomarev.jsonb.contract2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ponomarev.jsonb.contract.ContractRepository;
import ru.ponomarev.jsonb.contract.Contract;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class Contract2Service {

    private final Contract2Repository repository;

    public Contract2 createContractWithStringParam(String s) {
        Contract2 contract = new Contract2();
        contract.setStringParam(s);
        return repository.save(contract);
    }

    @Transactional
    public Contract2 longUpdateStringParam(String id, String newValue) {
        Contract2 contract = get(id);
        try {
            System.out.println("sleep ");
            Thread.sleep(10000);
            System.out.println("awake ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        contract.setStringParam(newValue);
        return repository.save(contract);
    }

    @Transactional
    public Contract2 updateStringParam(String id, String newValue) {
        Contract2 contract = get(id);
        System.out.println("get for param ");
        contract.setStringParam(newValue);
        System.out.println("setStringParam ");
        return repository.save(contract);
    }

    @Transactional
    public Contract2 updateContractValue(String id, String newValue) {
        Contract2 contract = get(id);
        contract.setValue(newValue);
        return repository.save(contract);
    }

    @Transactional
    public Contract2 longUpdateContractValue(String id, String newValue) {
        Contract2 contract = get(id);
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

    public Contract2 get(String id) {
        return repository.get(id);
    }
}
