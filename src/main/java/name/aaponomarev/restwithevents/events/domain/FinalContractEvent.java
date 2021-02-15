package name.aaponomarev.restwithevents.events.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FinalContractEvent extends PartnerContractEvent {

    public FinalContractEvent(Object source, Long id) {
        super(source, id);
    }
}
