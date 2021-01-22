package com.springboot.riot.data.summoner.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.HttpStatus
import com.springboot.riot.global.error.RiotErrorBodyDto

data class SummonerDto (

	var accountId: String? = null,
	var profileIconId:Int = 0,
	var revisionDate:Long = 0L,
	var name: String? = null,
	var id: String? = null,
	var puuid: String? = null,
	var summonerLevel:Long = 0L,
	
	@JsonIgnore
	var status: HttpStatus? = null,
	@JsonIgnore
	var errorBodyDto: RiotErrorBodyDto? = null

)