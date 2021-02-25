package ru.ponomarev.jsonb;

import lombok.Data;


@Data
public abstract class GeneralContract {

    public abstract String getNamedStringParam();

    public abstract void setNamedStringParam(String s);

    public abstract String getId();
}
