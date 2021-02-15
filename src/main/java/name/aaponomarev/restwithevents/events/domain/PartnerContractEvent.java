package name.aaponomarev.restwithevents.events.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PartnerContractEvent extends ApplicationEvent {

    private Long id;

    public PartnerContractEvent(Object source, Long id) {
        super(source);
        this.id = id;
    }

}
