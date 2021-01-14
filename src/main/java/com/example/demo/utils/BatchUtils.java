package com.example.demo.utils;

import com.example.demo.exception.UserNotExist;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import static com.example.demo.utils.CommonUtils.nvl;

/**
 * @package: com.hand.wms.inv.utils
 * @author: QuJiaQi
 * @date: 2021/1/11 16:57
 */
public class BatchUtils {
    public static Map<String, String> compareTwoObject(Object obj1, Object obj2, String[] includeFields, String[] excludeFields) throws IllegalAccessException {
        Map<String, String> diffMap = new LinkedHashMap<>();
        List<String> includeFieldList = Arrays.asList(includeFields);
        List<String> excludeFieldList = Arrays.asList(excludeFields);
        Class<?> clazz1 = obj1.getClass();
        Class<?> clazz2 = obj2.getClass();
        //父子属性要分开比，经测试发现重写的同名父子属性的值不同
        Field[] childFields1 = clazz1.getDeclaredFields();
        Field[] fatherFields1 = clazz1.getSuperclass().getDeclaredFields();
        Field[] childFields2 = clazz2.getDeclaredFields();
        Field[] fatherFields2 = clazz2.getSuperclass().getDeclaredFields();
        BiPredicate biPredicate = new BiPredicate() {
            @Override
            public boolean test(Object object1, Object object2) {
                if (object1 == null && object2 == null) {
                    return true;
                }
                if (object1 == null && object2 != null){
                    return false;
                }
                // 假如还有别的类型需要特殊判断 比如 BigDecimal, 演示，只写BigDecimal示例，其他都相似
                if (object1 instanceof BigDecimal && object2 instanceof BigDecimal) {
                    if (object1 == null && object2 == null) {
                        return true;
                    }
                    if (object1 == null ^ object2 == null) {
                        return false;
                    }
                    return ((BigDecimal) object1).compareTo((BigDecimal) object2) == 0;
                }

                if (nvl(object1, "").equals(nvl(object2, ""))) {
                    return true;
                }
                return false;
            }
        };
        //比较自身属性
        for (Field field1 : childFields1) {
            if (!includeFieldList.contains(field1.getName())) {
                continue;
            }
            if (excludeFieldList.contains(field1.getName())) {
                continue;
            }
            for (Field field2 : childFields2) {
                if (!includeFieldList.contains(field2.getName())) {
                    continue;
                }
                if (excludeFieldList.contains(field2.getName())) {
                    continue;
                }
                if (field1.getName().equals(field2.getName())) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!biPredicate.test(field1.get(obj1), field2.get(obj2))) {
                        throw new UserNotExist("查询用户不存在" + field1.getName());
//                        diffMap.put(obj1 + "_field:" + field1.getName(), field1.get(obj1).toString());
                    }
                }
            }
        }
        //比较父属性
        for (Field field1 : fatherFields1) {
            if (!includeFieldList.contains(field1.getName())) {
                continue;
            }
            if (excludeFieldList.contains(field1.getName())) {
                continue;
            }
            for (Field field2 : fatherFields2) {
                if (!includeFieldList.contains(field2.getName())) {
                    continue;
                }
                if (excludeFieldList.contains(field2.getName())) {
                    continue;
                }
                if (field1.getName().equals(field2.getName())) {
                    field1.setAccessible(true);
                    field2.setAccessible(true);
                    if (!biPredicate.test(field1.get(obj1), field2.get(obj2))) {
                        throw new UserNotExist("查询用户不存在" + field1.getName());
//                        diffMap.put(obj1 + "_field:" + field1.getName(), field1.get(obj1).toString());
                    }
                }
            }
        }
        return diffMap;
    }
}
