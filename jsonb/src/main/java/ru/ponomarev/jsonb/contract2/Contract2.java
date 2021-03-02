package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.ponomarev.jsonb.GeneralContract;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private List<Param> params = new ArrayList<>();

    private String value;

    public static final String LONG_NAMED_PARAM = "namedLongParam";
    public static final String DOUBLE_NAMED_PARAM = "namedDoubleParam";
    public static final String BOOLEAN_NAMED_PARAM = "namedBooleanParam";
    public static final String DATE_NAMED_PARAM = "localDateParam";
    public static final String STRING_NAMED_PARAM = "namedStringParam";
    public static final String ENUM_PARAM = "enumField";
    private static final String BIG_DECIMAL = "bigDecimal";

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
        return contractParamGetter.getLongParamValue(LONG_NAMED_PARAM);
    }

    public void setLongNamedParam(Long value) {
        contractParamSetter.setParam(LONG_NAMED_PARAM, value);
    }

    public Double getDoubleNamedParam() {
        return contractParamGetter.getDoubleParamValue(DOUBLE_NAMED_PARAM);
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
        return contractParamGetter.getBooleanParamValue(BOOLEAN_NAMED_PARAM);
    }

    public void setBooleanNamedParam(Boolean value) {
        contractParamSetter.setParam(BOOLEAN_NAMED_PARAM, value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return contractParamGetter.getContractParamObjectExampleParam(CONTRACT_PARAM_OBJECT_EXAMPLE);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        contractParamSetter.setParam(CONTRACT_PARAM_OBJECT_EXAMPLE, value);
    }

    public ContractParamType getEnumParam() {
        return contractParamGetter.getEnumParamValue(ENUM_PARAM, ContractParamType.class);
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

    public BigDecimal getBigDecimalParam() {
        return contractParamGetter.getBigDecimalParamValue(BIG_DECIMAL);
    }

    public void setBigDecimalParam(BigDecimal value) {
        contractParamSetter.setParam(BIG_DECIMAL, value);
    }

    private class ContractParamGetter extends AbstractContractParamGetter {

        ContractParamGetter() {
            super(Contract2.this, Contract2.this.getParams());
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
    }

    private class ContractParamSetter extends AbstractContractParamSetter {

        ContractParamSetter() {
            super(Contract2.this, Contract2.this.params);
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
    }


}
