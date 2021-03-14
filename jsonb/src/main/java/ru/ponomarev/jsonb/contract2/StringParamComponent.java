package ru.ponomarev.jsonb.contract2;

import java.util.List;

import static ru.ponomarev.jsonb.contract2.ContractParamType.BOOLEAN;

public abstract class StringParamComponent<T> extends BaseTypeParamComponent<T, String> {

    public StringParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    @Override
    Param setValueAndType(Param param, String value) {
        return setValueAndType(param, value, Param::setStringValue, BOOLEAN);
    }

    @Override
    String getParamValue(String name, Class<? extends String> clazz) {
        return getParamValue(name, Param::getStringValue);
    }
}
