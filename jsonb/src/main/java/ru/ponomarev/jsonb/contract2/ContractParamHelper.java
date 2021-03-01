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

    protected Optional<ContractParam> getParamByNameAndParent(
            @Nullable String name,
            @Nullable ContractParam parent) {
        if (name == null) {
            return Optional.empty();
        }
        return contract.getParams().stream()
                .filter(x -> name.equals(x.getName()))
                .filter(x -> parent == null || parent.equals(x.getParent()))
                .findFirst();
    }

    protected @NotNull List<ContractParam> getParamsByParentName(String name, ContractParam p) {
        Optional<ContractParam> parent = getParamByNameAndParent(name, p);
        if (parent.isEmpty()) {
            return Collections.emptyList();
        }
        return contract.getParams().stream()
                .filter(param -> parent.get().equals(param.getParent()))
                .collect(Collectors.toList());
    }

    protected @NotNull List<ContractParam> getParamsByParent(ContractParam parent) {
        if (parent == null) {
            return Collections.emptyList();
        }
        return contract.getParams().stream()
                .filter(param -> parent.equals(param.getParent()))
                .collect(Collectors.toList());
    }

    protected void setParamToContract(ContractParam param, Contract2 contract) {
        param.setContract(contract);
        contract.getParams().add(param);
    }

    protected <T> Supplier<? extends ContractParam> createContractParamSupplier(
            @Nullable String name,
            @NotNull T value,
            ContractParamType type,
            ContractParam parent,
            Class<T> clazz
    ) {
        return () -> {
            ContractParam p = new ContractParam(name);
            p.setType(type);
            p.setClassName(clazz == null ? value.getClass().getName() : clazz.getName());
            p.setParent(parent);
            setParamToContract(p, contract);
            return p;
        };
    }
}
