package com.imooc.roy.lambda.file;

import org.junit.Test;

import java.beans.Transient;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.Assert.*;

/**
 * @author roy f
 */
public class FileServiceTest {

    @Test
    public void fileHandle() throws IOException {
        FileService fileService = new FileService();
        String path = "D:\\logs\\history.2021-04-08.log";
        fileService.fileHandle(path, fileContent -> System.out.println(fileContent));
        System.out.println("success");

        Function<String,Integer> consumer1 = Integer::parseInt;
        Integer s1 = consumer1.apply("22");
        System.out.println(s1);
    }
}