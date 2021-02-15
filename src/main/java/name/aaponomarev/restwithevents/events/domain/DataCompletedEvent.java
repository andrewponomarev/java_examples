package name.aaponomarev.restwithevents.events.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DataCompletedEvent extends PartnerContractEvent {

    public DataCompletedEvent(Object source, Long id) {
        super(source, id);
    }
}
