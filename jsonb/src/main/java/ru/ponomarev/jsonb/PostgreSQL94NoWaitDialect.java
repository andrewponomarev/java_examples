package ru.ponomarev.jsonb;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class PostgreSQL94NoWaitDialect extends PostgreSQL94Dialect {

    public PostgreSQL94NoWaitDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "jsonb");
    }
}
