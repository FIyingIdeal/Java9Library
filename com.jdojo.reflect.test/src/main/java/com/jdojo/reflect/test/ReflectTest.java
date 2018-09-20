package com.jdojo.reflect.test;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

/**
 * @author yanchao
 * @date 2018/9/12 10:04
 */
public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("com.jdojo.reflect.Item");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            printFieldValue(field);
        }
    }

    private static void printFieldValue(Field field) {
        String fieldName = field.getName();
        try {
            field.setAccessible(true);
            System.out.println(fieldName + " = " + field.get(null));
        } catch (IllegalAccessException | InaccessibleObjectException e) {
            System.out.println("Accessing " + fieldName + " Error : " + e.getMessage());
        }
    }
}
