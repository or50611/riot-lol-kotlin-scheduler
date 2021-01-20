package com.springboot.riot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
open class RiotApplication: SpringBootServletInitializer() {

	override fun configure(builder: SpringApplicationBuilder): SpringApplicationBuilder {
		return builder.sources(RiotApplication::class.java)
	}
}

fun main(args: Array<String>) {
	runApplication<RiotApplication>(*args)
}
