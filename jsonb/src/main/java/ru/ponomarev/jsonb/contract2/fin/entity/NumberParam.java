package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;
import ru.ponomarev.jsonb.contract2.SerializationUtils;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("NUMBER")
@NoArgsConstructor
public class NumberParam<T extends Number> extends SimpleParam<T> {

    BigDecimal numberValue;

    public NumberParam(String name, T val, Class<T> clazz) {
        super(name);
    }

    @Override
    public T get() {
        T result = null;
        try {
            result = (T) SerializationUtils.deserialize(numberValue.toString(), this.cls);
        } catch (Exception e) {}
        return result;
    }

//    @Override
//    <R> R get(Class<R> clazz) {
//        return SerializationUtils.deserialize(String.valueOf(numericValue), clazz);
//    }

    @Override
    public void setValue(T value) {
        if (value instanceof BigDecimal) {
            numberValue = (BigDecimal) value;
        } else {
            numberValue = new BigDecimal(value.toString());
        }
    }
}
