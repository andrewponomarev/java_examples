package name.aaponomarev.restwithevents.events;

import lombok.RequiredArgsConstructor;
import name.aaponomarev.restwithevents.events.domain.DataCompletedEvent;
import name.aaponomarev.restwithevents.events.domain.FinalContractEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishFinalContractEvent(final Long id) {
        System.out.println("Publishing final contract event. ");
        FinalContractEvent finalContractEvent = new FinalContractEvent( this, id);
        applicationEventPublisher.publishEvent(finalContractEvent);
    }

    public void publishDataCompletedEvent(final Long id) {
        System.out.println("Publishing data completed event. ");
        DataCompletedEvent dataCompletedEvent = new DataCompletedEvent( this, id);
        applicationEventPublisher.publishEvent(dataCompletedEvent);
    }
}
