package ru.ponomarev.jsonb.contract2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import liquibase.pro.packaged.C;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.ponomarev.jsonb.contract.ContractParams;
import ru.ponomarev.jsonb.GeneralContract;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@TypeDefs({@TypeDef(name = "jsonb", typeClass = ContractParams.class)})
public class Contract2 extends GeneralContract {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade=CascadeType.ALL)
    @JsonIgnore
    private List<ContractParam> params = new ArrayList<>();

    private String value;

    @Override
    public String getStringParam() {
        Optional<ContractParam> param = getParamByName("string_param");
        return param.isPresent() ? param.get().getStringValue() : null;
    }

    @Override
    public void setStringParam(String s) {
       ContractParam param = getParamByName("string_param").orElse(new ContractParam("string_param"));
       param.setStringValue(s);
       param.setContract(this);
       params.add(param);
    }

    private Optional<ContractParam> getParamByName(String name) {
        return params.stream().filter(x -> name.equals(x.getName())).findFirst();
    }

    private void setLongParam(String name, Long value) {
        ContractParam param = getParamByName(name).orElse(new ContractParam(name));
        param.setLongValue(value);
        setParamToContract(param);
    }

    private void setDoubleParam(String name, Double value) {
        ContractParam param = getParamByName(name).orElse(new ContractParam(name));
        param.setDoubleValue(value);
        setParamToContract(param);
    }

    private void setBooleanParam(String name, Boolean value) {
        ContractParam param = getParamByName(name).orElse(new ContractParam(name));
        param.setBoolValue(value);
        setParamToContract(param);
    }

    private void setParamToContract(ContractParam param) {
        param.setContract(this);
        params.add(param);
    }

    private void setObjectParam(String name, Object object) {
        ContractParam param = getParamByName(name).orElse(new ContractParam(name));
        setParamToContract(param);
        for
    }
}
