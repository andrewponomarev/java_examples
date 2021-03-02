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

//    public void setLongParam(Contract2 contract, String name, Long value) {
//        setParam(contract, name, value, Param::set);
//    }
//
//    public Long getLongParam(Contract2 contract, String name) {
//        return getParam(contract, name, Param::getLongValue);
//    }
//
//    public void setDoubleParam(Contract2 contract, String name, Double value) {
//        setParam(contract, name, value, Param::setDoubleValue);
//    }
//
//    public Double getDoubleValue(Contract2 contract, String name) {
//        return getParam(contract, name, Param::getDoubleValue);
//    }
//
//    public void setBooleanParam(Contract2 contract, String name, Long value) {
//        setParam(contract, name, value, Param::setLongValue);
//    }
//
//    public Boolean getBooleanParam(Contract2 contract, String name) {
//        return getParam(contract, name, Param::getBoolValue);
//    }
//
//    public void setLocalDateParam(Contract2 contract, String name, LocalDate value) {
//        setParam(contract, name, value, Param::setDateValue);
//    }
//
//    public LocalDate getLocalDateParam(Contract2 contract, String name) {
//        return getParam(contract, name, Param::getDateValue);
//    }
//
//    public void setStringParam(Contract2 contract, String name, Object value) {
//        setParam(contract, name, value.toString(), Param::setStringValue);
//    }
//
//    public String getStringParam(Contract2 contract, String name) {
//        return getParam(contract, name, Param::getStringValue);
//    }
//
//    public <T> T getObjectParam(Contract2 contract, String name, Class<T> clazz) {
//        List<Param> childParams = getParamsByParentName(contract.getParams(), name);
//        Map<String, ?> map = childParams.stream()
//                .collect(Collectors.toMap(Param::getId, p -> getValue(contract, p, clazz)));
//        return SerializationUtils.deserialize(map, clazz);
//    }
//
//    public <T> void setObjectParam(Contract2 contract, String name, T value) {
//        Param parentParam = getParamByName(contract.getParams(), name).get();
//        if (parentParam == null) {
//            parentParam = new Param(name);
//            setParamToContract(contract, parentParam);
//        }
//        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
//        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
//            Param param = new Param(e.getKey());
//            setValue(param, e.getValue());
//            param.setParent(parentParam);
//            setParamToContract(contract, param);
//            if (OBJECT.equals(param.type)) {
//                setObjectParam(contract, e.getKey(), e.getValue());
//            }
//        }
//    }
//
//    public Collection<?> getCollection(Contract2 contract, String name, Class<?> clazz) {
//        List<Param> childParams = getParamsByParentName(contract.getParams(), name);
//        return childParams.stream().map(p -> getValue(contract, p, clazz))
//                .collect(Collectors.toList());
//    }
//
//    public void setCollection(Contract2 contract, String name, Collection<?> values) {
//        Param parentParam = getParamByName(contract.getParams(), name).get();
//        if (parentParam == null) {
//            parentParam = new Param(name);
//            setParamToContract(contract, parentParam);
//        }
//        for (Object val : values) {
//            Param param = new Param();
//            setValue(param, val);
//            param.setParent(parentParam);
//            setParamToContract(contract, param);
//        }
//    }
//
//    public void setValue(Param param, Object value) {
//        if (value instanceof Long) {
//            param.setType(LONG);
//            param.setLongValue((Long) value);
//        } else if (value instanceof Double) {
//            param.setType(DOUBLE);
//            param.setDoubleValue((Double) value);
//        } else if (value instanceof Boolean) {
//            param.setType(BOOLEAN);
//            param.setBoolValue((Boolean) value);
//        } else if(value instanceof LocalDate) {
//            param.setType(DATE);
//            param.setDateValue((LocalDate) value);
//        } else if (value instanceof ContractParamObject){
//            param.setType(OBJECT);
//        } else {
//            param.setType(STRING);
//            param.setStringValue(value.toString());
//        }
//    }
//
//    private Object getValue(Contract2 contract, Param param, Class<?> clazz) {
//        switch (param.getType()) {
//            case STRING:
//                return param.getStringValue();
//            case LONG:
//                return param.getLongValue();
//            case DOUBLE:
//                return param.getDoubleValue();
//            case BOOLEAN:
//                return param.getBoolValue();
//            case DATE:
//                return param.getDateValue();
//            case OBJECT:
//                return getObjectParam(contract, param.getName(), clazz);
//            default:
//                return null;
//        }
//    }
//
//    private <T> void setParam(Contract2 contract, String name, T value,
//                              BiConsumer<Param, T> setter) {
//        Param param = getParamByName(contract.getParams(), name).get();
//        if (param == null) {
//            param = new Param(name);
//            setParamToContract(contract, param);
//        }
//        setter.accept(param, value);
//    }
//
//    private <T> T getParam(Contract2 contract, String name,
//                           Function<Param, T> getter) {
//        Param param = getParamByName(contract.getParams(), name).get();
//        return param == null ? null : getter.apply(param);
//    }
//
//
//    private Optional<Param> getParamByName(List<Param> params, String name) {
//        return params.stream().filter(x -> name.equals(x.getName())).findFirst();
//    }
//
//    private List<Param> getParamsByParentName(List<Param> params, String name) {
//        Optional<Param> parent = getParamByName(params, name);
//        if (parent.isEmpty()) {
//            return null;
//        }
//        return params.stream()
//                .filter(param -> parent.equals(param.getParent()))
//                .collect(Collectors.toList());
//    }
//
//    private void setParamToContract(Contract2 contract, Param param) {
//        param.setContract(contract);
//        contract.getParams().add(param);
//    }
}
