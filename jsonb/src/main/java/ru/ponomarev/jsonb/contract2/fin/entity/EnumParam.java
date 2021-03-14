package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ENUM")
@NoArgsConstructor
public class EnumParam<T extends Enum<T>> extends SimpleParam<T> {

    String stringValue;

    public EnumParam(String name) {
        super(name);
    }

    @Override
    public T get() {
        T result = null;
        try {
            result = Enum.valueOf(this.cls, stringValue);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    void setValue(T value) {
        if (value != null && value.getClass().isEnum()) {
            stringValue = value.toString();
        }
    }
}