package com.imooc.roy.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author roy f
 */
public class SetsTest {
    /**
     * Sets工具类的常用方法
     * 并集 / 交集 / 差集 / 分解集合中的所有子集 / 求两个集合的笛卡尔积
     * <p>
     * Lists工具类的常用方式
     * 反转 / 拆分
     */
    private static final Set set1 = Sets.newHashSet(1, 2, 3,4);
    private static final Set set2 = Sets.newHashSet(4, 5, 6);

    /**
     * 并集
     */
    @Test
    public void union() {
        Set<Integer> union = Sets.union(set1, set2);
        System.out.println(union);
    }

    /**
     * 交集
     */
    @Test
    public void intersection() {
        Set<Integer> intersection = Sets.intersection(set1, set2);
        System.out.println(intersection);
    }

    /**
     * 差集
     * 属于集合A不属于集合B
     */
    @Test
    public void difference() {
        Set<Integer> difference = Sets.difference(set2,set1);
        System.out.println(difference);

        //相对差集：属于A而且不属于B 或者 属于B或者不属于A
        Set<Integer> set = Sets.symmetricDifference(set1, set2);
        System.out.println(set);
    }

    //将集合拆解为所有子集
    @Test
    public void powerSet() {
        //set1 = {1,2,3}
        // 拆解后 [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3],[4],[1,4],[2,4],[1,2,4],[3,4],[1,3,4],[2,3,4],[1,2,3,4]]
        Set<Set<Integer>> set = Sets.powerSet(set1);
        System.out.println(JSON.toJSONString(set));
    }

    //计算两个集合的笛卡尔积
    @Test
    public void cartesianProduct() {
        Set<List<Integer>> product = Sets.cartesianProduct(set1, set2);
        System.out.println(JSON.toJSONString(product));
    }

    @Test
    public void partition() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7);
        List<List<Integer>> partition = Lists.partition(list, 3);
        System.out.println(JSON.toJSONString(partition));
    }

    @Test
    public void reverse() {
        List<Integer> list = Lists.newLinkedList();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list);
        List<Integer> newList = Lists.reverse(list);
        System.out.println(newList);
    }

    @Test
    public void sort() {
        {
            List<Integer> list = Lists.newLinkedList();
            list.add(1);
            list.add(3);
            list.add(5);
            list.add(2);
            list.add(7);
            Collections.sort(list,Comparator.comparingInt(Integer::intValue));
            Collections.reverse(list);
            System.out.println(list);
        }
    }
}
