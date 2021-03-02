package ru.ponomarev.jsonb.contract2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SerializationUtils {

    /**
     *
     * Serialize each field of [obj] as an entry in HashMap
     * class A {
     *     Integer i;
     * }
     *
     * A a = new A(); a.i = 42   ->   Map.of("i", 42)
     *
     * @param obj - object to serialize
     *
     * @return
     */
    static Map<String, ?> serialize(Object obj) {
        Map<String, Object> result = new HashMap<>();
        try {
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                result.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     *
     * @param obj - map representation of object
     * @param clazz - target type of deserialization
     */
    static <T> T deserialize(Map<String, ?> obj, Class<T> clazz) {
        T instance = null;
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor();
            instance = ctor.newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, obj.get(field.getName()));
            }
        } catch (Exception e) {
        }
        return instance;
    }

    /**
     *
     * class A {
     *     String s;
     * }
     *
     * s; A.class -> new A(s)
     *
     * @param s - string value of object
     * @param clazz - target type of deserialization
     */
    static <T> T deserialize(String s, Class<T> clazz) {
        T instance = null;
        try {
            Constructor<T> ctor = clazz.getDeclaredConstructor(String.class);
            instance = ctor.newInstance(s);
        } catch (Exception e) {
        }
        return instance;
    }


}
