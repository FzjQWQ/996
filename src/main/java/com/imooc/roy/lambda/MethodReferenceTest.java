package com.imooc.roy.lambda;

import java.util.*;

/**
 * @author roy f
 */
public class MethodReferenceTest {
    static class Sku{
        private String skuName;
        private Integer skuPrice;

        public String getSkuName() {
            return skuName;
        }

        public void setSkuName(String skuName) {
            this.skuName = skuName;
        }

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public Sku(String skuName, Integer skuPrice) {
            this.skuName = skuName;
            this.skuPrice = skuPrice;
        }

    }

    public void test() {
        List<Sku> skuList = new ArrayList<Sku>(){
            {
                add(new Sku("3", 30));
                add(new Sku("1", 10));
                add(new Sku("2", 20));
                add(new Sku("4", 40));
                add(new Sku("0", 0));

            }
        };
//        skuList.sort((sku1, sku2) -> sku1.getSkuPrice() - sku2.getSkuPrice());
        skuList.sort(Comparator.comparingInt(Sku::getSkuPrice));
        skuList.forEach(System.out::println);
        for (Sku sku : skuList) {
            System.out.println(sku.getSkuName() + "-" + sku.getSkuPrice());
        }
    }

    public static void main(String[] args) {
        MethodReferenceTest methodReferenceTest = new MethodReferenceTest();
        methodReferenceTest.test();
    }
}
