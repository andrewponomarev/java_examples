package ru.ponomarev.jsonb.contract2.fin.entity;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("DATE")
@NoArgsConstructor
public class LocalDateParam extends SimpleParam<LocalDate> {

    private LocalDate dateValue;

    public LocalDateParam(String name) {
        super(name);
    }

    @Override
    public LocalDate get() {
        return dateValue;
    }

    @Override
    void setValue(LocalDate value) {
        dateValue = value;
    }
}
