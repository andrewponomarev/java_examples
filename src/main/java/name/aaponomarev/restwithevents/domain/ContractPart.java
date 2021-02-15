package name.aaponomarev.restwithevents.domain;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name = "contract")
@Proxy(lazy = false)
public class ContractPart {

    @Id
    @GeneratedValue
    private Long id;

    private String main;

    private String details;

}
