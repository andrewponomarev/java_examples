package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class BaseTypeParamComponent<T, R> extends ParamHelper<T> {

    public BaseTypeParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    Param setParam(String name, R value, Param parent) {
        Param param = getOrCreateParam(name, value, parent);
        return setValueAndType(param, value);
    }

    <R> Param getOrCreateParam(String name, R value, Param parent) {
        Param param = getParamByNameAndParent(name, parent)
                .orElseGet(paramSupplier(name, value, parent));
        if (param != null) {
            param.setIsNull(value == null);
        }
        return param;
    }

    <R> Param setValueAndType(Param param, R value,
                                      BiConsumer<Param, R> setter,
                                      ContractParamType type) {
        if (param == null) {
            return null;
        }
        setter.accept(param, value);
        param.setType(type);
        return param;
    }

    R getParamValue(String name, Function<Param, R> getter) {
        Optional<Param> optParam = getRootParamByName(name);
        return optParam.isEmpty() ? null : getter.apply(optParam.get());
    }

    abstract Param setValueAndType(Param param, R value);

    abstract R getParamValue(@NotNull String name, Class<? extends R> clazz);

}
