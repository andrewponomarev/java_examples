package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

public abstract class AbstractContractParamSetter extends ContractParamHelper {

    public AbstractContractParamSetter(Contract2 contract2, List<Param> params) {
        super(contract2, params);
    }

    protected Param setParam(@Nullable String name, Number value, Param parent) {
        return setParam(name, value.toString(), Param::setStringValue, NUMBER, parent, (Class) value.getClass());
    }

    protected Param setParam(@Nullable String name, Boolean value, Param parent) {
        return setParam(name, value, Param::setBoolValue, BOOLEAN, parent, null);
    }

    protected Param setParam(@Nullable String name, LocalDate value, Param parent) {
        return setParam(name, value, Param::setDateValue, DATE, parent, null);
    }

    protected Param setParam(@Nullable String name, String value, Param parent) {
        return setParam(name, value, Param::setStringValue, STRING, parent, null);
    }

    protected <T extends Enum<T>> Param setParam(String name, T value, Param parent) {
        return setParam(name, value.toString(), Param::setStringValue, ENUM, parent, (Class)value.getClass());
    }


    protected <T extends ContractParamObject> Param setParam(String name, T value, Param parent) {
        Param root = setParam(name, value, OBJECT, parent, null);
        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
            if (e.getValue() == null) {
                continue;
            }
            setParam(e.getKey(), e.getValue(), root);
        }
        return root;
    }

    protected Param setParam(String name, Collection<?> values, Param parent) {
        Param root = setParam(name, values, COLLECTION, parent, null);
        for (Object val : values) {
            if (val == null) {
                continue;
            }
            setParam(null, val, root);
        }
        return root;
    }

    private <T> Param setParam(String name, T value, Param parent) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return setParam(name, (Number) value, parent);
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

    private <T> Param setParam(@Nullable String name,
                               T value,
                               BiConsumer<Param, T> setter,
                               ContractParamType type,
                               Param parent,
                               Class<T> clazz) {
        Param param = setParam(name, value, type, parent, clazz);
        // todo: ?
//            if (!param.getType().equals(type)) {
//                return;
//            }
        setter.accept(param, value);
        return param;
    }

    protected <T> Param setParam(@Nullable String name,
                                 @NotNull T value,
                                 ContractParamType type,
                                 Param parent,
                                 Class<T> clazz) {
        return getParamByNameAndParent(name, parent)
                .orElseGet(createContractParamSupplier(name, value, type, parent, clazz));
    }
}
