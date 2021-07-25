package com.imooc.roy.stream;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 归约与汇总操作
 *
 * @author roy f
 */
public class ReduceAndCollectTest {
    //归约 返回一个值
    @Test
    public void reduceTest() {
        /**
         * 订单对象
         */
        @Data
        @AllArgsConstructor
        class Order {
            /**
             * 订单编号
             */
            private Integer id;
            /**
             * 商品数量
             */
            private Integer productCount;
            /**
             * 消费总金额
             */
            private Double totalAmount;
        }

        /**
         * 准备数据
         */
        ArrayList<Order> list = Lists.newArrayList();
        list.add(new Order(1, 2, 25.12));
        list.add(new Order(2, 5, 257.23));
        list.add(new Order(3, 3, 23332.12));
        /**
         * 传统方式：
         * 1.计算商品数量
         * 2.计算消费总金额
         */
        /**
         * 汇总商品数量和总金额
         * reduce:每次进行处理的处理会再下一次回调中传入。
         * 下面的计算逻辑运行顺序为 0 = 0 + 1，0 = 0 + 2，0 = 0 + 3；
         */
        Order order = list.stream()
                //开启并行模式
//                .parallel()
                .reduce(
                        //初始化值
                        new Order(0, 0, 0.0),
                        //Stream中两个元素的计算逻辑
                        (Order order1, Order order2) -> {
                            System.out.println("执行计算逻辑方法" + order1.getId() + "," + order2.getId());
                            int productCount = order1.getProductCount() + order2.getProductCount();
                            double totalAmount = order1.getTotalAmount() + order2.getTotalAmount();
                            return new Order(0, productCount, totalAmount);
                        },
                        //并行情况下，多个并行结果如何合并
                        (Order order1, Order order2) -> {
                            System.out.println("执行并行合并方法");
                            int productCount = order1.getProductCount() + order2.getProductCount();
                            double totalAmount = order1.getTotalAmount() + order2.getTotalAmount();
                            return new Order(0, productCount, totalAmount);
                        });
        System.out.println(JSON.toJSONString(order, true));
    }

    //汇总 返回一个集合
    @Test
    public void collecTest() {
        /**
         * 订单对象
         */
        @Data
        @AllArgsConstructor
        class Order {
            /**
             * 订单编号
             */
            private Integer id;
            /**
             * 用户账号
             */
            private String account;
            /**
             * 商品数量
             */
            private Integer productCount;
            /**
             * 消费总金额
             */
            private Double totalAmount;
        }

        ArrayList<Order> list = Lists.newArrayList();
        list.add(new Order(1, "roy", 2, 25.12));
        list.add(new Order(2, "roy", 5, 257.23));
        list.add(new Order(3, "lisa", 3, 23332.12));

        /*
            Map<用户账号,订单（数量和金额）>
         */
        Map<String,Order> collect = list.stream()
                //开启并行模式
                .parallel()
                .collect(
                        //初始化值
                        () -> {
                            System.out.println("执行初始化容器操作");
                            return new HashMap<String, Order>();
                        },
                        //计算逻辑
                        (HashMap<String, Order> map, Order newOrder) -> {
                            System.out.println("执行新元素添加到容器操作");
                            String account = newOrder.getAccount();
                            //如果此账号已存在，将新订单数据累加上
                            if (map.containsKey(account)) {
                                Order order = map.get(account);
                                order.setProductCount(newOrder.getProductCount() + order.getProductCount());
                                order.setTotalAmount(newOrder.getTotalAmount() + order.getTotalAmount());
                            } else {
                                //如果不存在，直接将order放入map
                                map.put(account, newOrder);
                            }
                        },
                        //并行情况下两个容器的合并逻辑
                        (HashMap<String, Order> map1, HashMap<String, Order> map2) -> {
                            System.out.println("执行并行结果合并操作");
                            //对key进行比对，相同的key，value进行累加，没有的key，添加到另一个
                            map2.forEach((key, value) -> {
                                //merge操作，如果在map1中发现了map2传来的key则将map1的value（order1）和map2对应的value（order2）传入lambda
                                //如果没有发现key则将value push到map1
                                map1.merge(key, value, (order1, order2) -> {
                                    //如果map1找到相同的key
                                    //TODO 注意:一定要用map1做合并，因为最后collect返回的是map1
                                    return new Order(0, key, order1.getProductCount() + order2.getProductCount(), order1.getTotalAmount() + order2.getTotalAmount());
                                });
                            });
                        });
        System.out.println(JSON.toJSONString(collect, true));

    }
}
