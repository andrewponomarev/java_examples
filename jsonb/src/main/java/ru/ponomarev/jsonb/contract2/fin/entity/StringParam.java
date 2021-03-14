package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STRING")
@NoArgsConstructor
public class StringParam extends SimpleParam<String> {

    String stringValue;

    public StringParam(String name) {
        super(name);
    }

    @Override
    public String get() {
        return stringValue;
    }

    @Override
    public void set(Object value) {
        if (value == null || value instanceof String) {
            stringValue = (String) value;
        }
    }
}
