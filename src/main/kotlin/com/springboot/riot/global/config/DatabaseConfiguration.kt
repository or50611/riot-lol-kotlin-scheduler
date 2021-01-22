package com.springboot.riot.global.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.springframework.context.annotation.Configuration
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource

import javax.sql.DataSource

@Configuration
@PropertySource("classpath:/application.properties")
class DatabaseConfiguration {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun hikariConfig(): HikariConfig {
        return HikariConfig()
    }

    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource(hikariConfig())
    }

    @Bean
    fun sqlSessionFactory(): SqlSessionFactory? {
        val factoryBean: SqlSessionFactoryBean = SqlSessionFactoryBean()
        factoryBean.setDataSource(dataSource())
        factoryBean.setMapperLocations(*applicationContext.getResources("classpath:/mappers/**/*Mapper.xml"))
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis-config.xml"))
        return factoryBean.`object`
    }

    @Bean
    fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

}