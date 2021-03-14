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
    void setValue(String value) {
        if (value == null || this.cls.isInstance(value)) {
            stringValue = value;
        }
    }

    @Override
    public String get() {
        return stringValue;
    }

}
