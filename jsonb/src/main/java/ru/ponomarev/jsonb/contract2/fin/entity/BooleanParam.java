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

    private Boolean boolValue;

    @Override
    public Boolean get() {
        return boolValue;
    }


    @Override
    void setValue(Boolean value) {
        if (value instanceof Boolean) {
            boolValue = value;
        }
    }
}
