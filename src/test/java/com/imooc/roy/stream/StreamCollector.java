package com.imooc.roy.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.roy.lambda.cart.CartService;
import com.imooc.roy.lambda.cart.Sku;
import com.imooc.roy.lambda.cart.SkuCategoryEnum;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 常见预定义收集器使用
 * @author roy f
 */
public class StreamCollector {

    List<Sku> list = CartService.getCartSkuList();

    /**
     * 集合收集器
     */
    @Test
    public void toListx() {
        List<Sku> collect = list.stream()
                .filter(sku -> sku.getTotalPrice() > 100)
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect, true));
//        collect.stream().forEach(sku -> System.out.println(JSON.toJSONString(sku, true)));
    }

    /**
     * 分组
     */
    @Test
    public void group() {
        Map<Object, List<Sku>> group = list.stream()
                .collect(Collectors.groupingBy(Sku::getSkuCategory));
//        System.out.println(JSON.toJSONString(group, true));
        System.out.println(JSON.toJSONString(group.get(SkuCategoryEnum.BOOKS),true));
    }

    /**
     * 分区
     * 分组的特殊情况，由一个谓词作为分类的函数，也成为分区函数。分区函数会返回一个boolean值，将数据根据boolean值分为两组
     */
    @Test
    public void partition() {
        Map<Boolean, List<Sku>> collect = list.stream()
                .collect(Collectors.partitioningBy(sku -> sku.getTotalPrice() > 100));
        System.out.println(JSON.toJSONString(collect.get(false), true));
    }
}
