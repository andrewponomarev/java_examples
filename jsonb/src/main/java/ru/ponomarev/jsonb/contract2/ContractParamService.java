package ru.ponomarev.jsonb.contract2;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.ponomarev.jsonb.contract2.ContractParamType.*;

@Data
public class ContractParamService {

    public void setLongParam(Contract2 contract, String name, Long value) {
        setParam(contract, name, value, ContractParam::setLongValue);
    }

    public Long getLongParam(Contract2 contract, String name) {
        return getParam(contract, name, ContractParam::getLongValue);
    }

    public void setDoubleParam(Contract2 contract, String name, Double value) {
        setParam(contract, name, value, ContractParam::setDoubleValue);
    }

    public Double getDoubleValue(Contract2 contract, String name) {
        return getParam(contract, name, ContractParam::getDoubleValue);
    }

    public void setBooleanParam(Contract2 contract, String name, Long value) {
        setParam(contract, name, value, ContractParam::setLongValue);
    }

    public Boolean getBooleanParam(Contract2 contract, String name) {
        return getParam(contract, name, ContractParam::getBoolValue);
    }

    public void setLocalDateParam(Contract2 contract, String name, LocalDate value) {
        setParam(contract, name, value, ContractParam::setDateValue);
    }

    public LocalDate getLocalDateParam(Contract2 contract, String name) {
        return getParam(contract, name, ContractParam::getDateValue);
    }

    public void setStringParam(Contract2 contract, String name, Object value) {
        setParam(contract, name, value.toString(), ContractParam::setStringValue);
    }

    public String getStringParam(Contract2 contract, String name) {
        return getParam(contract, name, ContractParam::getStringValue);
    }

    public <T> T getObjectParam(Contract2 contract, String name, Class<T> clazz) {
        List<ContractParam> childParams = getParamsByParentName(contract.getParams(), name);
        Map<String, ?> map = childParams.stream()
                .collect(Collectors.toMap(ContractParam::getId, p -> getValue(contract, p, clazz)));
        return SerializationUtils.deserialize(map, clazz);
    }

    public <T> void setObjectParam(Contract2 contract, String name, T value) {
        ContractParam parentParam = getParamByName(contract.getParams(), name).get();
        if (parentParam == null) {
            parentParam = new ContractParam(name);
            setParamToContract(contract, parentParam);
        }
        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
            ContractParam param = new ContractParam(e.getKey());
            setValue(param, e.getValue());
            param.setParent(parentParam);
            setParamToContract(contract, param);
            if (OBJECT.equals(param.type)) {
                setObjectParam(contract, e.getKey(), e.getValue());
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
                return getObjectParam(contract, param.getName(), clazz);
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
