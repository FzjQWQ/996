package com.imooc.roy.stream.cases;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author roy f
 */
public class CaseFive {
    /**
     * 交易实体模型
     */
    @Data
    @AllArgsConstructor
    class Trade {
        // 下单价格
        private BigDecimal price;
        // 下单时间
        private LocalDateTime time;
        // 下单量
        private Integer count;
        // 下单类型：机构 / 个人
        private String type;
    }

    /**
     * 一段时间内的交易申请
     */
    List<Trade> trades;

    @Before
    public void init() {
        trades = new ArrayList<>();

        trades.add(new Trade(new BigDecimal(100),
                // 在当前时间的基础上添加 1 秒
                LocalDateTime.now().plusSeconds(1),
                500, "机构"));
        trades.add(new Trade(new BigDecimal(101),
                LocalDateTime.now().plusSeconds(2),
                1, "个人"));
        trades.add(new Trade(new BigDecimal(101),
                LocalDateTime.now().plusSeconds(1),
                1, "个人"));
        trades.add(new Trade(new BigDecimal(100),
                LocalDateTime.now().plusSeconds(1),
                500, "个人"));
        trades.add(new Trade(new BigDecimal(100),
                LocalDateTime.now().plusSeconds(0),
                2, "个人"));
        trades.add(new Trade(new BigDecimal(100),
                LocalDateTime.now().plusSeconds(0),
                100, "机构"));
    }

    @Test
    public void sortTrade() {
//        System.out.println(JSON.toJSONString(trades, true));
        List<Trade> collect = trades.stream()
                .sorted(
                        //首先按照价格排序
                        Comparator.comparing(
                                //TODO 进行排序调整，将自然排序翻转
                        Trade::getPrice, Comparator.reverseOrder())
                        .thenComparing(Trade::getTime)
                        .thenComparing(Trade::getCount, Comparator.reverseOrder())
                        .thenComparing((Trade::getType), (type1, type2) -> {
                            //自定义排序，负数表示第一个在前
                            if ("机构".equals(type1) && "个人".equals(type2)) {
                                return -1;
                            } else if ("个人".equals(type1) && "机构".equals(type2)) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }))
                .collect(Collectors.toList());
        System.out.println(JSON.toJSONString(collect, true));
    }
}
