package com.imooc.roy.resource;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author roy f
 */
public class NewFileCopyTest {

    @Test
    public void copyFile() {
        //先定义输入输出路径，
        String originalUrl = "D:\\logs\\history.2021-04-08.log";
        String targetUrl = "D:\\logs\\target.log";
        //初始化输入输出流对象
        try (
                FileInputStream originalInputStream = new FileInputStream(originalUrl);
                FileOutputStream targetOutputStream = new FileOutputStream(targetUrl);
        ) {
            int content;
            while ((content = originalInputStream.read()) != -1) {
                targetOutputStream.write(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
