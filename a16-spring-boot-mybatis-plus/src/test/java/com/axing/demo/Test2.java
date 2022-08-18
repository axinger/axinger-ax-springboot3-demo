package com.axing.demo;

import com.axing.demo.entity.Person;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.LambdaUtils;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.junit.jupiter.api.Test;

/**
 * @author xing
 * @version 1.0.0
 * @ClassName Test2.java
 * @description TODO
 * @createTime 2022年06月12日 03:07:00
 */

public class Test2 {

    @Test
    void test_001() {
//        columnToString
        System.out.println("LambdaUtils.getColumnMap(Person.class) = " + LambdaUtils.getColumnMap(Person.class));
        System.out.println("LambdaUtils.extract(Person::getName).getImplMethodName() = " + LambdaUtils.extract(Person::getName).getImplMethodName());


    }
}
