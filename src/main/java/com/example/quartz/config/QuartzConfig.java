package com.example.quartz.config;

import com.example.quartz.job.MySecondJob;
import org.quartz.JobDataMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.*;

import java.util.Date;

@Configuration
public class QuartzConfig {

    @Bean
    MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean(){ //第一种方式，不支持传参
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("myFirstJob");
        bean.setTargetMethod("sayHello");
        return bean;
    }

    @Bean
    JobDetailFactoryBean jobDetailFactoryBean(){
        JobDetailFactoryBean bean = new JobDetailFactoryBean();
        JobDataMap map = new JobDataMap();
        map.put("name","javaboy");//配置secondjob中的name
        bean.setJobDataMap(map);
        bean.setJobClass(MySecondJob.class);
        return bean;
    }

    /**
     * 配置触发器SimpleTrigger用法
     * @return
     */
    @Bean
    SimpleTriggerFactoryBean simpleTriggerFactoryBean(){
        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
        bean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());//绑定第一种方式methodInvokingJobDetailFactoryBean(),
        bean.setStartTime(new Date());
        bean.setRepeatInterval(2000); //重复的时间间隔
        bean.setRepeatCount(4); //重复次数
        return bean;
    }
    /**
     * 配置触发器CronTrigger用法
     * @return
     */
    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(jobDetailFactoryBean().getObject());
        bean.setCronExpression("* * * * * ?");
        return bean;
    }

    /**
     * 启动定时任务（注入上面两个Trigger）
     * @return
     */
    @Bean
    SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(simpleTriggerFactoryBean().getObject(),cronTriggerFactoryBean().getObject());
        return bean;
    }
}
