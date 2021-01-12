package com.springboot.riot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RiotApplication

fun main(args: Array<String>) {
	runApplication<RiotApplication>(*args)
}
