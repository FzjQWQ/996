package com.imooc.roy.guava;

import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * java8中的Optional使用方法
 *
 * @author roy f
 */
public class OptionalTest {
    @Test
    public void test() throws Throwable {
        /**
         * 三种创建Optional对象的方式
         */
        //创建空的Optional对象
        Optional.empty();

        //使用非null值创建Optional对象，如果值为null则会抛npe
        Optional.of("roy");

        //使用任意值创建Optional对象
        Optional optional = Optional.ofNullable("roy test");
        /**
         * 判断是否引用缺失的方法(建议不直接使用)
         */
        optional.isPresent();

        /**
         * 当optional的引用非空时，会执行方法（consumer）
         * 类似方法：map filter flatmap
         */
        optional.ifPresent(System.out::println);

        /**
         * 当optional引用缺失时执行
         */
        //当optional引用缺失时，赋一个默认值
        optional.orElse("引用缺失");

        optional.orElseGet(() -> {
            //自定义引用缺失时的返回值
            return "自定义引用缺失";
        });
        optional.orElseThrow(() -> {
            //返回一个异常对象
            return new RuntimeException("引用缺失异常");
        });

    }

    public static void stream(List<String> list) {
        Optional.ofNullable(list)
                .map(List::stream)
                .orElseGet(Stream::empty)
                .forEach(System.out::println);

    }
}
