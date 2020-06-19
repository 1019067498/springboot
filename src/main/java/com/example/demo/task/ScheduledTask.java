package com.example.demo.task;

import com.example.demo.dto.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：11537qujiaqi
 * @date ：Created in 2020/5/9 17:14
 * @description：
 * @modified By：
 * @version: 1$
 */
@Component
public class ScheduledTask {
	/**
	 * @Scheduled(fixedDelay=1000*60) 不论你业务执行花费了多少时间。我都是1分钟执行1次
	 * @Scheduled(fixedRate=1000*60) 当任务执行完毕后1分钟在执行
	 * @Scheduled(initialDelay =  1000 * 10,fixedDelay = 1000 * 5)
	 * initialDelay和fixedDelay、fixedRate有着密切的关系，为什么这么说呢？该属性的作用是第一次执行延迟时间
	 * (*)星号：可以理解为每的意思，每秒，每分，每天，每月，每年...
	 * (?)问号：问号只能出现在日期和星期这两个位置，表示这个位置的值不确定，每天3点执行，所以第六位星期的位置，我们是不需要关注的，就是不确定的值。
	 * 同时：日期和星期是两个相互排斥的元素，通过问号来表明不指定值。比如，1月10日，比如是星期1，如果在星期的位置是另指定星期二，就前后冲突矛盾了。
	 * (-)减号：表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12
	 * (,)逗号：表达一个列表值，如在星期字段中使用“1,2,4”，则表示星期一，星期二，星期四
	 * (/)斜杠：如：x/y，x是开始值，y是步长，比如在第一位（秒） 0/15就是，从0秒开始，每15秒，最后就是0，15，30，45，60
	 * <p>
	 * * 第一位，表示秒，取值0-59
	 * * 第二位，表示分，取值0-59
	 * * 第三位，表示小时，取值0-23
	 * * 第四位，日期天/日，取值1-31
	 * * 第五位，日期月份，取值1-12
	 * * 第六位，星期，取值1-7，星期一，星期二...，注：不是第1周，第二周的意思
	 * 另外：1表示星期天，2表示星期一。
	 * * 第7为，年份，可以留空，取值1970-2099
	 */
	@Resource
	UserService userService;

	/**
	 * 此定时任务为了测试使用版本号实现乐观锁
	 */
	//@Scheduled(cron = "0/5 * * * * ?")
	public void scheduledTask1() {
		int userId = 3;
		//根据相同的id用户信息，赋给2个对象
		User u1 = this.userService.selectById(userId);
		User u2 = this.userService.selectById(userId);
		System.out.println(u1);
		System.out.println(u2);

		u1.setPhone("111");
		int updateResult1 = this.userService.updateUserWithLock(u1);
		System.out.println("修改商品信息1" + (updateResult1 == 1 ? "成功" : "失败"));
		u2.setPhone("111");
		int updateResult2 = this.userService.updateUserWithLock(u2);
		System.out.println("修改商品信息2" + (updateResult2 == 1 ? "成功" : "失败"));
	}

	//@Scheduled(initialDelay =  1000 * 10,fixedDelay = 1000 * 5)
	public void scheduledTask2() {
		System.out.println("任务2执行时间：" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
		System.out.println("定时任务2");
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("任务2执行时间：" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
	}

	//@Scheduled(initialDelay =  1000 * 10,fixedRate = 1000 * 5)
	public void scheduledTask3() {
		System.out.println("任务3执行时间：" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
		System.out.println("定时任务3");
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("任务3结束时间：" + new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
	}
}
