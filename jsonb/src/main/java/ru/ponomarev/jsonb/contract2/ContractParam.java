package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class ContractParam {

    public ContractParam() {
    }

    public ContractParam(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    @NotNull
    Contract2 contract;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    ContractParam parent;

    @Enumerated(EnumType.STRING)
    ContractParamType type;

    String name;

    String stringValue;

    Long longValue;

    Double doubleValue;

    Boolean boolValue;

    LocalDate dateValue;

}
