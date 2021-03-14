package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;
import ru.ponomarev.jsonb.contract2.fin.ParamUtils;

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
            result = Enum.valueOf((Class<T>) ParamUtils.getClass(this), stringValue);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public void set(Object value) {
        if (value != null && value.getClass().isEnum()) {
            stringValue = value.toString();
        }
    }
}