package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Contract extends GeneralContract {

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
    private ContractParamComponent contractParamComponent = new ContractParamComponent();


    public String getNamedStringParam() {
        return contractParamComponent.getStringParam(STRING_NAMED_PARAM);
    }

    public void setNamedStringParam(String value) {
        contractParamComponent.setParam(STRING_NAMED_PARAM, value);
    }

    public Long getLongNamedParam() {
        return contractParamComponent.getLongParamValue(LONG_NAMED_PARAM);
    }

    public void setLongNamedParam(Long value) {
        contractParamComponent.setParam(LONG_NAMED_PARAM, value);
    }

    public Double getDoubleNamedParam() {
        return contractParamComponent.getDoubleParamValue(DOUBLE_NAMED_PARAM);
    }

    public void setDoubleNamedParam(Double value) {
        contractParamComponent.setParam(DOUBLE_NAMED_PARAM, value);
    }

    public LocalDate getLocalDateNamedParam() {
        return contractParamComponent.getLocalDateParam(DATE_NAMED_PARAM);
    }

    public void setLocalDateNamedParam(LocalDate value) {
        contractParamComponent.setParam(DATE_NAMED_PARAM, value);
    }


    public Boolean getBooleanNamedParam() {
        return contractParamComponent.getBooleanParamValue(BOOLEAN_NAMED_PARAM);
    }

    public void setBooleanNamedParam(Boolean value) {
        contractParamComponent.setParam(BOOLEAN_NAMED_PARAM, value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return contractParamComponent.getContractParamObjectExampleParam(CONTRACT_PARAM_OBJECT_EXAMPLE);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        contractParamComponent.setParam(CONTRACT_PARAM_OBJECT_EXAMPLE, value);
    }

    public ContractParamType getEnumParam() {
        return contractParamComponent.getEnumParamValue(ENUM_PARAM, ContractParamType.class);
    }

    public void setEnumParam(ContractParamType value) {
        contractParamComponent.setParam(ENUM_PARAM, value);
    }

    public Collection<?> getCollectionLongNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_LONG, Long.class);
    }

    public void setCollectionLongNamedParam(Collection<? extends Long> value) {
        contractParamComponent.setParam(COLLECTION_LONG, value);
    }

    public Collection<?> getCollectionDoubleNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_DOUBLE, Double.class);
    }

    public void setCollectionDoubleNamedParam(Collection<? extends Double> value) {
        contractParamComponent.setParam(COLLECTION_DOUBLE, value);
    }

    public Collection<?> getCollectionBooleanNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_BOOLEAN, Boolean.class);
    }

    public void setCollectionBooleanNamedParam(Collection<? extends Boolean> value) {
        contractParamComponent.setParam(COLLECTION_BOOLEAN, value);
    }

    public Collection<?> getCollectionDateNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_DATE, LocalDate.class);
    }

    public void setCollectionDateNamedParam(Collection<? extends LocalDate> value) {
        contractParamComponent.setParam(COLLECTION_DATE, value);
    }

    public Collection<?> getCollectionStringNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_STRING, String.class);
    }

    public void setCollectionStringNamedParam(Collection<? extends String> value) {
        contractParamComponent.setParam(COLLECTION_STRING, value);
    }

    public Collection<?> getCollectionSomeObjectNamedParam() {
        return contractParamComponent.getCollection(COLLECTION_ENUM, String.class);
    }

    public void setCollectionSomeObject(Collection<? extends Object> value) {
        contractParamComponent.setParam(COLLECTION_ENUM, value);
    }

    public BigDecimal getBigDecimalParam() {
        return contractParamComponent.getBigDecimalParamValue(BIG_DECIMAL);
    }

    public void setBigDecimalParam(BigDecimal value) {
        contractParamComponent.setParam(BIG_DECIMAL, value);
    }


    private class ContractParamComponent extends AbstractParamComponent<Contract> {

        ContractParamComponent() {
            super(Contract.this, Contract.this.params);
        }

        @Override
        void setParamToParentObject(Param param, Contract parentObject) {
            param.setContract(parentObject);
        }
    }


}
