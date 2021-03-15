package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import ru.ponomarev.jsonb.GeneralContract;
import ru.ponomarev.jsonb.contract2.fin.ParamComponent;
import ru.ponomarev.jsonb.contract2.fin.entity.Param;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade=CascadeType.MERGE, orphanRemoval=true)
    @JsonIgnore
    private List<Param<?>> params = new ArrayList<>();

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
    private ContractParamComponent contractParamComponent;

    public String getNamedStringParam() {
        return contractParamComponent.get(STRING_NAMED_PARAM, String.class);
    }

    public void setNamedStringParam(String value) {
        contractParamComponent.set(STRING_NAMED_PARAM, value);
    }

    public Long getLongNamedParam() {
        return contractParamComponent.get(LONG_NAMED_PARAM, Long.class);
    }

    public void setLongNamedParam(Long value) {
        contractParamComponent.set(LONG_NAMED_PARAM, value);
    }

    public Double getDoubleNamedParam() {
        return contractParamComponent.get(DOUBLE_NAMED_PARAM, Double.class);
    }

    public void setDoubleNamedParam(Double value) {
        contractParamComponent.set(DOUBLE_NAMED_PARAM, value);
    }

    public LocalDate getLocalDateNamedParam() {
        return contractParamComponent.get(DATE_NAMED_PARAM, LocalDate.class);
    }

    public void setLocalDateNamedParam(LocalDate value) {
        contractParamComponent.set(DATE_NAMED_PARAM, value);
    }


    public Boolean getBooleanNamedParam() {
        return contractParamComponent.get(BOOLEAN_NAMED_PARAM, Boolean.class);
    }

    public void setBooleanNamedParam(Boolean value) {
        contractParamComponent.set(BOOLEAN_NAMED_PARAM, value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return contractParamComponent.get(CONTRACT_PARAM_OBJECT_EXAMPLE, ContractParamObjectExample.class);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        contractParamComponent.set(CONTRACT_PARAM_OBJECT_EXAMPLE, value);
    }

    public ContractParamType getEnumParam() {
        return contractParamComponent.get(ENUM_PARAM, ContractParamType.class);
    }

    public void setEnumParam(ContractParamType value) {
        contractParamComponent.set(ENUM_PARAM, value);
    }

    public Collection<?> getCollectionLongNamedParam() {
        return contractParamComponent.get(COLLECTION_LONG, Collection.class);
    }

    public void setCollectionLongNamedParam(Collection<? extends Long> value) {
        contractParamComponent.set(COLLECTION_LONG, value);
    }

    public Collection<?> getCollectionDoubleNamedParam() {
        return contractParamComponent.get(COLLECTION_DOUBLE, Collection.class);
    }

    public void setCollectionDoubleNamedParam(Collection<? extends Double> value) {
        contractParamComponent.set(COLLECTION_DOUBLE, value);
    }

    public Collection<?> getCollectionBooleanNamedParam() {
        return contractParamComponent.get(COLLECTION_BOOLEAN, Collection.class);
    }

    public void setCollectionBooleanNamedParam(Collection<? extends Boolean> value) {
        contractParamComponent.set(COLLECTION_BOOLEAN, value);
    }

    public Collection<?> getCollectionDateNamedParam() {
        return contractParamComponent.get(COLLECTION_DATE, Collection.class);
    }

    public void setCollectionDateNamedParam(Collection<? extends LocalDate> value) {
        contractParamComponent.set(COLLECTION_DATE, value);
    }

    public Collection<?> getCollectionStringNamedParam() {
        return contractParamComponent.get(COLLECTION_STRING, Collection.class);
    }

    public void setCollectionStringNamedParam(Collection<? extends String> value) {
        contractParamComponent.set(COLLECTION_STRING, value);
    }

    public Collection<?> getCollectionSomeObjectNamedParam() {
        return contractParamComponent.get(COLLECTION_ENUM, Collection.class);
    }

    public void setCollectionSomeObject(Collection<? extends Object> value) {
        contractParamComponent.set(COLLECTION_ENUM, value);
    }

    public BigDecimal getBigDecimalParam() {
        return contractParamComponent.get(BIG_DECIMAL, BigDecimal.class);
    }

    public void setBigDecimalParam(BigDecimal value) {
        contractParamComponent.set(BIG_DECIMAL, value);
    }


    @PostLoad
    private void initContractParamComponent() {
        contractParamComponent = new ContractParamComponent();
    }

    private class ContractParamComponent extends ParamComponent {

        public ContractParamComponent() {
            super(params);
        }

        public void linkWithParent(Param<?> p) {
            p.setContract(Contract.this);
        }
    }

}
