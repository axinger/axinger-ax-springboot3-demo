package com.ax.test.guava;

import com.google.common.collect.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class SetTest {


    @Test
    void set() {
        HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

        Sets.SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union:" + union);

        Sets.SetView<Integer> difference = Sets.difference(setA, setB);
        System.out.println("difference:" + difference);

        Sets.SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:" + intersection);

    }

    //双键 map - 超级实用
    @Test
    void test_HashBasedTable() {
        Table<String, String, Object> tables = HashBasedTable.create();
        tables.put("财务部", "总监", "a");
        tables.put("财务部", "职员", "b");
        tables.put("法务部", "助理", "c");
        System.out.println("双键 map = " + tables);
        System.out.println("双键 map 取值 = " + tables.get("财务部", "总监"));
    }


    @Test
    void map() {
        Map<Object, Object> map1 = Maps.newHashMap();
        map1.put("name", "jim");
        map1.put("age", 20);
        final Map<Object, Object> filterKeys = Maps.filterKeys(map1, key -> {
            System.out.println("key = " + key);
            return key.equals("name");
        });
        System.out.println("filterKeys = " + filterKeys);

        Map<Object, Object> map2 = Maps.newHashMap();
        map2.put("name", "tom");
        map2.put("high", 20);
        //交集
        MapDifference<Object, Object> difference = Maps.difference(map1, map2);

        System.out.println("求两个map集合的差集,key相同,value不同 = " + difference);

        //左边差集
        final Map<Object, Object> onlyOnLeft = difference.entriesOnlyOnLeft();
        System.out.println("map1 独有 = " + onlyOnLeft);
        //右边差集
        final Map<Object, Object> onlyOnRight = difference.entriesOnlyOnRight();
        System.out.println("map2 独有 = " + onlyOnRight);
        System.out.println("difference.entriesDiffering() = " + difference.entriesDiffering());
        System.out.println("difference.entriesInCommon() = " + difference.entriesInCommon());


    }

    // map的一个key可以关联多个value
    @Test
    void test2() {
        ArrayListMultimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("name", "jim");
        multimap.put("name", "tom");
        multimap.put("name", "jack");

        System.out.println("multimap = " + multimap);
        System.out.println("multimap.get(\"name\") = " + multimap.get("name"));


    }

    // map通过value获取key
    @Test
    void test_HashBiMap() {
        HashBiMap map = HashBiMap.create();
        map.put("name", "jim");
        System.out.println("map.get(\"name\") = " + map.get("name"));
        System.out.println("map.inverse().get(\"jim\") = " + map.inverse().get("jim"));
    }

    //复制一个map，新的map的值不可变,ImmutableMap 不可变map
    @Test
    void test_ImmutableMap() {


        final ImmutableMap<Object, Object> map3 = ImmutableMap.of("name", "jim", "age", 20);
        System.out.println("不可变map = " + map3);


        final ImmutableMap<Object, Object> map2 = ImmutableMap.builder()
                .put("name", "tom")
                .put("age", 20)
                .build();
//        map2 .put("name", "tom");
        System.out.println("不可变map = " + map2);

        Map map = new HashMap();
        map.put("name", "jim");

        Map map1 = ImmutableMap.copyOf(map);
        map.put("age", 20);

        System.out.println("map = " + map);
        System.out.println("map1 = " + map1);


    }
}
