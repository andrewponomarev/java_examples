package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.ponomarev.jsonb.GeneralContract;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

@Entity
@Data
//@TypeDefs({@TypeDef(name = "jsonb", typeClass = ContractParams.class)})
public class Contract2 extends GeneralContract {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<ContractParam> params = new ArrayList<>();

    private String value;

    public static final String LONG_NAMED_PARAM = "namedLongParam";
    public static final String DOUBLE_NAMED_PARAM = "namedDoubleParam";
    public static final String BOOLEAN_NAMED_PARAM = "namedBooleanParam";
    public static final String DATE_NAMED_PARAM = "localDateParam";
    public static final String STRING_NAMED_PARAM = "namedStringParam";
    public static final String SOME_OBJECT_PARAM = "someObjectField";

    public static String CONTRACT_PARAM_OBJECT_EXAMPLE = "ContractParamObjectExample";

    public static String COLLECTION_LONG = "collectionLong";
    public static String COLLECTION_DOUBLE = "collectionDouble";
    public static String COLLECTION_BOOLEAN = "collectionBoolean";
    public static String COLLECTION_DATE = "collectionDate";
    public static String COLLECTION_STRING = "collectionString";
    public static String COLLECTION_SOME_OBJECT = "collectionSomeObject";


    @Transient
    private ContractParamUtils contractParamUtils = new ContractParamUtils();

    public String getNamedStringParam() {
        return contractParamUtils.getStringParam(STRING_NAMED_PARAM);
    }

    public void setNamedStringParam(String value) {
        contractParamUtils.setStringParam(STRING_NAMED_PARAM, value);
    }

    public Long getLongNamedParam() {
        return contractParamUtils.getLongParam(LONG_NAMED_PARAM);
    }

    public void setLongNamedParam(Long value) {
        contractParamUtils.setLongParam(LONG_NAMED_PARAM, value);
    }

    public Double getDoubleNamedParam() {
        return contractParamUtils.getDoubleParam(DOUBLE_NAMED_PARAM);
    }

    public void setDoubleNamedParam(Double value) {
        contractParamUtils.setDoubleParam(DOUBLE_NAMED_PARAM, value);
    }

    public LocalDate getLocalDateNamedParam() {
        return contractParamUtils.getLocalDateParam(DATE_NAMED_PARAM);
    }

    public void setLocalDateNamedParam(LocalDate value) {
        contractParamUtils.setLocalDateParam(DATE_NAMED_PARAM, value);
    }


    public Boolean getBooleanNamedParam() {
        return contractParamUtils.getBooleanParam(BOOLEAN_NAMED_PARAM);
    }

