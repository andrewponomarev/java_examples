package ru.ponomarev.jsonb.contract2;

public enum ContractParamType {

    STRING,
    LONG,
    DOUBLE,
    NUMBER,
    BOOLEAN,
    DATE,
    ENUM,
    OBJECT,
    COLLECTION;

    public static boolean isComplex(ContractParamType type) {
        return OBJECT.equals(type) || COLLECTION.equals(type);
    }

}