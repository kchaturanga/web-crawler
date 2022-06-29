package com.kasunc.webcrawler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
public class WebConfig implements WebMvcConfigurer , SchedulingConfigurer
 {
 
    
    /**
     * The pool size.
     */
	@Value("${schaduler.thread.pool.size}")
    private  int schadulerThreadPoolSize ;
	
	

    public int getSchadulerThreadPoolSize() {
		return schadulerThreadPoolSize;
	}



	public void setSchadulerThreadPoolSize(int schadulerThreadPoolSize) {
		this.schadulerThreadPoolSize = schadulerThreadPoolSize;
	}



	/**
     * Configures the scheduler to allow multiple pools.
     *
     * @param taskRegistrar The task registrar.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar)
    {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();

        threadPoolTaskScheduler.setPoolSize(getSchadulerThreadPoolSize());
        threadPoolTaskScheduler.setThreadNamePrefix("scheduled-task-pool-");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
    
}