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
public class ContractParamHelper {

    private final Contract2 contract;

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

    protected @NotNull List<Param> getParamsByParentName(String name, Param p) {
        Optional<Param> parent = getParamByNameAndParent(name, p);
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

    protected void setParamToContract(Param param, Contract2 contract) {
        param.setContract(contract);
        contract.getParams().add(param);
    }

    protected <T> Supplier<? extends Param> createContractParamSupplier(
            @Nullable String name,
            @NotNull T value,
            ContractParamType type,
            Param parent,
            Class<T> clazz
    ) {
        return () -> {
            Param p = new Param(name);
            p.setType(type);
            p.setClassName(clazz == null ? value.getClass().getName() : clazz.getName());
            p.setParent(parent);
            setParamToContract(p, contract);
            return p;
        };
    }
}
