package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.ponomarev.jsonb.contract.Contract;

import javax.persistence.*;

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

    String name;

    String stringValue;

    Long longValue;

    Double doubleValue;

    Boolean boolValue;
}
