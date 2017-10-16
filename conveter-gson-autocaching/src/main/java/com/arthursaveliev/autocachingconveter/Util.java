package com.arthursaveliev.autocachingconveter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class Util {

    /**
     * @param type
     * @return type's class. Types with arguments return the argument's class e.g Collection<Post> will return Post.class
     */
    static Class typeToClass(Type type) {
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
            throw new IllegalStateException("Could not find class with name " + type.getClass().getName());
        }
    }

    static boolean isCacheable(Annotation[] annotations){
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Cacheable.class)) return true;
        }
        return false;
    }
}
