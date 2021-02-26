package ru.ponomarev.jsonb.contract2;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Collection;

@Data
@EqualsAndHashCode
public class ContractParamObjectExample implements ContractParamObject {

    private Long l;

    private Double d;

    private String s;

    private Boolean b;

    private LocalDate ld;

    private ContractParamObjectExample cpoe;

    private Class clazz;

    private ContractParamType type;

    private Collection<ContractParamObjectExample> col;

}
