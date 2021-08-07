package com.imooc.roy.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.PrimitiveSink;
import org.junit.Test;

/**
 * @author roy f
 */
public class BloomFilterTest {
    @Test
    public void bloomFilter() {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(
                //将任意类型数据转换为Java基础类型，默认转换为byte数组
                (Integer from, PrimitiveSink primitiveSink) -> primitiveSink.putInt(from),
                //预计插入的元素总数
                10000L,
                //期望误判率（0.0 ~ 1.0）
                0.1
        );
        for (int i = 1; i < 10000; i++) {
            bloomFilter.put(i);
        }

        //检测给定元素是否 可能 存在在布隆过滤器中
        Object object;
        boolean might = bloomFilter.mightContain(66666);
        System.out.println("是否存在？" + might);

    }
}
