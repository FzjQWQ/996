package com.imooc.roy.guava;

import com.google.common.base.Charsets;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 演示如何使用流（Source）与汇（Sink）来对文件进行常用操作
 *
 * @author roy f
 */
public class IOTest {
    @Test
    public void copyFile() throws IOException {
        /**
         * 创建对应的source和sink
         */
        CharSource charSource = Files.asCharSource(new File("D:\\logs\\history.2021-04-08.log"), Charsets.UTF_8);
        CharSink charSink = Files.asCharSink(new File("D:\\logs\\copyTest.2021-07-28.log"), Charsets.UTF_8);
        /**
         * 拷贝
         */
        charSource.copyTo(charSink);

        File file = new File("C:\\Users\\a9627\\Desktop\\source.txt");
        File to = new File("C:\\Users\\a9627\\Desktop\\target.txt");

        Files.copy(file, to);
    }
}
