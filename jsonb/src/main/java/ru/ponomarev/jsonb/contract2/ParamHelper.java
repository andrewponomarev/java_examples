package ru.ponomarev.jsonb.contract2;

import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public abstract class ParamHelper<T> {

    private final T parentObject;

    private final List<Param> params;

    protected Optional<Param> getParamByNameAndParent(
            @Nullable String name,
            @Nullable Param parent) {
        if (name == null) {
            return Optional.empty();
        }
        return params.stream()
                .filter(x -> name.equals(x.getName()))
                .filter(x -> parent == null || parent.equals(x.getParent()))
                .findFirst();
    }

    protected Optional<Param> getRootParamByName(@NotNull String name) {
        return params.stream()
                .filter(x -> name.equals(x.getName()))
                .filter(x -> x.getParent() == null)
                .findFirst();
    }

    protected @NotNull List<Param> getParamsByParentName(String name) {
        Optional<Param> parent = getRootParamByName(name);
        if (parent.isEmpty()) {
            return Collections.emptyList();
        }
        return params.stream()
                .filter(param -> parent.get().equals(param.getParent()))
                .collect(Collectors.toList());
    }

    protected @NotNull List<Param> getParamsByParent(Param parent) {
        if (parent == null) {
            return Collections.emptyList();
        }
        return params.stream()
                .filter(param -> parent.equals(param.getParent()))
                .collect(Collectors.toList());
    }

    abstract void setParamToParentObject(Param param, T parentObject);

    protected <T> Supplier<? extends Param> createContractParamSupplier(
            String name,
            ContractParamType type,
            T value,
            Param parent,
            @NotNull Class<T> clazz
    ) {
        return () -> {
            if (value == null) {
                return null;
            }
            Param p = new Param(name);
            p.setType(type);
            if (clazz != null) {
                p.setClassName(clazz.getName());
            }
            p.setParent(parent);
            params.add(p);
            setParamToParentObject(p, parentObject);
            return p;
        };
    }
}
