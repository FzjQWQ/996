package com.imooc.roy.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.roy.lambda.cart.CartService;
import com.imooc.roy.lambda.cart.Sku;
import com.imooc.roy.lambda.cart.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 演示流的各种操作
 *
 * @author roy f
 */
public class StreamOperator {

    List<Sku> skuList;

    @Before
    public void init() {
        skuList = CartService.getCartSkuList();
    }

    /**
     * filter使用：过滤掉不符合断言(Predicate)判断的数据
     */
    @Test
    public void filterTest() {
        skuList.stream()
                //filter 包含一个Predicate断言（传入参数，返回boolean值）函数式接口，true的值进行保留
                .filter(sku -> SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * map使用：将一个元素转换成另一个元素
     */
    @Test
    public void mapTest() {
        skuList.stream()
                .map(Sku::getSkuName)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * flatMap使用：将一个对象转换成流
     */
    @Test
    public void flatMapTest() {
        skuList.stream()
                .flatMap(sku -> Arrays.stream(
                        sku.getSkuName().split("")))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

    }

    /**
     * peek使用：对源进行遍历，不是peek完全执行完再进行下面的操作，下面代码peek和forEach会轮流执行
     */
    @Test
    public void peekTest() {
        skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

    }

    /**
     * peek使用：此处peek执行完后下面的sorted是有状态的操作，需要将前面所有的数据进行汇总排序，所以会将所有元素进行peek操作再继续流程
     */
    @Test
    public void sortTest() {
        skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }
}
