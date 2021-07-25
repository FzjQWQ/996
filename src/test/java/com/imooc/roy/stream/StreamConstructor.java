package com.imooc.roy.stream;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 流的四种构建形式
 * @author roy f
 */
public class StreamConstructor {


    /**
     * 由数值直接构建
     */
    @Test
    public void streamFromValue() {
        Stream stream = Stream.of(1, 2, 3, 4, 5);
        stream.forEach(System.out::println);
    }

    /**
     * 通过数组构建流
     */
    @Test
    public void streamFromArray() {
        int[] arr = {1, 2, 3, 4, 5};
        IntStream stream = Arrays.stream(arr);
        stream.filter(i -> i % 2 == 0)
                .forEach(System.out::println);

    }

    /**
     * 通过文件构建流
     */
    @Test
    public void streamFromFile() throws IOException {
        //获取行
        Stream<String> stream = Files.lines(Paths.get("D:\\logs\\history.2021-04-08.log"));
        StringBuilder stringBuilder = new StringBuilder();
        stream
                .flatMap(s -> Arrays.stream(s.split("")))
                .map(s -> {
                    if (s.equals("t")) {
                        s = "T";
                    }
                    return s;
                })
                .forEach(s->stringBuilder.append(s));
        System.out.println(stringBuilder);
    }

    /**
     * 利用函数生成流(无限流)
     */
    @Test
    public void streamFromFunction() {
        //利用迭代的方式生成流
        Stream stream = Stream.iterate(0, n -> n + 2);
        //generate不会根据上一个流来进行生成，而是随机生成
//        Stream stream = Stream.generate(Math::random);
        stream
                .limit(100)
                .forEach(System.out::println);
    }
}
