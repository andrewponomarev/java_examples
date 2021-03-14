package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class SimpleParam<T> extends Param<T> {

    public SimpleParam(String name) {
        super(name);
    }

    @Override
    public void set(Object value) {
        if (this.cls == null && value == null) {
            return;
        }
        if (value == null) {
            setValue(null);
        }
        if (this.cls != null && this.cls.isInstance(value)) {
            setValue(cls.cast(value));
        }
    }

    abstract void setValue(T value);

}
