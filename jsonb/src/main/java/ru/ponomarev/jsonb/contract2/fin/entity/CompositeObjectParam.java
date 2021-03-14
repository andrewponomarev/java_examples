package ru.ponomarev.jsonb.contract2.fin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import ru.ponomarev.jsonb.contract2.SerializationUtils;
import ru.ponomarev.jsonb.contract2.fin.ParamFactory;

import javax.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("OBJECT")
@NoArgsConstructor
public class CompositeObjectParam extends CompositeParam<Object> {

    public CompositeObjectParam(String name) {
        super(name);
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Param<?>> children = new ArrayList<>();

    @Override
    void add(Param<?> c) {
        children.add(c);
        c.setParent(this);
    }

    @Override
    void clean() {
        children = new ArrayList<>();
    }


    @Override
    public Object get() {
        Map<String, Object> map = new HashMap<>();
        for (Param<?> p : children) {
            if (p.getName() == null) {
                continue;
            }
            Object val = p.get();
            if (val == null) {
                continue;
            }
            map.put(p.getName(), val);
        }
        return SerializationUtils.deserialize(map, this.cls);
    }


    @Override
    public void set(Object value) {
        if (value == null) {
            clean();
            return;
        }
        Map<String, ?> fieldMap = SerializationUtils.serialize(value);
        for (Map.Entry<String, ?> e : fieldMap.entrySet()) {
            Optional<Param<?>> optP = children.stream().filter(
                    x -> e.getKey().equals(x.getName()))
                    .findFirst();
            Param<?> param;
            if (!optP.isPresent()) {
                if (e.getValue() == null) {
                    continue;
                }
                param = ParamFactory.create(e.getKey(), e.getValue());
                add(param);
            } else {
                optP.get().set(e.getValue());
            }
        }
    }
}
