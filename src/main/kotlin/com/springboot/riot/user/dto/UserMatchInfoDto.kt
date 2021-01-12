package com.springboot.riot.user.dto

import org.springframework.http.HttpStatus
import com.springboot.riot.global.error.RiotErrorBodyDto
import com.fasterxml.jackson.annotation.JsonIgnore

data class UserMatchInfoDto (
	var name: String? = null,
	var sumWin: Int = 0,
	var sumLoss: Int = 0,
	var avgKill: Double = 0.0,
	var avgDeath: Double = 0.0,
	var avgAssist: Double = 0.0,
	var avgActiveScore: Double = 0.0,
	var items: List<UserMatchListInfoDto>? = null,
	
	@JsonIgnore
	var status: HttpStatus? = null,
	@JsonIgnore
	var errorBodyDto: RiotErrorBodyDto? = null
	
)

fun UserMatchInfoDto.setWinFlag(win: Boolean){
	if(win){
		this.sumWin++
	}else{
		this.sumLoss++
	}
}