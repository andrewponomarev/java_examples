package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.ponomarev.jsonb.contract2.Contract;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Data
@NoArgsConstructor
public abstract class Param<T> {

    public Param(String name) {
        this.name = name;
        isNull = false;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id")
    Contract contract;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL} )
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    Param<?> parent;

    String name;

    String className;

    Boolean isNull;

    public abstract T get();

    public abstract void set(Object value);
}
