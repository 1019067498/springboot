package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotExist;
import com.example.demo.service.UserService;
import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
    @Resource
    RedisUtil redisUtil;

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
    public String addUser(UserDTO user) {
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
            boolean hasKey = redisUtil.hasKey(key);
            if (hasKey) {
                redisUtil.del(key);
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
    public UserDTO  selectById(@PathVariable("userId") int userId) {
        String path = getPath();
        //redis中key加冒号可以实现文件夹层级结构展示
        String key = path  + ":userId_" + userId;

        boolean hasKey = redisUtil.hasKey(key);
        UserDTO user =null;
        if (hasKey) {
            Object userObj;
            //此处要用OBJ接收后转化User，直接用User接收会报java.util.LinkedHashMap cannot be cast to com.example.demo.dto.UserDTO
            userObj = redisUtil.get(key);
            user = JSON.parseObject(JSON.toJSONString(userObj),UserDTO.class);
            System.out.println("==========从缓存中获得数据=========");
        } else {
            user = userService.selectById(userId);
            if (user == null) {
                throw new UserNotExist("查询用户不存在");
            }
            System.out.println("==========从数据库中获得数据=========");
            // 写入缓存
            redisUtil.set(key, user, 60*60);
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
    public UserDTO updateUser(UserDTO user) {
        UserDTO userOld = userService.selectById(user.getUserId());
        userOld.setUserName(user.getUserName());
        userOld.setPassword(user.getPassword());
        userOld.setPhone(user.getPhone());
        int result = userService.updateUser(userOld);
        if (result != 0) {
            String path = getPath();
            String key = path + ":userId_" + user.getUserId();
            boolean hasKey = redisUtil.hasKey(key);
            if (hasKey) {
                redisUtil.del(key);
                System.out.println("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            UserDTO userNew = userService.selectById(user.getUserId());
            if (userNew != null) {
                redisUtil.set(key, userNew, 60*60);
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
    public List<UserDTO> selectAll() {
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
    public String transfer() {
        try {
            // 2用户给3用户转账100.5元
            userService.transfer(2, 3, new BigDecimal("100.5"));
            return "转账成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "转账失败";
        }
    }

    /**
     * 从redis中获取值
     * @param key
     * @return
     */
    @GetMapping("/redis/get/{key}")
    private String get(@PathVariable("key") String key) {
        Object userObj;
        userObj = redisUtil.get(key);
        return userObj.toString();
    }

    /**
     * 向redis中放值
     * @param key
     * @param value
     * @return
     */
    @PostMapping("/redis/set/{key}/{value}")
    private Boolean set(@PathVariable("key") String key, @PathVariable("value") String value) {
        return redisUtil.set(key, value);
    }

    /**
     * 测试拦截器过滤器
     * @return
     * 过滤器的实现基于回调函数。而拦截器（代理模式）的实现基于反射，代理又分静态代理和动态代理，动态代理是拦截器的简单实现。那何时使用拦截器？何时使用过滤器？
     * 如果是非spring项目，那么拦截器不能用，只能使用过滤器，这里说的拦截器是基于spring的拦截器。
     * 如果是处理controller前后，既可以使用拦截器也可以使用过滤器，如果都使用了，注意前后顺序。
     * 如果是处理dispatcherServlet前后，只能使用过滤器。
     */
    @RequestMapping(value = "/hello")
    public String test() {
        log.info("test hello.............");
        return "SUCCESS";
    }


}

