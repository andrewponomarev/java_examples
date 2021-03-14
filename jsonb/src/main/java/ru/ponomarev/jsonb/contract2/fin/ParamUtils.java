package ru.ponomarev.jsonb.contract2.fin;


import ru.ponomarev.jsonb.contract2.fin.entity.Param;

public class ParamUtils {

    public static Class<?> getClass(Param<?> param) {
        try {
            return Class.forName(param.getClassName());
//            return param.g();
        } catch (Exception e) {
            return null;
        }
    }
}
