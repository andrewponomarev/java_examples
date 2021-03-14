package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BOOLEAN")
@NoArgsConstructor
public class BooleanParam extends SimpleParam<Boolean> {

    public BooleanParam(String name) {
        super(name);
    }

    private Boolean booleanValue;

    @Override
    public Boolean get() {
        return booleanValue;
    }


    @Override
    void setValue(Boolean value) {
        if (value instanceof Boolean) {
            booleanValue = value;
        }
    }
}
