package ru.ponomarev.jsonb;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;


@Data
public abstract class GeneralContract {

    public abstract String getStringParam();

    public abstract void setStringParam(String s);

    public abstract String getId();
}
