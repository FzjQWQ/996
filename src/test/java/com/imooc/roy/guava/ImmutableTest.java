package com.imooc.roy.guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 不可变集合用法
 *
 * @author roy f
 */
public class ImmutableTest {
    public static void test(List<Integer> list) {
        list.remove(0);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        //jdk 自带的不可变集合使用方法
        List<Integer> newList = Collections.unmodifiableList(list);
        test(newList);

        System.out.println(newList);
    }

    public void immutable() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        //guava构造不可变集合对象的三种方式

        //通过已存在的集合创建
        ImmutableSet<Integer> immutableSet = ImmutableSet.copyOf(list);
        ImmutableSet<Integer> immutableSetOf = ImmutableSet.of(1, 2, 3);
        ImmutableSet<Object> immutableSetBuild = ImmutableSet.builder()
                .add(1)
                .addAll(list)
                .addAll(Sets.newHashSet(4, 5))
                .add(6)
                .build();
    }
}
