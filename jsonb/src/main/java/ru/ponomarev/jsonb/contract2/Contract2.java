package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.Nullable;
import ru.ponomarev.jsonb.GeneralContract;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public static final String ENUM_PARAM = "enumField";

    public static String CONTRACT_PARAM_OBJECT_EXAMPLE = "ContractParamObjectExample";

    public static String COLLECTION_LONG = "collectionLong";
    public static String COLLECTION_DOUBLE = "collectionDouble";
    public static String COLLECTION_BOOLEAN = "collectionBoolean";
    public static String COLLECTION_DATE = "collectionDate";
    public static String COLLECTION_STRING = "collectionString";
    public static String COLLECTION_ENUM = "collectionEnum";


    @Transient
    private ContractParamGetter contractParamGetter = new ContractParamGetter();

    @Transient
    private ContractParamSetter contractParamSetter = new ContractParamSetter();


    public String getNamedStringParam() {
        return contractParamGetter.getStringParam(STRING_NAMED_PARAM);
    }

    public void setNamedStringParam(String value) {
        contractParamSetter.setParam(STRING_NAMED_PARAM, value);
    }

    public Long getLongNamedParam() {
        return contractParamGetter.getLongParam(LONG_NAMED_PARAM);
    }

    public void setLongNamedParam(Long value) {
        contractParamSetter.setParam(LONG_NAMED_PARAM, value);
    }

    public Double getDoubleNamedParam() {
        return contractParamGetter.getDoubleParam(DOUBLE_NAMED_PARAM);
    }

    public void setDoubleNamedParam(Double value) {
        contractParamSetter.setParam(DOUBLE_NAMED_PARAM, value);
    }

    public LocalDate getLocalDateNamedParam() {
        return contractParamGetter.getLocalDateParam(DATE_NAMED_PARAM);
    }

    public void setLocalDateNamedParam(LocalDate value) {
        contractParamSetter.setParam(DATE_NAMED_PARAM, value);
    }


    public Boolean getBooleanNamedParam() {
        return contractParamGetter.getBooleanParam(BOOLEAN_NAMED_PARAM);
    }

    public void setBooleanNamedParam(Boolean value) {
        contractParamSetter.setParam(BOOLEAN_NAMED_PARAM, value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return (ContractParamObjectExample) contractParamGetter.getObjectParam(CONTRACT_PARAM_OBJECT_EXAMPLE, ContractParamObjectExample.class);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        contractParamSetter.setParam(CONTRACT_PARAM_OBJECT_EXAMPLE, value);
    }

    public ContractParamType getEnumParam() {
        return contractParamGetter.getEnumParam(ENUM_PARAM, ContractParamType.class);
    }

    public void setEnumParam(ContractParamType value) {
        contractParamSetter.setParam(ENUM_PARAM, value);
    }

    public Collection<?> getCollectionLongNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_LONG, Long.class);
    }

    public void setCollectionLongNamedParam(Collection<? extends Long> value) {
        contractParamSetter.setParam(COLLECTION_LONG, value);
    }

    public Collection<?> getCollectionDoubleNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_DOUBLE, Double.class);
    }

    public void setCollectionDoubleNamedParam(Collection<? extends Double> value) {
        contractParamSetter.setParam(COLLECTION_DOUBLE, value);
    }

    public Collection<?> getCollectionBooleanNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_BOOLEAN, Boolean.class);
    }

    public void setCollectionBooleanNamedParam(Collection<? extends Boolean> value) {
        contractParamSetter.setParam(COLLECTION_BOOLEAN, value);
    }

    public Collection<?> getCollectionDateNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_DATE, LocalDate.class);
    }

    public void setCollectionDateNamedParam(Collection<? extends LocalDate> value) {
        contractParamSetter.setParam(COLLECTION_DATE, value);
    }

    public Collection<?> getCollectionStringNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_STRING, String.class);
    }

    public void setCollectionStringNamedParam(Collection<? extends String> value) {
        contractParamSetter.setParam(COLLECTION_STRING, value);
    }

    public Collection<?> getCollectionSomeObjectNamedParam() {
        return contractParamGetter.getCollection(COLLECTION_ENUM, String.class);
    }

    public void setCollectionSomeObject(Collection<? extends Object> value) {
        contractParamSetter.setParam(COLLECTION_ENUM, value);
    }

    private class ContractParamGetter extends AbstractContractParamGetter {

        ContractParamGetter() {
            super(Contract2.this);
        }

        public Long getLongParam(String name) {
            return getLongParam(name, null);
        }

        public Double getDoubleParam(String name) {
            return getDoubleParam(name, null);
        }

        public Boolean getBooleanParam(String name) {
            return getBooleanParam(name,null);
        }

        public LocalDate getLocalDateParam(String name) {
            return getLocalDateParam(name, null);
        }

        public String getStringParam(String name) {
            return getStringParam(name, null);
        }

        public <T extends Enum<T>> T getEnumParam(@NotNull String name, Class enumClass) {
            return getEnumParam(name, enumClass, null);
        }

        public Object getObjectParam(String name, Class<?> clazz) {
            return getObjectParam(name, clazz, null);
        }

        public Collection<?> getCollection(String name, Class<?> clazz) {
            return getCollection(name, clazz, null);
        }
    }

    private class ContractParamSetter extends AbstractContractParamSetter {

        ContractParamSetter() {
            super(Contract2.this);
        }

        public ContractParam setParam(@Nullable String name, Long value) {
            return setParam(name, value, null);
        }

        public ContractParam setParam(@Nullable String name, Double value) {
            return setParam(name, value, null);
        }

        public ContractParam setParam(@Nullable String name, Boolean value) {
            return setParam(name, value, null);
        }

        public ContractParam setParam(@Nullable String name, LocalDate value) {
            return setParam(name, value, null);
        }

        public ContractParam setParam(@Nullable String name, String value) {
            return setParam(name, value, null);
        }

        public <T extends Enum<T>> ContractParam setParam(String name, T value) {
            return setParam(name, value, null);
        }

        public <T extends ContractParamObject> ContractParam setParam(String name, T value) {
            return setParam(name, value, null);
        }

        public ContractParam setParam(String name, Collection<?> values) {
            return setParam(name, values, null);
        }
    }


}
