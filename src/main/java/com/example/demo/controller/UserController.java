package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotExist;
import com.example.demo.service.UserService;
import com.example.demo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
     * 用单个对象接收，返回数据是空时该对象为null注意空指针,即使使用无参构造也是null
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
        UserDTO user = new UserDTO();
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
     * 用list等接收，如果没有查询到数据，会返回空集合而不是null
     * @return
     */
    @GetMapping("/selectAll")
    public List<UserDTO> selectAll() {
        List<UserDTO> userDTOList;
        userDTOList =userService.selectAll();
        return userDTOList;
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
        return userObj == null ? null : userObj.toString();
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

    @GetMapping("/lambda")
    public  void testLambda(){
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO(20,"张三","123"));
        userList.add(new UserDTO(10,"李四","456"));
        userList.add(new UserDTO(30, "王五","789"));
        userList.add(new UserDTO(30, "王五","147"));

        System.out.println("----------forEach集合中内部迭代----------");
        userList.forEach(user -> System.out.println(user.toString()));
        userList.stream().map(item -> item.getUserId() + item.getUserId()*1.5).forEach(user -> System.out.println(user.toString()));


        System.out.println("----------filter过滤map返回需要的结果collect将结果去重转化为list----------");
        List<String> result1 = userList.stream().filter(user -> user.getUserId() > 18).distinct().map(UserDTO::getUserName).collect(Collectors.toList());
        //返回过滤后对象的list
        //List<UserDTO> result1 = userList.stream().filter(user -> user.getUserId() > 18).distinct().collect(Collectors.toList());
        //根据多个条件过滤
        //Predicate<Person> predicate1 = p->p.getSalary()<20000d;
        //Predicate<Person> predicate2 = p-> p.getAddr().equals("中国深圳");
        //List<Person> personList3 = personList.stream().filter(predicate1.and(predicate2)).collect(Collectors.toList());
        System.out.println(result1);

        System.out.println("----------count返回流中元素的个数----------");
        //map主要是用于遍历每个参数，然后进行参数合并或者返回新类型的集合
        //FlatMap主要是用于stream合并
        long result2 = userList.stream().filter(user -> user.getUserId() > 18).count();
        System.out.println(result2);

        System.out.println("----------filter过滤后limit限制数量并输出----------");
        userList.stream().filter(user -> user.getUserId() > 18).limit(1).forEach(user -> System.out.println(user.getUserName()));

        System.out.println("----------sorted排序----------");
        List<UserDTO> result3 = userList.stream().sorted(Comparator.comparingInt(UserDTO::getUserId)).collect(Collectors.toList());
        List<UserDTO> result4 = userList.stream().sorted(Comparator.comparingInt(UserDTO::getUserId).reversed()).collect(Collectors.toList());
        System.out.println("升序 " + result3);
        System.out.println("降序 " + result4);

        System.out.println("----------sum求和----------");
        int result5 = userList.stream().mapToInt(UserDTO::getUserId).sum();
        System.out.println(result5);

        System.out.println("----------将字符串换成大写并用逗号拼接起来----------");
        List<String> stringList = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String result6 = stringList.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(result6);

        System.out.println("----------list转map----------");
        Map<String, UserDTO> userMap;
        Map<String, String> userMap2;
        Map<String, List<UserDTO>> userMapAll;
        //以name为键，该name的对象为值。键不能重复
        //userMap = userList.stream().collect(Collectors.toMap(UserDTO::getUserName, Function.identity()));
        //以name为键，密码为值。若键重复时取最后插入的键对应的值
        userMap2 = userList.stream().collect(Collectors.toMap(UserDTO::getUserName, UserDTO::getPassword, (key1, key2) -> key2));
        //以name为键，该name的对象的list为值
        userMapAll =userList.stream().collect(Collectors.groupingBy(item -> item.getUserName()));
        //userMapAll =userList.stream().collect(Collectors.groupingBy(item -> item.getUserName() + "-" + item.getUserId()));
        //System.out.println(userMap);
        System.out.println(userMap2);
        System.out.println(userMapAll);

        System.out.println("----------list的交集和差集----------");
        ArrayList<Integer> listA = new ArrayList<>();
        ArrayList<Integer> listB = new ArrayList<>();
        listA.add(2);
        listA.add(3);
        listA.add(8);
        System.out.println("**this is listA*********"+listA);
        listB.add(3);
        listB.add(8);
        listB.add(9);
        System.out.println("**this is listB*********"+listB);

        List<Integer> intersectionList =listA.stream().filter(t->listB.contains(t)).collect(Collectors.toList());
        System.out.println("集合的交集"+intersectionList);
        List<Integer> ListARemoveB =listA.stream().filter(t-> !listB.contains(t)).collect(Collectors.toList());
        System.out.println("a去除b的差集"+ListARemoveB);
        List<Integer> ListBRemoveA =listB.stream().filter(t-> !listA.contains(t)).collect(Collectors.toList());
        System.out.println("b去除a的差集"+ListBRemoveA);

        System.out.println("----------指定位数随机字符串----------");
        //配合<groupId>org.apache.commons</groupId><artifactId>commons-lang3</artifactId>使用
        String result7= RandomStringUtils.randomAlphanumeric(10);
        System.out.println(result7);
    }

}

