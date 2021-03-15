package ru.ponomarev.jsonb.contract2.fin.entity;

import liquibase.util.BooleanUtils;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public abstract class CompositeParam<T> extends Param<T> {

    public CompositeParam(String name) {
        super(name);
    }

    abstract void add(Param<?> c);

    abstract void clear();

    public abstract List<Param<?>> getChildren();

    protected abstract T getValue();

    protected abstract void setValue(Object value);

    public T get() {
        if (BooleanUtils.isTrue(isNull)) {
            return null;
        }
        return getValue();
    }

    public void set(Object value) {
        if (value == null) {
            clear();
            isNull = true;
            return;
        }
        setValue(value);
    }

}
