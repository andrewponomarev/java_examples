package name.aaponomarev.restwithevents.domain;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@Proxy(lazy = false)
public class Contract {

    @Id
    @GeneratedValue
    private Long id;

    private Status status;

    private String main;

    private String details;

    private Integer num;
}
