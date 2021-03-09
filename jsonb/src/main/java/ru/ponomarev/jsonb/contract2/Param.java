package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class Param {

    public Param() {
    }

    public Param(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    @NotNull
    Contract contract;

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    Param parent;

    @Enumerated(EnumType.STRING)
    ContractParamType type;

    String name;

    String className;

    String stringValue;

    BigDecimal numberValue;

    Boolean boolValue;

    LocalDate dateValue;

    Boolean isNull;

}
