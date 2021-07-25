package com.imooc.roy.lambda.file;

/**
 * @author roy f
 * 文件处理函数式接口
 */
@FunctionalInterface
public interface FileConsumer {
    /**
     * 函数式接口抽象方法
     * @param fileContent
     */
    void fileHandler(String fileContent);

}
