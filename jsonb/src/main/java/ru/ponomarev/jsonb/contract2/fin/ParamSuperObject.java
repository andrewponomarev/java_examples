package ru.ponomarev.jsonb.contract2.fin;

import com.sun.istack.NotNull;
import ru.ponomarev.jsonb.contract2.Contract;
import ru.ponomarev.jsonb.contract2.fin.entity.Param;

import java.util.List;
import java.util.Optional;

public class ParamSuperObject {

    private final List<Param<?>> paramsTree;

    private final Contract contract;

    public ParamSuperObject(Contract parentObject, List<Param<?>> params) {
        this.contract = parentObject;
        this.paramsTree = params;
    }

    public <T> void set(@NotNull String name, T value) {
        Optional<Param<?>> optParam = getByName(name);
        Param<?> p;
        if (!optParam.isPresent()) {
            if (value == null) {
                return;
            } else {
                p = ParamFactory.create(name, value);
                addParam(p);
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
        return paramsTree.stream().filter(p -> name.equals(p.getName())).findFirst();
    }

    private void addParam(Param<?> p) {
        paramsTree.add(p);
        p.setContract(contract);
    }
}
