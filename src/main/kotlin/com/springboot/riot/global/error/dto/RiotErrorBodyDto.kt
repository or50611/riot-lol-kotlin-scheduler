package com.springboot.riot.global.error

import com.springboot.riot.global.error.dto.RiotErrorStatusDto

data class RiotErrorBodyDto (
	var status: RiotErrorStatusDto? = null
)