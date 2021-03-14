package ru.ponomarev.jsonb.contract2.fin;

import com.sun.istack.NotNull;
import ru.ponomarev.jsonb.contract2.Contract;
import ru.ponomarev.jsonb.contract2.fin.entity.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParamSuperObject {

    private final List<Param<?>> params;

    private final Contract contract;

    public ParamSuperObject(Contract parentObject, List<Param<?>> params) {
        this.contract = parentObject;
        this.params = new ArrayList<>();
        for(Param<?> p : params) {
            if (p.getParent() == null) {
                params.add(p);
            }
        }
    }

    public <T> void set(@NotNull String name, T value) {
        Optional<Param<?>> optParam = getByName(name);
        Param<?> p;
        if (!optParam.isPresent()) {
            if (value == null) {
                return;
            } else {
                p = ParamFactory.create(name, value);
                p.setContract(contract);
                params.add(p);
            }
        } else {
            p = optParam.get();
            p.set(value);
            p.setIsNull(value == null);
        }
    }

    public <T> T get(@NotNull String name, Class<T> clazz) {
        Optional<Param<?>> optParam = getByName(name);
        Param<T> p;
        if (!optParam.isPresent()) {
            return null;
        }
        try {
            p = (Param<T>) optParam.get();
        } catch (Exception e) {
            return null;
        }
        return p.get();
    }

    private Optional<Param<?>> getByName(String name) {
        return params.stream().filter(p -> name.equals(p.getName())).findFirst();
    }
}
