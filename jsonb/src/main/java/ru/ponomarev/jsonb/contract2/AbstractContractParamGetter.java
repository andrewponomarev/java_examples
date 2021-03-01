package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractContractParamGetter extends ContractParamHelper {

    public AbstractContractParamGetter(Contract2 contract) {
        super(contract);
    }

    protected Long getLongParam(String name, ContractParam parent) {
        return getParamValue(name, ContractParam::getLongValue, parent);
    }

    protected Double getDoubleParam(String name, ContractParam parent) {
        return getParamValue(name, ContractParam::getDoubleValue, parent);
    }

    protected Boolean getBooleanParam(String name, ContractParam parent) {
        return getParamValue(name, ContractParam::getBoolValue, parent);
    }

    protected LocalDate getLocalDateParam(String name, ContractParam parent) {
        return getParamValue(name, ContractParam::getDateValue, parent);
    }

    protected String getStringParam(String name, ContractParam parent) {
        return getParamValue(name, ContractParam::getStringValue, parent);
    }

    //todo: много косяков
    protected <T extends Enum<T>> T getEnumParam(@NotNull String name, Class enumClass, ContractParam parent) {
        Optional<ContractParam> optParam = getParamByNameAndParent(name, parent);
        Enum result = null;
        try {
            result = optParam.isEmpty() ? null : Enum.valueOf(enumClass, optParam.get().getStringValue());
        } catch (IllegalArgumentException e) {
        }
        return (T) result;
    }

    protected Object getObjectParam(String name, Class<?> clazz, ContractParam parent) {
        if (!ContractParamObject.class.isAssignableFrom(clazz)) {
            return null;
        }
        ContractParam root = getParamByNameAndParent(name, parent).orElse(null);
        return getObjectParam(root, clazz);
    }

    protected Collection<?> getCollection(String name, Class<?> clazz, ContractParam parent) {
        ContractParam root = getParamByNameAndParent(name, parent).orElse(null);
        return getCollection(root);
    }

    private Object getValue(@NotNull ContractParam param, ContractParam parent) {
        try {
            Class<?> clazz = Class.forName(param.getClassName());
            return getValue(param, clazz, parent);
        } catch (Exception e) {
            return null;
        }
    }

    private <T> Object getValue(ContractParam param, Class<T> clazz, ContractParam parent) {
        switch (param.getType()) {
            case STRING:
                return param.getStringValue();
            case LONG:
                return param.getLongValue();
            case DOUBLE:
                return param.getDoubleValue();
            case BOOLEAN:
                return param.getBoolValue();
            case DATE:
                return param.getDateValue();
            case ENUM:
                return getEnumParam(param.getName(), clazz, parent);
            case OBJECT:
                return getObjectParam(param, clazz);
            case COLLECTION:
                return getCollection(param);
            default:
                return null;
        }
    }

    private Collection<?> getCollection(ContractParam root) {
        List<ContractParam> childParams = getParamsByParent(root);
        return childParams.stream().map(p -> getValue(p, root))
                .collect(Collectors.toList());
    }

    private Object getObjectParam(ContractParam root, Class<?> clazz) {
        List<ContractParam> childParams = getParamsByParent(root);
        Map<String, Object> map = new HashMap<>();
        for (ContractParam p : childParams) {
            if (p.getName() == null) {
                continue;
            }
            Object val = getValue(p, root);
            if (val == null) {
                continue;
            }
            map.put(p.getName(), val);
        }
        return SerializationUtils.deserialize(map, clazz);
    }

    private <T> T getParamValue(String name,
                                Function<ContractParam, T> getter,
                                ContractParam parent) {
        Optional<ContractParam> optParam = getParamByNameAndParent(name, parent);
        return optParam.isEmpty() ? null : getter.apply(optParam.get());
    }
}
