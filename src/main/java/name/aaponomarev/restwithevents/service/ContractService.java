package name.aaponomarev.restwithevents.service;

import lombok.RequiredArgsConstructor;
import name.aaponomarev.restwithevents.domain.*;
import name.aaponomarev.restwithevents.events.ContractEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractDaoService contractDaoService;
    private final ContractAsyncService contractAsyncService;
    private final ContractEventPublisher contractEventPublisher;

    public Contract create(Contract contract) {
        contract.setStatus(Status.CREATED);
        contract.setNum(0);
        contract = contractDaoService.save(contract);
        contractAsyncService.makeSomeLongAsyncJob(contract.getId());
        return contract;
    }

    public Contract saveDetails(Long id, Details details) {
        Contract contract = contractDaoService.getOne(id);
        contract.setDetails(details.getDetails());
        contractDaoService.save(contract);
        contractEventPublisher.publishDataCompletedEvent(contract.getId());
        //waitForStatus(id);
        return contract;
    }

    public List<Contract> list() {
        return contractDaoService.findAll();
    }

    private void waitForStatus(Long id) {
        Contract contract = contractDaoService.getOne(id);
        while (contract.getStatus().equals(Status.CREATED)) {
            try {
                System.out.println("wait for status");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            contract = contractDaoService.getOne(id);
        }
        makeFinalChanges2(contract);
    }

    public void makeFinalChanges2(Contract contract) {
        contract.setMain("VICTORY");
        contractDaoService.save(contract);
    }

    public Contract update(ContractPart contract) {
        return new Contract();
//        return contractDaoService.save(contract);
    }
}
