package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.User;
import com.example.demo.exception.UserNotExist;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/4/23 17:06 @description：
 * @modified By：
 * @version: 1$
 */
@RestController
@Slf4j
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    UserService userService;

    /**
     * 自定义redis序列化方式，否则键值以二进制存储，
     * @param redisTemplate
     */
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 启用默认序列化方式
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取包路径
     * @return
     * redis中key加冒号可以实现文件夹层级结构展示
     */
    public String getPath(){
//        获取方法名
//        Thread.currentThread() .getStackTrace()[1].getMethodName()
        return this.getClass().getName().replaceAll("\\.",":");
    }

    /**
     * 增加用户信息
     *
     * @param user
     * @return xml配置useGeneratedKeys="true" keyProperty="userId"插入数据后会将id直接返回到对象 否则返回的对象没有id属性
     */
    @PostMapping
    public String addUser(User user) {
        userService.addUser(user);
        return JSON.toJSONString(user) ;
    }

    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     * 删除用户策略：删除数据表中数据，然后删除缓存
     */
    @DeleteMapping("/{userId}")
    public int deleteUser(@PathVariable("userId") int userId) {
        int result = userService.deleteUser(userId);
        String path = getPath();
        String key = path + ":userId_" + userId;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除了缓存中的key:" + key);
            }
        }
        return result;
    }

    /**
     * 查询单个用户信息
     *
     * @param userId
     * @return
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
    @GetMapping("/{userId}")
    public User selectById(@PathVariable("userId") int userId) {
        String path = getPath();
        //redis中key加冒号可以实现文件夹层级结构展示
        String key = path  + ":userId_" + userId;

        ValueOperations<String, User> operations = redisTemplate.opsForValue();
//        HashOperations<String, Object, Object> operations = redisTemplate.opsForHash();
        boolean hasKey = redisTemplate.hasKey(key);
        User user =null;
        if (hasKey) {
            Object userObj;
            //此处要用OBJ接收后转化User，直接用User接收会报java.util.LinkedHashMap cannot be cast to com.example.demo.dto.User
            userObj = operations.get(key);
            operations.get(key);
            user = JSON.parseObject(JSON.toJSONString(userObj),User.class);
            System.out.println("==========从缓存中获得数据=========");
        } else {
            user = userService.selectById(userId);
            if (user == null) {
                throw new UserNotExist("查询用户不存在");
            }
            System.out.println("==========从数据库中获得数据=========");
            // 写入缓存
            operations.set(key, user, 5, TimeUnit.HOURS);
        }
        return user;
    }

    /**
     * 更新单个用户信息
     *
     * @param user
     * @return
     */
    @PutMapping()
    public User updateUser(User user) {
        User userOld = userService.selectById(user.getUserId());
        userOld.setUserName(user.getUserName());
        userOld.setPassword(user.getPassword());
        userOld.setPhone(user.getPhone());
        int result = userService.updateUser(userOld);
        if (result != 0) {
            String path = getPath();
            String key = path + ":userId_" + user.getUserId();
            ValueOperations<String, User> operations = redisTemplate.opsForValue();
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            User userNew = userService.selectById(user.getUserId());
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }

        return userOld;
    }

    /**
     * 查询全部用户信息
     *
     * @return
     */
    @GetMapping("/selectAll")
    public List<User> selectAll() {
        return userService.selectAll();
    }

    /**
     * 测试事物自动回滚
     *
     * @return BigDecimal 尽量用字符串的形式初始化,否则0.1会显示为num1=0.1000000000000000055
     *         比较大小用compareTo，不要用equals和==，否则0.1<>0.10 在divide的时候就设置好要精确的小数位数和舍入模式，否则出现无法精确表达除不尽的问题即
     *         Non-terminating decimal expansion; no exact representable decimal result 正确示例 new
     *         BigDecimal("1").divide(new BigDecimal("3"), 6, BigDecimal.ROUND_HALF_UP)
     */
    @RequestMapping("/transfer")
    public String test() {
        try {
            // 2用户给3用户转账100.5元
            userService.transfer(2, 3, new BigDecimal("100.5"));
            return "转账成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "转账失败";
        }
    }

    @Resource
    private StringRedisTemplate template;

    @GetMapping("/redis/get/{key}")
    private String get(@PathVariable("key") String key) {
        return template.opsForValue().get(key);
    }

    @PostMapping("/redis/set/{key}/{value}")
    private Boolean set(@PathVariable("key") String key, @PathVariable("value") String value) {
        boolean flag = true;
        try {
            template.opsForValue().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

}

