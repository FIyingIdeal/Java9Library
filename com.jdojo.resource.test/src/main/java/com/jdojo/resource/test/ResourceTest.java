package com.jdojo.resource.test;

import com.jdojo.exported.AppResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanchao
 * @date 2018/9/17 16:25
 */
public class ResourceTest {

    public static void main(String[] args) {
        String[] resources = {
                "java/lang/Object.class",
                "com/jdojo/resource/test/own.properties",
                "com/jdojo/resource/test/ResourceTest.class",
                "unnamed.properties",
                "META-INF/invalid_pkg.properties",
                "com/jdojo/opened/opened.properties",
                "com/jdojo/opened/OpenedTest.class",
                "com/jdojo/exported/AppResource.class",
                "com/jdojo/resource/exported.properties",
                "com/jdojo/encapsulated/EncapsulatedTest.class",
                "com/jdojo/encapsulated/encapsulated.properties"
        };
        System.out.println("Using a Module:");
        // AppResource 是 com.jdojo.resource 模块中的类，可以找到该模块中未命名、无效
        Module otherModule = AppResource.class.getModule();
        for (String resource : resources) {
            lookupResource(otherModule, resource);
        }

        System.out.println("Using a Class:");
        Class clazz = ResourceTest.class;
        for (String resource : resources) {
            lookupResource(clazz, "/" + resource);
        }

    }

    private static void lookupResource(Module module, String resource) {
        try {
            InputStream inputStream = module.getResourceAsStream(resource);
            print(resource, inputStream);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void lookupResource(Class clazz, String resource) {
        InputStream inputStream = clazz.getResourceAsStream(resource);
        print(resource, inputStream);
    }

    private static void print(String resource, InputStream inputStream) {
        if (inputStream != null) {
            System.out.println("Found: " + resource);
        } else {
            System.out.println("Not Found: " + resource);
        }
    }


}
