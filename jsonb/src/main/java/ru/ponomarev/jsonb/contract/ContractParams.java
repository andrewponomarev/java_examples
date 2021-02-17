package ru.ponomarev.jsonb.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Data
public class ContractParams implements UserType {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    private Boolean boolParam;

    private Integer numParam;

    private String stringParam;

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return this.getClass();
    }

    @Override
    public boolean equals(final Object obj1, final Object obj2) throws HibernateException {
        if (obj1 == null) {
            return obj2 == null;
        }
        return obj1.equals(obj2);
    }

    @Override
    public int hashCode(final Object obj) throws HibernateException {
        return obj.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        if (rs.getString(names[0]) == null) {
            return null;
        }
        try {
            return objectMapper.readValue(rs.getString(names[0]).getBytes("UTF-8"), returnedClass());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert String to Invoice: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
            return;
        }
        try {
            st.setObject(index, objectMapper.writeValueAsString(value), Types.OTHER);
        } catch (final Exception ex) {
            throw new RuntimeException("Failed to convert Invoice to String: " + ex.getMessage(), ex);
        }
    }

    @Override
    public Object deepCopy(final Object value) throws HibernateException {
        Object copy = null;
        try {
            copy = objectMapper.readValue(objectMapper.writeValueAsBytes(value), this.returnedClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copy;
    }

    @Override
    @JsonIgnore
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) this.deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return this.deepCopy(o);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return this.deepCopy(o);
    }
}
