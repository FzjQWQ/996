package com.imooc.roy.stream.cases;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author roy f
 */
public class CaseFour {
    @Data
    @AllArgsConstructor
    class Order {
        /**
         * 订单编号
         */
        private Integer orderId;
        /**
         * 账户编号
         */
        private String accountId;
    }

    /**
     * 模拟数据库查询
     * @param accountIds
     * @return
     */
    public List<Order> selectFromDB(List<String> accountIds) {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            orderList.add(
                    new Order(i,
                            accountIds.get(i % accountIds.size())));
        }
        return orderList;
    }


    /**
     * 接口
     *
     * @param accountIds
     * @return
     */
    public Map<String, List<Order>> queryOrderByAccountIds(List<String> accountIds) {
        List<Order> orders = selectFromDB(accountIds);
        //使用Optional防空
        return Optional.ofNullable(orders)
                //如果不为空进行的操作：调用List的stream方法产生一个流
                .map(List::stream)
                //如果为空进行的操作：产生一个默认的空流
                .orElseGet(Stream::empty)
                // TODO group分组功能
                .collect(Collectors.groupingBy(Order::getAccountId));
    }

    @Test
    public void test() {
        Map<String, List<Order>> stringListMap = queryOrderByAccountIds(Lists.newArrayList("user1", "user2", "user4"));
        System.out.println(JSON.toJSONString(stringListMap, true));
    }
}
