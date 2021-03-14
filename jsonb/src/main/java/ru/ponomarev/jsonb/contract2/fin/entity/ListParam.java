package ru.ponomarev.jsonb.contract2.fin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import ru.ponomarev.jsonb.contract2.fin.ParamFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("LIST")
@NoArgsConstructor
public class ListParam extends CompositeParam<List<?>> {

    public ListParam(String name, List<?> val,
                     Class<? extends List<?>> cls) {
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
    public List<?> get() {
        if (isNull) {
            return null;
        }
        return children.stream().map(Param::get).collect(Collectors.toList());
    }

    @Override
    public void set(Object value) {
        if (value == null) {
            clean();
            isNull = true;
            return;
        }
        if (!(value instanceof Collection<?>)) {
            return;
        }
        if (children == null) {
            children = new ArrayList<>();
        }
        List l = (List)value;
        for (int i = 0; i < l.size(); i++) {
            add(ParamFactory.create(String.valueOf(i), l.get(i)));
        }
    }
}
