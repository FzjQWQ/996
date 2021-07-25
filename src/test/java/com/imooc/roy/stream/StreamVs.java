package com.imooc.roy.stream;

import com.alibaba.fastjson.JSON;
import com.imooc.roy.lambda.cart.CartService;
import com.imooc.roy.lambda.cart.Sku;
import com.imooc.roy.lambda.cart.SkuCategoryEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * 对比原始集合操作与Stream集合操作
 *
 * @author roy f
 */
public class StreamVs {
    /**
     * 1 想看看购物车中都有什么商品
     * 2 图书类商品都给买
     * 3 其余的商品中买两件最贵的
     * 4 只需要两件商品的名称和总价
     */

    /**
     * 以原始的集合操作实现需求
     */
    @Test
    public void oldCartHandle() {
        List<Sku> cartSkuList = CartService.getCartSkuList();
        //1打印所有商品
        System.out.println(JSON.toJSONString(cartSkuList, true));

        //图书类过滤掉
        ArrayList<Sku> notBooksSkuList = new ArrayList<>();
        for (Sku sku : cartSkuList) {
            if (!SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory())) {
                notBooksSkuList.add(sku);
            }
        }
        System.out.println(JSON.toJSONString(notBooksSkuList, true));
        //其余的商品中买两件最贵的
        notBooksSkuList.sort(Comparator.comparingDouble(Sku::getTotalPrice).reversed());
        List<Sku> top2SkuList = new ArrayList<Sku>();
        top2SkuList.add(notBooksSkuList.get(0));
        top2SkuList.add(notBooksSkuList.get(1));
        System.out.println(JSON.toJSONString(top2SkuList, true));
        //两件最贵商品的总价
        Double money = 0.0;
        for (Sku sku : top2SkuList) {
            money += sku.getTotalPrice();
        }
        System.out.println("总价=" + money);
        //获取两件商品的名称
        List<String> resultSkuNameList = new ArrayList<String>();
        for (Sku sku : top2SkuList) {
            resultSkuNameList.add(sku.getSkuName());
        }
        System.out.println(JSON.toJSONString(resultSkuNameList, true));
    }

    /**
     * 以stream流的操作实现需求
     */
    @Test
    public void newCartHandle() {
        AtomicReference<Double> money = new AtomicReference<>(Double.valueOf(0.0));
        List<String> resultSkuNameList = CartService.getCartSkuList()
                .stream()
                //打印商品信息
                .peek(sku -> System.out.println(JSON.toJSONString(sku, true)))
                //过滤所有图书类商品
                .filter(sku -> !SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                //排序
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                //top2
                .limit(2)
                //对top2金额进行累加
                .peek(sku -> money.set(money.get() + sku.getTotalPrice()))
                //只要名字
                .map(sku -> sku.getSkuName())
                //收集结果
                .collect(Collectors.toList());

        /**
         * 打印输入结果
         */
        System.out.println(
                JSON.toJSONString(resultSkuNameList, true));
        System.out.println("商品总价：" + money.get());
    }
}
