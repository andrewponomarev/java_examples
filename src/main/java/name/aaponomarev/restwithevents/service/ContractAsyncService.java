package name.aaponomarev.restwithevents.service;

import lombok.RequiredArgsConstructor;
import name.aaponomarev.restwithevents.domain.Contract;
import name.aaponomarev.restwithevents.domain.ContractDaoService;
import name.aaponomarev.restwithevents.domain.Status;
import name.aaponomarev.restwithevents.events.ContractEventPublisher;
import name.aaponomarev.restwithevents.events.domain.DataCompletedEvent;
import name.aaponomarev.restwithevents.events.domain.FinalContractEvent;
import name.aaponomarev.restwithevents.events.domain.PartnerContractEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@RequiredArgsConstructor
public class ContractAsyncService {

    private final ContractDaoService contractDaoService;
    private final ContractEventPublisher contractEventPublisher;

    @Async
    public void makeSomeLongAsyncJob(Long id) {
        mockRequestToQ5f();
        contractDaoService.updateStatus(id);
        contractEventPublisher.publishFinalContractEvent(id);
        return;
    }

    @Async
    @EventListener({FinalContractEvent.class, DataCompletedEvent.class})
    @Transactional
    public void makeFinalChanges(PartnerContractEvent partnerContractEvent) {
        Contract contract = contractDaoService.getOne(partnerContractEvent.getId());
        if (contract.getStatus() == Status.PENDING && contract.getDetails() != null) {
            contract.setNum(contract.getNum() + 1);
            mockRequestToQ5f();
            contract.setStatus(Status.FINAL);
            contractDaoService.save(contract);
        }
    }

    private void mockRequestToQ5f() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
