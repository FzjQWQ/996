package com.imooc.roy.lambda.file;

import java.io.*;

/**
 * @author roy f
 */
public class FileService {
    /**
     * 通过url获取本地文件内容，调用函数式接口处理
     * @param url
     * @param fileConsumer
     */
    public void fileHandle(String url, FileConsumer fileConsumer) throws IOException {
        //创建文件读取流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        //循环获取文本内容
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }
        //调用函数时接口方法，将文件内容传递给lambda表达式，实现业务逻辑
        fileConsumer.fileHandler(stringBuilder.toString());
    }
}
