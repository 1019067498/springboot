package com.example.demo.utils;

import com.example.demo.dto.WmsDto;
import com.example.demo.service.impl.AuditDomain;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author QuJiaQi
 */
@Component
public class CommonUtils {
    @Autowired
    private static CommonUtils commonUtils;

    //表字段
    private static String[] ignoreTableFields = new String[]{WmsDto.FIELD_ID, AuditDomain.FIELD_OBJECT_VERSION_NUMBER, AuditDomain.FIELD_LAST_UPDATED_BY, AuditDomain.FIELD_CREATED_BY, AuditDomain.FIELD_CREATION_DATE, AuditDomain.FIELD_LAST_UPDATE_DATE};
    //弹性域字段
    private static String[] attributeFields = new String[]{WmsDto.FIELD_ATTRIBUTE_CATEGORY, AuditDomain.FIELD_OBJECT_VERSION_NUMBER, AuditDomain.FIELD_LAST_UPDATED_BY, AuditDomain.FIELD_CREATED_BY, AuditDomain.FIELD_CREATION_DATE, AuditDomain.FIELD_LAST_UPDATE_DATE, WmsDto.FIELD_ATTRIBUTE15, WmsDto.FIELD_ATTRIBUTE14, WmsDto.FIELD_ATTRIBUTE13, WmsDto.FIELD_ATTRIBUTE12, WmsDto.FIELD_ATTRIBUTE11, WmsDto.FIELD_ATTRIBUTE10, WmsDto.FIELD_ATTRIBUTE9, WmsDto.FIELD_ATTRIBUTE8, WmsDto.FIELD_ATTRIBUTE7, WmsDto.FIELD_ATTRIBUTE6, WmsDto.FIELD_ATTRIBUTE5, WmsDto.FIELD_ATTRIBUTE4, WmsDto.FIELD_ATTRIBUTE3, WmsDto.FIELD_ATTRIBUTE2, WmsDto.FIELD_ATTRIBUTE1, WmsDto.FIELD_REMARK};

    @PostConstruct
    public void init() {
        commonUtils = this;
    }

    /**
     * @Description: nvl函数
     * @Author: Qiusun.Ding
     * @Date: 2019/11/6
     */
    public static <T> T nvl(T... args) {
        for (int i = 0; i < args.length; ++i) {
            if (args[i] != null) {
                return args[i];
            }
        }
        return null;
    }

    /**
     * @Description:
     * @Author: Qiusun.Ding
     * @Date: 2020/2/28
     */
    public static String sqlCharReplace(String srcStr) {
        if (srcStr == null) {
            return srcStr;
        }
        srcStr = StringUtils.replace(srcStr, "\\", "\\\\\\\\");
        srcStr = StringUtils.replace(srcStr, "_", "\\_");
        srcStr = StringUtils.replace(srcStr, "%", "\\%");
        srcStr = StringUtils.replace(srcStr, "-", "\\-");
        srcStr = StringUtils.replace(srcStr, "/", "\\/");
        srcStr = StringUtils.replace(srcStr, "\"", "\"");
        srcStr = StringUtils.replace(srcStr, "'", "\\'");
        srcStr = StringUtils.replace(srcStr, "Φ", "\\Φ");
        srcStr = StringUtils.replace(srcStr, ".", "\\.");
        srcStr = StringUtils.replace(srcStr, "+", "\\+");
        srcStr = StringUtils.replace(srcStr, "@", "\\@");
        srcStr = StringUtils.replace(srcStr, "#", "\\#");
        srcStr = StringUtils.replace(srcStr, "*", "\\*");
        return srcStr;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * @Description: 对象值转换时忽略空值
     * @Author: Qiusun.Ding
     * @Date: 2019/11/14
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /**
     * @Description: 对象值转换时忽略空值和需要排除的字段
     * @Author: Jianqiu.Zhao
     * @Date: 2019/11/28
     */
    public static void copyPropertiesIgnoreNull(Object src, Object target, String[] ignoreProperties) {
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(getNullPropertyNames(src)));
        hashSet.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[hashSet.size()];
        BeanUtils.copyProperties(src, target, hashSet.toArray(result));
    }

