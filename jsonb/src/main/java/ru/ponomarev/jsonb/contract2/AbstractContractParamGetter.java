package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractContractParamGetter extends ContractParamHelper {

    public AbstractContractParamGetter(Contract2 contract, List<Param> params) {
        super(contract, params);
    }

    protected <T extends Number> T getNumberParamValue(@NotNull String name, Class<T> clazz) {
        return SerializationUtils.deserialize(
                getParamValue(name, Param::getNumberValue).toString(), clazz);
    }

    protected <T> T getParamValue(String name, Function<Param, T> getter) {
        Optional<Param> optParam = getRootParamByName(name);
        return optParam.isEmpty() ? null : getter.apply(optParam.get());
    }

    protected <T extends Enum<T>> T getEnumParamValue(@NotNull String name, @NotNull Class<T> clazz) {
        if (!clazz.isEnum()) {
            return null;
        }
        Param param = getRootParamByName(name).orElse(null);
        if (param == null) {
            return null;
        }
        return getEnumParamValue(param, clazz);
    }

    protected <T> T getObjectParamValue(String name, Class<T> clazz) {
        // todo: удалить интерфейс
        if (!ContractParamObject.class.isAssignableFrom(clazz)) {
            return null;
        }
        Param root = getRootParamByName(name).orElse(null);
        return getObjectParamValue(root, clazz);
    }

    protected Collection<?> getCollection(String name) {
        Param root = getRootParamByName(name).orElse(null);
        return getCollection(root);
    }


    private <T> T getObjectParamValue(Param root, Class<T> clazz) {
        List<Param> childParams = getParamsByParent(root);
        Map<String, Object> map = new HashMap<>();
        for (Param p : childParams) {
            if (p.getName() == null) {
                continue;
            }
            Object val = getValue(p);
            if (val == null) {
                continue;
            }
            map.put(p.getName(), val);
        }
        return SerializationUtils.deserialize(map, clazz);
    }

    private Collection<?> getCollection(Param root) {
        List<Param> childParams = getParamsByParent(root);
        return childParams.stream().map(p -> getValue(p))
                .collect(Collectors.toList());
    }

    private Object getValue(Param param) {
        switch (param.getType()) {
            case STRING:
                return param.getStringValue();
            case NUMBER:
                return getNumberParamValue(param);
            case BOOLEAN:
                return param.getBoolValue();
            case DATE:
                return param.getDateValue();
            case ENUM:
                return getEnumParamValue(param, getEnumClass(param));
            case OBJECT:
                return getObjectParamValue(param, getClass(param));
            case COLLECTION:
                return getCollection(param);
            default:
                return null;
        }
    }
    private Object getNumberParamValue(Param param) {
        return SerializationUtils.deserialize(param.getNumberValue().toString(), getClass(param));
    }

    protected <T extends Enum<T>> T getEnumParamValue(Param param, Class<T> clazz) {
        if (!clazz.isEnum()) {
            return null;
        }
        T result = null;
        try {
            result = param == null ? null : Enum.valueOf(clazz, param.getStringValue());
        } catch (Exception e) {
        }
        return result;
    }

    private Class<?> getClass(Param param) {
        try {
            return Class.forName(param.getClassName());
        } catch (Exception e) {
            return null;
        }
    }

    private Class<? extends Enum> getEnumClass(Param param) {
        try {
            return (Class<? extends Enum>) Class.forName(param.getClassName());
        } catch (Exception e) {
            return null;
        }
    }

}
