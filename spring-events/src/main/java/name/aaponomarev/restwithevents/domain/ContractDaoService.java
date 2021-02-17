package name.aaponomarev.restwithevents.domain;

import lombok.RequiredArgsConstructor;
import name.aaponomarev.restwithevents.events.ContractEventPublisher;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractDaoService {

    private final ContractRepository contractRepository;
    private final ContractEventPublisher contractEventPublisher;

    public Contract getOne(Long id) {
        return contractRepository.getOne(id);
    }

    public Contract getOneForWrite(Long id) {
        return contractRepository.getOneForWrite(id);
    }

    public Contract getOneForRead(Long id) {
        return contractRepository.getOneForRead(id);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Transactional
    public Contract save(Contract contract) {
        contractRepository.save(contract);
        return contract;
    }

    @Transactional
    public Contract updateStatus(Long id) {
        Contract contract = contractRepository.getOne(id);
        contract.setStatus(Status.PENDING);
        return save(contract);
    }

}
