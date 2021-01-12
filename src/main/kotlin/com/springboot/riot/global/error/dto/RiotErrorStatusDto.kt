package com.springboot.riot.global.error.dto

data class RiotErrorStatusDto (
	var message: String? = null,
	var status_code: Int = 0
)