    public void setBooleanNamedParam(Boolean value) {
        contractParamUtils.setBooleanParam(BOOLEAN_NAMED_PARAM, value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return contractParamUtils.getObjectParam(CONTRACT_PARAM_OBJECT_EXAMPLE, ContractParamObjectExample.class);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        contractParamUtils.setObjectParam(CONTRACT_PARAM_OBJECT_EXAMPLE, value);
    }

    public String getSomeObjectParam() {
        return contractParamUtils.getStringParam(SOME_OBJECT_PARAM);
    }

    public void setSomeObjectParam(Object value) {
        contractParamUtils.setStringParam(SOME_OBJECT_PARAM, value);
    }

    public Collection<?> getCollectionLongNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_LONG, Long.class);
    }

    public void setCollectionLongNamedParam(Collection<? extends Long> value) {
        contractParamUtils.setCollection(COLLECTION_LONG, value);
    }

    public Collection<?> getCollectionDoubleNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_DOUBLE, Double.class);
    }

    public void setCollectionDoubleNamedParam(Collection<? extends Double> value) {
        contractParamUtils.setCollection(COLLECTION_DOUBLE, value);
    }

    public Collection<?> getCollectionBooleanNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_BOOLEAN, Boolean.class);
    }

    public void setCollectionBooleanNamedParam(Collection<? extends Boolean> value) {
        contractParamUtils.setCollection(COLLECTION_BOOLEAN, value);
    }

    public Collection<?> getCollectionDateNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_DATE, LocalDate.class);
    }

    public void setCollectionDateNamedParam(Collection<? extends LocalDate> value) {
        contractParamUtils.setCollection(COLLECTION_DATE, value);
    }

    public Collection<?> getCollectionStringNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_STRING, String.class);
    }

    public void setCollectionStringNamedParam(Collection<? extends String> value) {
        contractParamUtils.setCollection(COLLECTION_STRING, value);
    }

    public Collection<?> getCollectionSomeObjectNamedParam() {
        return contractParamUtils.getCollection(COLLECTION_SOME_OBJECT, String.class);
    }

    public void setCollectionSomeObject(Collection<? extends Object> value) {
        contractParamUtils.setCollection(COLLECTION_SOME_OBJECT, value);
    }


    private class ContractParamUtils {

        public void setLongParam(String name, Long value) {
            setParam(name, value, ContractParam::setLongValue);
        }

        public Long getLongParam(String name) {
            return getParam( name, ContractParam::getLongValue);
        }

        public void setDoubleParam(String name, Double value) {
            setParam(name, value, ContractParam::setDoubleValue);
        }

        public Double getDoubleParam(String name) {
            return getParam( name, ContractParam::getDoubleValue);
        }

        public void setBooleanParam(String name, Boolean value) {
            setParam( name, value, ContractParam::setBoolValue);
        }

        public Boolean getBooleanParam(String name) {
            return getParam( name, ContractParam::getBoolValue);
        }

        public void setLocalDateParam(String name, LocalDate value) {
            setParam(name, value, ContractParam::setDateValue);
        }

        public LocalDate getLocalDateParam(String name) {
            return getParam( name, ContractParam::getDateValue);
        }

        public void setStringParam(String name, Object value) {
            setParam( name, value.toString(), ContractParam::setStringValue);
        }

        public String getStringParam(String name) {
            return getParam( name, ContractParam::getStringValue);
        }

        //todo: nullpointer
        public <T> T getObjectParam(String name, Class<T> clazz) {
            List<ContractParam> childParams = getParamsByParentName(name);
            Map<String, ?> map = childParams.stream()
                    .collect(Collectors.toMap(ContractParam::getId, p -> getValue(p, clazz)));
            return SerializationUtils.deserialize(map, clazz);
        }

        public <T> void setObjectParam(String name, T value) {
            ContractParam parentParam = getParamByName(name).get();
            if (parentParam == null) {
                parentParam = new ContractParam(name);
                setParamToContract(parentParam);
            }
            Map<String, ?> fieldMap = SerializationUtils.serialize(value);
            for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
                ContractParam param = new ContractParam(e.getKey());
                setValue(param, e.getValue());
                param.setParent(parentParam);
                setParamToContract(param);
                if (OBJECT.equals(param.type)) {
                    setObjectParam(e.getKey(), e.getValue());
                }
            }
        }

        //todo: nullpointer
        public Collection<?> getCollection(String name, Class<?> clazz) {
            List<ContractParam> childParams = getParamsByParentName(name);
            return childParams.stream().map(p -> getValue(p, clazz))
                    .collect(Collectors.toList());
        }

        public void setCollection(String name, Collection<?> values) {
            ContractParam parentParam = getParamByName(name).orElseGet(() -> {
                ContractParam p = new ContractParam(name);
                setParamToContract(p);
                return p;
            }) ;
            for (Object val : values) {
                ContractParam param = new ContractParam();
                setValue(param, val);
                param.setParent(parentParam);
                setParamToContract(param);
            }
        }

        public void setValue(ContractParam param, Object value) {
            if (value instanceof Long) {
                param.setType(LONG);
                param.setLongValue((Long) value);
            } else if (value instanceof Double) {
                param.setType(DOUBLE);
                param.setDoubleValue((Double) value);
            } else if (value instanceof Boolean) {
                param.setType(BOOLEAN);
                param.setBoolValue((Boolean) value);
            } else if(value instanceof LocalDate) {
                param.setType(DATE);
                param.setDateValue((LocalDate) value);
            } else if (value instanceof ContractParamObject){
                param.setType(OBJECT);
            } else {
                param.setType(STRING);
                param.setStringValue(value.toString());
            }
        }

        // todo: classcast
        private Object getValue(ContractParam param, Class<?> clazz) {
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
                case OBJECT:
                    return getObjectParam(param.getName(), clazz);
                default:
                    return null;
            }
        }

        private <T> void setParam(String name, T value,
                                  BiConsumer<ContractParam, T> setter) {
            ContractParam param = getParamByName(name).orElseGet(() -> {
                ContractParam p = new ContractParam(name);
                setParamToContract(p);
                return p;
            });
            setter.accept(param, value);
        }

        private <T> T getParam(String name,
                               Function<ContractParam, T> getter) {
            Optional<ContractParam> optParam = getParamByName(name);
            return optParam.isEmpty() ? null : getter.apply(optParam.get());
        }


        private Optional<ContractParam> getParamByName(String name) {
            return params.stream().filter(x -> name.equals(x.getName())).findFirst();
        }

        private List<ContractParam> getParamsByParentName(String name) {
            Optional<ContractParam> parent = getParamByName(name);
            if (parent.isEmpty()) {
                return null;
            }
            return params.stream()
                    .filter(param -> parent.get().equals(param.getParent()))
                    .collect(Collectors.toList());
        }

        private void setParamToContract(ContractParam param) {
            param.setContract(Contract2.this);
            params.add(param);
        }

    }

}
