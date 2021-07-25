package com.imooc.roy.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.roy.lambda.cart.CartService;
import com.imooc.roy.lambda.cart.Sku;
import com.imooc.roy.lambda.cart.SkuCategoryEnum;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
     * flatMap使用：将一个对象转换成流（IO流）
     */
    @Test
    public void flatMapTest() {
        skuList.stream()
                .flatMap(sku -> Arrays.stream(
                        sku.getSkuName().split("")))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

    }

    /**
     * peek使用：peek为中间操作，对源进行遍历，不是peek完全执行完再进行下面的操作，下面代码peek和forEach会轮流执行 无状态
     */
    @Test
    public void peekTest() {
        skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));

    }

    /**
     * peek使用：此处peek执行完后下面的sorted是有状态的操作，需要将前面所有的数据进行汇总排序，所以会将所有元素进行peek操作再继续流程
     * sort使用：对流中元素进行排序，可选择自然排序或指定排序  有状态
     */
    @Test
    public void sortTest() {
        skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * distinct使用：对流元素进行去重。有状态
     */
    @Test
    public void distinctTest() {
        skuList.stream()
                .map(sku -> sku.getSkuCategory())
                .distinct()
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * skip使用：跳过前N条记录。有状态
     */
    @Test
    public void skipTest() {
        skuList.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice))
                .skip(3)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * limit使用：截断前N条记录。有状态
     */
    @Test
    public void limitTest() {
        skuList.stream()
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                .limit(3)
                .forEach(item -> System.out.println(JSON.toJSONString(item, true)));
    }

    /**
     * allMatch使用：终端操作，短路操作 所有元素满足断言返回true，有一个不满足则短路返回false
     */
    @Test
    public void allMatchTest() {
        boolean match = skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .allMatch(sku -> sku.getTotalPrice() > 100);
        System.out.println(match);
    }

    /**
     * anyMatch使用：终端操作，短路操作 有元素满足断言则短路返回true
     */
    @Test
    public void anyMatchTest() {
        boolean match = skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .anyMatch(sku -> sku.getTotalPrice() > 100);
        System.out.println(match);
    }

    /**
     * noneMatch使用：终端操作，短路操作 所有元素都不满足断言返回true，有元素满足则短路返回false
     */
    @Test
    public void noneMatchTest() {
        long count = skuList.stream()
                .count();
        boolean match = skuList.stream()
                .peek(sku -> System.out.println(sku.getSkuName()))
                .noneMatch(sku -> sku.getTotalPrice() > 10000);
        System.out.println(count);
        System.out.println(match);
    }

    /**
     * 找到第一个元素
     */
    @Test
    public void findFirstTest() {
        Optional<Sku> optional = skuList.stream()
                .findFirst();
        System.out.println(JSON.toJSONString(optional.get(), true));
    }

    /**
     * findAny 在串行的情况下跟findFirst没有区别，在并行的情况下效率要比findFirst好，但是会随机获取到一个元素
     */
    @Test
    public void findAnyTest() {
        Optional<Sku> optional = skuList.stream()
                .findAny();
        System.out.println(JSON.toJSONString(optional.get(), true));

    }

    /**
     * max使用：终端操作，非短路
     */
    @Test
    public void maxTest() {
        OptionalDouble max = skuList.stream()
                //获取总价
                .mapToDouble(Sku::getTotalPrice)
                .max();
        System.out.println(max.getAsDouble());
    }

    /**
     * min使用：终端操作，非短路
     */
    @Test
    public void minTest() {
        OptionalDouble max = skuList.stream()
                //获取总价
                .mapToDouble(Sku::getTotalPrice)
                .min();
        System.out.println(max.getAsDouble());
    }

    @Test
    public void countTest() {
        long count = skuList.stream()
                .count();
        System.out.println(count);
    }
}
