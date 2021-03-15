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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", cascade= CascadeType.MERGE, orphanRemoval=true)
    @JsonIgnore
    private List<Param<?>> children = new ArrayList<>();

    @Override
    void add(Param<?> c) {
        children.add(c);
        c.setParent(this);
    }

    @Override
    void clear() {
        children.clear();
    }

    @Override
    public List<Param<?>> getChildren() {
        return children;
    }

    @Override
    public List<?> getValue() {
        return children.stream().map(Param::get).collect(Collectors.toList());
    }

    @Override
    public void setValue(Object value) {
        if (!(value instanceof Collection<?>)) {
            return;
        }
        List l = (List)value;
        mergeWithChildren(l);
    }

    private void mergeWithChildren(List l) {
        for (int i = 0; i < Math.min(l.size(), children.size()); i++) {
            children.get(i).set(l.get(i));
        }
        for (int i = Math.min(l.size(), children.size()); i < Math.max(l.size(), children.size()); i++) {
            add(ParamFactory.create(String.valueOf(i), l.get(i)));
        }
    }

}
