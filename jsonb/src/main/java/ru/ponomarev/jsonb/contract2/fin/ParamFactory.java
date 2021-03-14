package ru.ponomarev.jsonb.contract2.fin;

import com.sun.istack.NotNull;
import ru.ponomarev.jsonb.contract2.fin.entity.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class ParamFactory {

    // todo: разбить на n методов
    //  вынести в рапамы
    public static <T> Param<?> create(@NotNull String name, @NotNull T obj) {
        Param<?> p;
        Class<?> clazz = obj.getClass();
        if (String.class.isAssignableFrom(clazz)) {
            p = new StringParam(name);
        } else if (Number.class.isAssignableFrom(obj.getClass())) {
            p = new NumberParam(name, (Number) obj, clazz);
        } else if (Boolean.class.isAssignableFrom(obj.getClass())) {
            p = new BooleanParam(name);
        } else if (obj instanceof LocalDate) {
            p = new LocalDateParam(name);
        } else if (obj != null && clazz.isEnum()) {
            p = new EnumParam(name);
        } else if (obj instanceof Collection) {
            p = new ListParam(name, (List<?>) obj,
                    (Class) clazz);
        } else {
            p = new CompositeObjectParam(name);
        }
        p.set(obj);
//        p.setClassName(obj.getClass().getName());
        p.setCls(obj.getClass());
        return p;
    }
}
