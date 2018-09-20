package com.runtime.version;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

/**
 * @author yanchao
 * @date 2018/9/17 9:38
 */
public class RuntimeVersion {

    public static void main(String[] args) throws IOException {
        Runtime.Version version = Runtime.Version.parse("9");
        System.out.println(version.feature());

        // jdk.internal.loader.ClassLoaders$PlatformClassLoader@7a0ac6e3
        System.out.println(DataSource.class.getClassLoader());

        // 传递给ClassLoader类中所有方法的资源名称都是绝对的，它们不以斜线（/）开头。
        URL url = ClassLoader.getSystemResource("java/lang/Object.class");
        System.out.println("ClassLoader#getSystemResource  :  java.lang.Object's url is " + url);

        // Class类中的资源查找方法可以指定绝对和相对资源名称。
        // 绝对资源名称以斜线开头，而相对资源名称不用。
        // 当使用绝对名称时，Class类中的方法会删除前导斜线并委派给加载Class对象的类加载器来查找资源
        URL url1 = RuntimeVersion.class.getResource("/java/lang/Object.class");
        // 上述语句会被转换成 com.runtime.version.RuntimeVersion.class.getClassLoader().getResource("java/lang/Object.class");
        // 输出：Class#getResource  :  java.lang.Object's url is jrt:/java.base/java/lang/Object.class
        System.out.println("Class#getResource  :  java.lang.Object's url is " + url1);

        // 当使用相对名称时，Class类中的方法预先添加了包名称，在使用斜线后跟斜线替换包名中的点，然后再委托加载Class对象的类加载器来查找资源。
        URL url2 = RuntimeVersion.class.getResource("java/lang/Object.class");
        // 输出：Class#getResource  :  java.lang.Object's url is null
        System.out.println("Class#getResource  :  java.lang.Object's url is " + url2);

        ModuleLayer bootLayer = ModuleLayer.boot();
        Optional<Module> moduleOptional = bootLayer.findModule("com.jdojo.address");
        if (moduleOptional.isPresent()) {
            Module module = moduleOptional.get();
            // 无法加载到？？？
            InputStream inputStream = module.getResourceAsStream("com/jdojo/address/Address.class");
            if (inputStream != null) {
                System.out.println("Resource com/jdojo/address/Address.class exists!");
            } else {
                System.out.println("Resource com/jdojo/address/Address.class not exists!");
            }
        } else {
            System.out.println("Module com.jdojo.address not exists!");
        }
    }
}
