package com.springboot.riot.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
@EnableScheduling
class ScheduledConfiguration {

    @Bean
    fun scheduler(): TaskScheduler {
        var scheduler: ThreadPoolTaskScheduler = ThreadPoolTaskScheduler()
        scheduler.poolSize = 10
        return scheduler
    }

}