package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

public abstract class AbstractContractParamSetter extends ContractParamHelper {

    public AbstractContractParamSetter(Contract2 contract2) {
        super(contract2);
    }

    protected ContractParam setParam(@Nullable String name, Long value, ContractParam parent) {
        return setParam(name, value, ContractParam::setLongValue, LONG, parent, null);
    }

    protected ContractParam setParam(@Nullable String name, Double value, ContractParam parent) {
        return setParam(name, value, ContractParam::setDoubleValue, DOUBLE, parent, null);
    }

    protected ContractParam setParam(@Nullable String name, Boolean value, ContractParam parent) {
        return setParam(name, value, ContractParam::setBoolValue, BOOLEAN, parent, null);
    }

    protected ContractParam setParam(@Nullable String name, LocalDate value, ContractParam parent) {
        return setParam(name, value, ContractParam::setDateValue, DATE, parent, null);
    }

    protected ContractParam setParam(@Nullable String name, String value, ContractParam parent) {
        return setParam(name, value, ContractParam::setStringValue, STRING, parent, null);
    }

    protected <T extends Enum<T>> ContractParam setParam(String name, T value, ContractParam parent) {
        return setParam(name, value.toString(), ContractParam::setStringValue, ENUM, parent, (Class)value.getClass());
    }


    protected <T extends ContractParamObject> ContractParam setParam(String name, T value, ContractParam parent) {
        ContractParam root = setParam(name, value, OBJECT, parent, null);
        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
            if (e.getValue() == null) {
                continue;
            }
            setParam(e.getKey(), e.getValue(), root);
        }
        return root;
    }

    protected ContractParam setParam(String name, Collection<?> values, ContractParam parent) {
        ContractParam root = setParam(name, values, COLLECTION, parent, null);
        for (Object val : values) {
            if (val == null) {
                continue;
            }
            setParam(null, val, root);
        }
        return root;
    }

    private <T> ContractParam setParam(String name, T value, ContractParam parent) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return setParam(name, (Long) value, parent);
        } else if (value instanceof Double) {
            return setParam(name, (Double) value, parent);
        } else if (value instanceof Boolean) {
            return setParam(name, (Boolean) value, parent);
        } else if (value instanceof LocalDate) {
            return setParam(name, (LocalDate) value, parent);
        } else if (value instanceof String) {
            return setParam(name, (String) value, parent);
        } else if (value.getClass().isEnum()) {
            return setParam(name, (Enum) value, parent);
        } else if (value instanceof ContractParamObject){
            return setParam(name, (ContractParamObject) value, parent);
        } else if (value instanceof Collection) {
            return setParam(name, (Collection<?>) value, parent);
        } else {
            return setParam(name, value.toString(), parent);
        }
    }

    private <T> ContractParam setParam(@Nullable String name,
                                       T value,
                                       BiConsumer<ContractParam, T> setter,
                                       ContractParamType type,
                                       ContractParam parent,
                                       Class<T> clazz) {
        ContractParam param = setParam(name, value, type, parent, clazz);
        // todo: ?
//            if (!param.getType().equals(type)) {
//                return;
//            }
        setter.accept(param, value);
        return param;
    }

    protected <T> ContractParam setParam(@Nullable String name,
                                         @NotNull T value,
                                         ContractParamType type,
                                         ContractParam parent,
                                         Class<T> clazz) {
        return getParamByNameAndParent(name, parent)
                .orElseGet(createContractParamSupplier(name, value, type, parent, clazz));
    }
}
