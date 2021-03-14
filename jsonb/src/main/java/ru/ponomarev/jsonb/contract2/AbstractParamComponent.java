package ru.ponomarev.jsonb.contract2;

import java.util.List;

public abstract class AbstractParamComponent<T> extends ParamHelper<T> {
    public AbstractParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

//    public AbstractParamComponent(T parentObject, List<Param> params) {
//        super(parentObject, params);
//    }
//
//    private NumberParamComponent<T> numberParamComponent;
//    private BooleanParamComponent<T> booleanParamComponent;
//    private LocalDateParamComponent<T> localDateParamComponent;
//    private StringParamComponent<T> stringParamComponent;
//
//    public Param setParam(@NotNull String name, Number value) {
//        return numberParamComponent.setParam(name, value, null);
//    }
//
//    public Param setParam(@NotNull String name, Boolean value) {
//        return booleanParamComponent.setParam(name, value, null);
//    }
//
//    public Param setParam(@NotNull String name, LocalDate value) {
//        return localDateParamComponent.setParam(name, value, null);
//    }
//
//    public Param setParam(@NotNull String name, String value) {
//        return stringParamComponent.setParam(name, value, null);
//    }
//
//    public <T extends Enum<T>> Param setParam(@NotNull String name, T value) {
//        return setParam(name, value, null);
//    }
//
//    public <T extends ContractParamObject> Param setParam(@NotNull String name, T value) {
//        return setParam(name, value, null);
//    }
//
//    public Param setParam(@NotNull String name, Collection<?> values) {
//        return setParam(name, values, null);
//    }
//
//    public Long getLongParamValue(@NotNull String name) {
//        return numberParamComponent.getParamValue(name, Long.class);
//    }
//
//    public BigDecimal getBigDecimalParamValue(@NotNull String name) {
//        return numberParamComponent.getParamValue(name, BigDecimal.class);
//    }
//
//    public Double getDoubleParamValue(@NotNull String name) {
//        return numberParamComponent.getParamValue(name, Double.class);
//    }
//
//    public LocalDate getLocalDateParam(@NotNull String name) {
//        return localDateParamComponent.getParamValue(name, LocalDate.class);
//    }
//
//    public String getStringParam(@NotNull String name) {
//        return getParamValue(name, Param::getStringValue);
//    }
//
//    public ContractParamType getEnumParam(@NotNull String name) {
//        return getEnumParamValue(name, ContractParamType.class);
//    }
//
//    public Collection<?> getCollection(@NotNull String name, Class<?> clazz) {
//        return getCollection(name);
//    }
//
//    private Param setParam(String name, Number value, Param parent) {
//        Param param = getOrCreateParam(name, value, parent);
//        return setValueAndType(param, value);
//    }
//
//    private Param setParam(String name, Boolean value, Param parent) {
//        Param param = getOrCreateParam(name, value, parent);
//        return setValueAndType(param, value);
//    }
//
//    private Param setParam(String name, LocalDate value, Param parent) {
//        Param param = getOrCreateParam(name, value, parent);
//        return setValueAndType(param, value);
//    }
//
//    private Param setParam(String name, String value, Param parent) {
//        Param param = getOrCreateParam(name, value, parent);
//        return setValueAndType(param, value);
//    }
//
//    private <T extends Enum<T>> Param setParam(String name, T value, Param parent) {
//        return setParam(name, toString(value), Param::setStringValue, ENUM, parent);
//    }
//
//    private <T extends ContractParamObject> Param setParam(String name, T value, Param parent) {
//        Param root = getOrCreateParam(name, value, parent);
//        root.setType(OBJECT);
//        if (root == null) {
//            return null;
//        }
//        if (root.getIsNull()) {
//            deleteChildParams(name);
//        } else {
//            Map<String, ?> fieldMap = SerializationUtils.serialize(value);
//            for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
//                Param param = getOrCreateParam(e.getKey(), e.getValue(), root);
//                setValueAndType(param, e.getValue());
//            }
//        }
//        return root;
//    }
//
//    private Param setParam(String name, Collection<?> values, Param parent) {
//        Param root = getOrCreateParam(name, values, parent);
//        root.setType(COLLECTION);
//        if (root == null) {
//            return null;
//        }
//        deleteChildParams(name);
//        for (Object val : values) {
//            setParam(null, val, root);
//        }
//        return root;
//    }
//
//    private Param setValueAndType(Param param, String value) {
//        return setValueAndType(param, value, Param::setStringValue, STRING);
//    }
//
//    private <T extends Enum<T>> Param setValueAndType(Param param, T value) {
//        return setValueAndType(param, toString(value), Param::setStringValue, ENUM);
//    }
//
//    private <T extends ContractParamObject> Param setValueAndType(String name, T value, Param parent) {
//        Param root = getOrCreateParam(name, value, parent);
//        root.setType(OBJECT);
//        if (root == null) {
//            return null;
//        }
//        if (root.getIsNull()) {
//            deleteChildParams(name);
//        } else {
//            Map<String, ?> fieldMap = SerializationUtils.serialize(value);
//            for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
//                Param param = getOrCreateParam(e.getKey(), e.getValue(), root);
//                setValueAndType(param, e.getValue());
//            }
//        }
//        return root;
//    }
//
//    private Param setValueAndType(String name, Collection<?> values, Param parent) {
//        Param root = getOrCreateParam(name, values, parent);
//        root.setType(COLLECTION);
//        if (root == null) {
//            return null;
//        }
//        deleteChildParams(name);
//        for (Object val : values) {
//            setParam(null, val, root);
//        }
//        return root;
//    }
//
//    private <T> Param setValueAndType(Param param, T value) {
//        Class<?> clazz = getClass(param);
//        if (clazz == null) {
//            return param;
//        }
//        if (Number.class.isAssignableFrom(clazz)) {
//            return setValueAndType(param, toBigDecimal((Number)value), Param::setNumberValue, NUMBER);
//        } else if (Boolean.class.isAssignableFrom(clazz)) {
//            return setValueAndType(param, (Boolean) value, Param::setBoolValue, BOOLEAN);
//        } else if (LocalDate.class.isAssignableFrom(clazz)) {
//
//        } else if (String.class.isAssignableFrom(clazz)) {
//
//        } else if (Enum.class.isAssignableFrom(clazz)) {
//
//        } else if (Collection.class.isAssignableFrom(clazz)) {
//            setValueAndType(param, , Param::setStringValue, COLLECTION);
//        } else {
//            setValueAndType(param, toString(value), Param::setStringValue, STRING);
//        }
//
//        else if (value instanceof LocalDate) {
//            return setParam(name, (LocalDate) value, parent);
//        } else if (value instanceof String) {
//            return setParam(name, (String) value, parent);
//        } else if (value != null && value.getClass().isEnum()) {
//            return setParam(name, (Enum) value, parent);
//        } else if (value instanceof ContractParamObject){
//            return setParam(name, (ContractParamObject) value, parent);
//        } else if (value instanceof Collection) {
//            return setParam(name, (Collection<?>) value, parent);
//        } else {
//            return setParam(name, value.toString(), parent);
//        }
//    }
//
//    private <T> Param setParam(String name,
//                               T value,
//                               BiConsumer<Param, T> setter,
//                               ContractParamType type,
//                               Param parent) {
//        Param param = getOrCreateParam(name, value, parent);
//        return setValueAndType(param, value, setter, type);
//    }
//
//    private <T> Param setValueAndType(Param param, T value,
//                               BiConsumer<Param, T> setter,
//                               ContractParamType type) {
//        if (param != null) {
//            setter.accept(param, value);
//        }
//        param.setType(type);
//        return param;
//    }
//
//    private void deleteChildParams(String parentName) {
//        getParamsByParentName(parentName).stream()
//                .forEach((p) -> p = null);
//    }
//
//    public <T extends Enum<T>> T getEnumParamValue(@NotNull String name, @NotNull Class<T> clazz) {
//        if (!clazz.isEnum()) {
//            return null;
//        }
//        Param param = getRootParamByName(name).orElse(null);
//        if (param == null) {
//            return null;
//        }
//        return getEnumParamValue(param, clazz);
//    }
//
//    protected <T> T getObjectParamValue(String name, Class<T> clazz) {
//        // todo: удалить интерфейс
//        if (!ContractParamObject.class.isAssignableFrom(clazz)) {
//            return null;
//        }
//        Param root = getRootParamByName(name).orElse(null);
//        return getObjectParamValue(root, clazz);
//    }
//
//    private Collection<?> getCollection(String name) {
//        Param root = getRootParamByName(name).orElse(null);
//        return getCollection(root);
//    }
//
//    private <T> T getObjectParamValue(Param root, Class<T> clazz) {
//        if (root == null || root.getIsNull() == null) {
//            return null;
//        }
//        List<Param> childParams = getParamsByParent(root);
//        Map<String, Object> map = new HashMap<>();
//        for (Param p : childParams) {
//            if (p.getName() == null) {
//                continue;
//            }
//            Object val = getValue(p);
//            if (val == null) {
//                continue;
//            }
//            map.put(p.getName(), val);
//        }
//        return SerializationUtils.deserialize(map, clazz);
//    }
//
//    private Collection<?> getCollection(Param root) {
//        if (root == null || root.getIsNull() == null) {
//            return null;
//        }
//        List<Param> childParams = getParamsByParent(root);
//        return childParams.stream().map(p -> getValue(p))
//                .collect(Collectors.toList());
//    }
//
//    private Object getValue(Param param) {
//        switch (param.getType()) {
//            case STRING:
//                return param.getStringValue();
//            case NUMBER:
//                return getNumberParamValue(param);
//            case BOOLEAN:
//                return param.getBoolValue();
//            case DATE:
//                return param.getDateValue();
//            case ENUM:
//                return getEnumParamValue(param, getEnumClass(param));
//            case OBJECT:
//                return getObjectParamValue(param, getClass(param));
//            case COLLECTION:
//                return getCollection(param);
//            default:
//                return null;
//        }
//    }
//
//    private Object getNumberParamValue(Param param) {
//        return SerializationUtils.deserialize(param.getNumberValue().toString(), getClass(param));
//    }
//
//    private <T extends Enum<T>> T getEnumParamValue(Param param, Class<T> clazz) {
//        if (!clazz.isEnum()) {
//            return null;
//        }
//        T result = null;
//        try {
//            result = param == null ? null : Enum.valueOf(clazz, param.getStringValue());
//        } catch (Exception e) {
//        }
//        return result;
//    }
//
//    private Class<?> getClass(Param param) {
//        try {
//            return Class.forName(param.getClassName());
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private Class<? extends Enum> getEnumClass(Param param) {
//        try {
//            return (Class<? extends Enum>) Class.forName(param.getClassName());
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private String toString(Object obj) {
//        if (obj == null) {
//            return null;
//        }
//        return obj.toString();
//    }
}
