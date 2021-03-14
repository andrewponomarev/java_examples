package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class CompositeParam<T> extends Param<T> {

    public CompositeParam(String name) {
        super(name);
    }

    abstract void add(Param<?> c);

    abstract void clean();

}
