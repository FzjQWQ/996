package com.imooc.roy.resource;

import org.junit.Test;

import java.io.*;

/**
 * @author roy f
 */
public class ResourceCloseVs {

    @Test
    public void copyFile() {
        /**
         * 1.创建输入/输出流
         * 2.执行文件拷贝，读取文件内容，写入到另一个文件中
         * 3.**关闭文件流资源**
         */
        //定义输入路径和输出路径
        String originalUrl = "D:\\logs\\history.2021-04-08.log";
        String targetUrl = "D:\\logs\\target.log";
        //声明文件输入流，文件输出流
        FileInputStream originalInputStream = null;
        FileOutputStream targetOutputStream = null;

        try {
            //实例化文件流对象
            originalInputStream = new FileInputStream(originalUrl);
            targetOutputStream = new FileOutputStream(targetUrl);

            //读取的字节信息
            int content;
            //迭代，读取/写入字节
            while ((content = originalInputStream.read()) != -1) {
                targetOutputStream.write(content);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (targetOutputStream != null) {
                try {
                    targetOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (originalInputStream != null) {
                try {
                    originalInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
