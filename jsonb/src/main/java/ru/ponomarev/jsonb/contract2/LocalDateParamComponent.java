package ru.ponomarev.jsonb.contract2;

import java.time.LocalDate;
import java.util.List;

import static ru.ponomarev.jsonb.contract2.ContractParamType.DATE;

public abstract class LocalDateParamComponent<T> extends BaseTypeParamComponent<T, LocalDate> {

    public LocalDateParamComponent(T parentObject, List<Param> params) {
        super(parentObject, params);
    }

    @Override
    Param setValueAndType(Param param, LocalDate value) {
        return setValueAndType(param, value, Param::setDateValue, DATE);
    }

    @Override
    LocalDate getParamValue(String name, Class<? extends LocalDate> clazz) {
        return getParamValue(name, Param::getDateValue);
    }
}