    /**
     * @Description: 对象值转换时屏蔽表字段：ID，创建更新人等信息
     * @Author: Qiusun.Ding
     * @Date: 2019/12/4
     */
    public static void copyPropertiesIgnoreNullAndTableFields(Object src, Object target) {
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(getNullPropertyNames(src)));
        hashSet.addAll(Arrays.asList(ignoreTableFields));
        String[] result = new String[hashSet.size()];
        BeanUtils.copyProperties(src, target, hashSet.toArray(result));
    }

    /**
     * @Description 对象值转换时屏蔽表字段：ID，创建更新人和弹性域字段等信息
     * @Author yunfeng.ye
     * @Date 2020/1/14
     * @Time 15:40
     */
    public static void copyPropertiesIgnoreNullAndTableFieldsAndAttribute(Object src, Object target) {
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(getNullPropertyNames(src)));
        hashSet.addAll(Arrays.asList(ignoreTableFields));
        hashSet.addAll(Arrays.asList(attributeFields));
        String[] result = new String[hashSet.size()];
        BeanUtils.copyProperties(src, target, hashSet.toArray(result));
    }

    /**
     * @Description: 对象值转换时屏蔽表字段：ID，创建更新人等信息
     * @Author: Qiusun.Ding
     * @Date: 2019/12/4
     */
    public static void copyPropertiesIgnoreNullAndTableFields(Object src, Object target, String[] ignoreProperties) {
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(getNullPropertyNames(src)));
        hashSet.addAll(Arrays.asList(ignoreTableFields));
        hashSet.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[hashSet.size()];
        BeanUtils.copyProperties(src, target, hashSet.toArray(result));
    }

    /**
     * @Description 对象值转换时屏蔽表字段：ID，创建更新人和弹性域字段等信息
     * @Author yunfeng.ye
     * @Date 2020/1/14
     * @Time 15:40
     */
    public static void copyPropertiesIgnoreNullAndTableFieldsAndAttribute(Object src, Object target, String[] ignoreProperties) {
        Set<String> hashSet = new HashSet<>();
        hashSet.addAll(Arrays.asList(getNullPropertyNames(src)));
        hashSet.addAll(Arrays.asList(ignoreTableFields));
        hashSet.addAll(Arrays.asList(attributeFields));
        hashSet.addAll(Arrays.asList(ignoreProperties));
        String[] result = new String[hashSet.size()];
        BeanUtils.copyProperties(src, target, hashSet.toArray(result));
    }


    /**
     * @Description 对字符串类型集合进行升序排序
     * @Param 原集合
     * @Return 排序完集合
     * @Author yunfeng.ye
     * @Date 2020/1/8
     * @Time 10:41
     */
    public static List<String> sortStringList(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if (o1.length() > o2.length()) {
                    return 1;
                } else {
                    return Integer.parseInt(o1) - Integer.parseInt(o2);
                }
            }
        });
        return strings;
    }

    /**
     * 通过对象和属性名称取得属性的描述注解
     *
     * @Author: jianqiu.zhao
     * @Date 2020/03/13
     */
    public static String getDesc(Object obj, String propertyName, String lang) {
        String result = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals(propertyName)) {
                    String desc = getDesc(field, lang);
                    if (desc != null && !desc.isEmpty()) {
                        result = desc;
                        break;
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过属性取得属性的描述注解
     *
     * @Author: jianqiu.zhao
     * @Date 2020/03/13
     */
    public static String getDesc(Field field, String lang) {
        String result = null;
        try {
            field.setAccessible(true);
            Annotation[] annotation = field.getAnnotations();
            for (Annotation tag : annotation) {
                if (tag instanceof ApiModelProperty) {
                    result = ((ApiModelProperty) tag).value();
                    break;
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 多种类型转BigDecimal
     */
    public static BigDecimal convertBigDecimal(Object value){
        if (value == null){
            return null;
        }
        if (value instanceof BigDecimal){
            return (BigDecimal) value;
        } else if (value instanceof String){
            return new BigDecimal((String) value);
        } else if (value instanceof Long){
            return new BigDecimal((Long) value);
        }
        throw new IllegalArgumentException("not support the convert value to BigDecimal, value="+value.toString());
    }
}
