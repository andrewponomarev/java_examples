package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.ponomarev.jsonb.GeneralContract;
import ru.ponomarev.jsonb.contract.ContractParams;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

@Entity
@Data
@TypeDefs({@TypeDef(name = "jsonb", typeClass = ContractParams.class)})
public class Contract2 extends GeneralContract {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<ContractParam> params = new ArrayList<>();

    private String value;

    public String getNamedStringParam() {
        return getStringParam("namedStringParam");
    }

    public void setNamedStringParam(String value) {
        setStringParam("namedStringParam", value);
    }

    public Long getLongNamedParam() {
        return getLongParam("namedLongParam");
    }

    public void setLongNamedParam(Long value) {
        setLongParam("namedLongParam", value);
    }

    public Double getDoubleNamedParam() {
        return getDoubleParam("namedDoubleParam");
    }

    public void setDoubleNamedParam(Double value) {
        setDoubleParam("namedDoubleParam", value);
    }

    public Boolean getBooleanNamedParam() {
        return getBooleanParam("namedBooleanParam");
    }

    public void setBooleanNamedParam(Boolean value) {
        setBooleanParam("namedDoubleParam", value);
    }

    public ContractParamObjectExample getContractParamObjectExample() {
        return getObjectParam( "namedObjectParam", ContractParamObjectExample.class);
    }

    public void setContractParamObjectExample(ContractParamObjectExample value) {
        setObjectParam("contractParamObjectExample", value);
    }


    public void setLongParam(String name, Long value) {
        setParam(this, name, value, ContractParam::setLongValue);
    }

    public Long getLongParam(String name) {
        return getParam(this, name, ContractParam::getLongValue);
    }

    public void setDoubleParam(String name, Double value) {
        setParam(this, name, value, ContractParam::setDoubleValue);
    }

    public Double getDoubleParam(String name) {
        return getParam(this, name, ContractParam::getDoubleValue);
    }

    public void setBooleanParam(String name, Boolean value) {
        setParam(this, name, value, ContractParam::setBoolValue);
    }

    public Boolean getBooleanParam(String name) {
        return getParam(this, name, ContractParam::getBoolValue);
    }

    public void setLocalDateParam(String name, LocalDate value) {
        setParam(this, name, value, ContractParam::setDateValue);
    }

    public LocalDate getLocalDateParam(String name) {
        return getParam(this, name, ContractParam::getDateValue);
    }

    public void setStringParam(String name, Object value) {
        setParam(this, name, value.toString(), ContractParam::setStringValue);
    }

    public String getStringParam(String name) {
        return getParam(this, name, ContractParam::getStringValue);
    }

    public <T> T getObjectParam(String name, Class<T> clazz) {
        List<ContractParam> childParams = getParamsByParentName(this.getParams(), name);
        Map<String, ?> map = childParams.stream()
                .collect(Collectors.toMap(ContractParam::getId, p -> getValue(this, p, clazz)));
        return SerializationUtils.deserialize(map, clazz);
    }

    public <T> void setObjectParam(String name, T value) {
        ContractParam parentParam = getParamByName(params, name).get();
        if (parentParam == null) {
            parentParam = new ContractParam(name);
            setParamToContract(this, parentParam);
        }
        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
            ContractParam param = new ContractParam(e.getKey());
            setValue(param, e.getValue());
            param.setParent(parentParam);
            setParamToContract(this, param);
            if (OBJECT.equals(param.type)) {
                setObjectParam(e.getKey(), e.getValue());
            }
        }
    }

    public Collection<?> getCollection(Contract2 contract, String name, Class<?> clazz) {
        List<ContractParam> childParams = getParamsByParentName(contract.getParams(), name);
        return childParams.stream().map(p -> getValue(contract, p, clazz))
                .collect(Collectors.toList());
    }

    public void setCollection(Contract2 contract, String name, Collection<?> values) {
        ContractParam parentParam = getParamByName(contract.getParams(), name).get();
        if (parentParam == null) {
            parentParam = new ContractParam(name);
            setParamToContract(contract, parentParam);
        }
        for (Object val : values) {
            ContractParam param = new ContractParam();
            setValue(param, val);
            param.setParent(parentParam);
            setParamToContract(contract, param);
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

    private Object getValue(Contract2 contract, ContractParam param, Class<?> clazz) {
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

    private <T> void setParam(Contract2 contract, String name, T value,
                              BiConsumer<ContractParam, T> setter) {
        ContractParam param = getParamByName(contract.getParams(), name).get();
        if (param == null) {
            param = new ContractParam(name);
            setParamToContract(contract, param);
        }
        setter.accept(param, value);
    }

    private <T> T getParam(Contract2 contract, String name,
                           Function<ContractParam, T> getter) {
        ContractParam param = getParamByName(contract.getParams(), name).get();
        return param == null ? null : getter.apply(param);
    }


    private Optional<ContractParam> getParamByName(List<ContractParam> params, String name) {
        return params.stream().filter(x -> name.equals(x.getName())).findFirst();
    }

    private List<ContractParam> getParamsByParentName(List<ContractParam> params, String name) {
        Optional<ContractParam> parent = getParamByName(params, name);
        if (parent.isEmpty()) {
            return null;
        }
        return params.stream()
                .filter(param -> parent.equals(param.getParent()))
                .collect(Collectors.toList());
    }

    private void setParamToContract(Contract2 contract, ContractParam param) {
        param.setContract(contract);
        contract.getParams().add(param);
    }

}
