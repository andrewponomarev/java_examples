package ru.ponomarev.jsonb.contract2;

import java.util.List;

import static ru.ponomarev.jsonb.contract2.ContractParamType.BOOLEAN;

public abstract class BooleanParamComponent<T> extends BaseTypeParamComponent<T, Boolean> {

    public BooleanParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    @Override
    Param setValueAndType(Param param, Boolean value) {
        return setValueAndType(param, value, Param::setBoolValue, BOOLEAN);
    }

    @Override
    Boolean getParamValue(String name, Class<? extends Boolean> clazz) {
        return getParamValue(name, Param::getBoolValue);
    }

}
