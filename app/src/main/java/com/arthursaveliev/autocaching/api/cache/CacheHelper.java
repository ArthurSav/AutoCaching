package com.arthursaveliev.autocaching.api.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CacheHelper {

    public static Class typeToClass(Type type) {
        try {
            if (type instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
                if (arguments.length > 0) {
                    Type argumentType = arguments[0];
                    return typeToClass(argumentType);
                }
            }
            return Class.forName(((Class) type).getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Could not find class with name " + type.getClass().getName());
    }
}
