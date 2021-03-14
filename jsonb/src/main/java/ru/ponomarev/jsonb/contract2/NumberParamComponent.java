package ru.ponomarev.jsonb.contract2;

import java.util.List;

import static ru.ponomarev.jsonb.contract2.ContractParamType.NUMBER;

public abstract class NumberParamComponent<T> extends BaseTypeParamComponent<T, Number> {

    public NumberParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    @Override
    Param setValueAndType(Param param, Number value) {
        return setValueAndType(param, value, Param::setNumberValue, NUMBER);
    }

    @Override
    Number getParamValue(String name, Class<? extends Number> clazz) {
        return SerializationUtils.deserialize(
                String.valueOf(getParamValue(name, Param::getNumberValue)), clazz);
    }

    private Object getNumberParamValue(Param param) {
        return null;
        //return SerializationUtils.deserialize(param.getNumberValue().toString(), ParamUtils.getClass(param));
    }
}
