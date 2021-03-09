package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

public abstract class AbstractParamComponent<T> extends ParamHelper<T> {

    public AbstractParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    public Param setParam(String name, Number value) {
        return setParam(name, value, null);
    }

    public Param setParam(String name, Boolean value) {
        return setParam(name, value, null);
    }

    public Param setParam(String name, LocalDate value) {
        return setParam(name, value, null);
    }

    public Param setParam(String name, String value) {
        return setParam(name, value, null);
    }

    public <T extends Enum<T>> Param setParam(String name, T value) {
        return setParam(name, value, null);
    }

    public <T extends ContractParamObject> Param setParam(String name, T value) {
        return setParam(name, value, null);
    }

    public Param setParam(String name, Collection<?> values) {
        return setParam(name, values, null);
    }

    public Long getLongParamValue(String name) {
        return this.getNumberParamValue(name, Long.class);
    }

    public BigDecimal getBigDecimalParamValue(String name) {
        return this.getNumberParamValue(name, BigDecimal.class);
    }

    public Double getDoubleParamValue(String name) {
        return this.getNumberParamValue(name, Double.class);
    }

    public Boolean getBooleanParamValue(String name) {
        return getParamValue(name, Param::getBoolValue);
    }

    public LocalDate getLocalDateParam(String name) {
        return getParamValue(name, Param::getDateValue);
    }

    public String getStringParam(String name) {
        return getParamValue(name, Param::getStringValue);
    }

    public ContractParamType getEnumParam(@NotNull String name) {
        return getEnumParamValue(name, ContractParamType.class);
    }

    public ContractParamObjectExample getContractParamObjectExampleParam(String name) {
        return getObjectParamValue(name, ContractParamObjectExample.class);
    }

    public Collection<?> getCollection(String name, Class<?> clazz) {
        return getCollection(name);
    }
    
    private Param setParam( String name, Number value, Param parent) {
        if (value == null) {
            return setParam(name, null, Param::setNumberValue, NUMBER, parent, null);
        } else {
            BigDecimal bigDecimalValue = new BigDecimal(value.toString());
            return setParam(name, bigDecimalValue, Param::setNumberValue, NUMBER, parent, (Class) value.getClass());
        }
    }

    private Param setParam( String name, Boolean value, Param parent) {
        return setParam(name, value, Param::setBoolValue, BOOLEAN, parent, null);
    }

    private Param setParam( String name, LocalDate value, Param parent) {
        return setParam(name, value, Param::setDateValue, DATE, parent, null);
    }

    private Param setParam( String name, String value, Param parent) {
        return setParam(name, value, Param::setStringValue, STRING, parent, null);
    }

    private <T extends Enum<T>> Param setParam(String name, T value, Param parent) {
        if (value == null) {
            return null;
        }
        return setParam(name, value.toString(), Param::setStringValue, ENUM, parent, (Class)value.getClass());
    }


    private <T extends ContractParamObject> Param setParam(String name, T value, Param parent) {
        Param root = setParamWithoutValue(name, OBJECT, value, parent, getClass(value));
        if (root == null) {
            return null;
        }
        if (root.getIsNull()) {
            deleteChildParams(name);
        } else {
            Map<String, ?> fieldMap = SerializationUtils.serialize(value);
            for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
                //todo: может изменить функцию на поиск сцщетсвующего парамаметра и создание в случае чего нового
                // возможно нужно считывать класс филда?
                setParam(e.getKey(), e.getValue(), root);
            }
        }
        return root;
    }

    private Param setParam(String name, Collection<?> values, Param parent) {
        Param root = setParamWithoutValue(name, COLLECTION, values, parent, null);
        if (root == null) {
            return null;
        }
        if (root.getIsNull()) {
            deleteChildParams(name);
        } else {
            for (Object val : values) {
                setParam(null, val, root);
            }
        }
        return root;
    }

    private <T> Param setParam(String name, T value, Param parent) {
        if (value instanceof Number) {
            return setParam(name, (Number) value, parent);
        } else if (value instanceof Boolean) {
            return setParam(name, (Boolean) value, parent);
        } else if (value instanceof LocalDate) {
            return setParam(name, (LocalDate) value, parent);
        } else if (value instanceof String) {
            return setParam(name, (String) value, parent);
        } else if (value != null && value.getClass().isEnum()) {
            return setParam(name, (Enum) value, parent);
        } else if (value instanceof ContractParamObject){
            return setParam(name, (ContractParamObject) value, parent);
        } else if (value instanceof Collection) {
            return setParam(name, (Collection<?>) value, parent);
        } else {
            return setParam(name, value.toString(), parent);
        }
    }

    private void deleteChildParams(String parentName) {
        getParamsByParentName(parentName).stream()
                .forEach((p) -> p = null);
    }


    private <T> Param setParam(String name,
                               T value,
                               BiConsumer<Param, T> setter,
                               ContractParamType type,
                               Param parent,
                               Class<T> clazz) {
        Param param = setParamWithoutValue(name, type, value, parent, clazz);
        // todo: ?
//            if (!param.getType().equals(type)) {
//                return;
//            }
        if (param != null) {
            setter.accept(param, value);
        }
        return param;
    }

    private <T> Param setParamWithoutValue(String name,
                                 ContractParamType type,
                                 T value,
                                 Param parent,
                                 Class<T> clazz) {
        Param param = getParamByNameAndParent(name, parent)
                .orElseGet(createContractParamSupplier(name, type, value, parent, clazz));
        if (param != null) {
            param.setIsNull(value == null);
        }
        return param;
    }

    private <T extends Number> T getNumberParamValue(@NotNull String name, Class<T> clazz) {
        return SerializationUtils.deserialize(
                String.valueOf(getParamValue(name, Param::getNumberValue)), clazz);
    }

    private <T> T getParamValue(String name, Function<Param, T> getter) {
        Optional<Param> optParam = getRootParamByName(name);
        return optParam.isEmpty() ? null : getter.apply(optParam.get());
    }

    public <T extends Enum<T>> T getEnumParamValue(@NotNull String name, @NotNull Class<T> clazz) {
        if (!clazz.isEnum()) {
            return null;
        }
        Param param = getRootParamByName(name).orElse(null);
        if (param == null) {
            return null;
        }
        return getEnumParamValue(param, clazz);
    }

    private <T> T getObjectParamValue(String name, Class<T> clazz) {
        // todo: удалить интерфейс
        if (!ContractParamObject.class.isAssignableFrom(clazz)) {
            return null;
        }
        Param root = getRootParamByName(name).orElse(null);
        return getObjectParamValue(root, clazz);
    }

    private Collection<?> getCollection(String name) {
        Param root = getRootParamByName(name).orElse(null);
        return getCollection(root);
    }


    private <T> T getObjectParamValue(Param root, Class<T> clazz) {
        if (root == null || root.getIsNull() == null) {
            return null;
        }
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
        if (root == null || root.getIsNull() == null) {
            return null;
        }
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

    private <T extends Enum<T>> T getEnumParamValue(Param param, Class<T> clazz) {
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

    private Class getClass(Object obj) {
        if (obj == null) {
            return null;
        } else {
            return obj.getClass();
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
