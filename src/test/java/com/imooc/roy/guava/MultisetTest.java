package com.imooc.roy.guava;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Chars;
import org.junit.Test;

/**
 * 实现：使用Multiset统计一首古诗的文字出现频率
 * @author roy f
 */
public class MultisetTest {

    private static final String text = "风急天高猿啸哀，渚清沙白鸟飞回。" +
            "无边落木萧萧下，不尽长江滚滚来。" +
            "万里悲秋常作客，百年多病独登台。" +
            "艰难苦恨繁霜鬓，潦倒新停浊酒杯。";

    @Test
    public void handle() {
        //multiset 创建
        Multiset<Character> multiset = HashMultiset.create();
        //string 转换成 char数组
        char[] chars = text.toCharArray();

        //遍历数组，添加到multiset中
        Chars.asList(chars)
                .stream()
                .forEach(charItem ->{
                    multiset.add(charItem);
                });
        System.out.println("size = " + multiset.size());
        System.out.println("count = " + multiset.count('萧'));

    }

}
