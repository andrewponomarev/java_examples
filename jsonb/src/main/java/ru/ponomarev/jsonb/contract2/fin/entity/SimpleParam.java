package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class SimpleParam<T> extends Param<T> {

    public SimpleParam(String name) {
        super(name);
    }

}
