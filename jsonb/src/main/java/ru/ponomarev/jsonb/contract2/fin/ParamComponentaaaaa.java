package ru.ponomarev.jsonb.contract2.fin;

public interface ParamComponentaaaaa<T> {

    T get(String name, Parametrized p);

    <R> R get(String name, Class<R> clazz, Parametrized p);

    void set(String name, T value, Parametrized p);
}
