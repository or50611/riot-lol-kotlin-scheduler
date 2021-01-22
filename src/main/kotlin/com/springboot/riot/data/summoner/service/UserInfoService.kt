package com.springboot.riot.data.summoner.service

import com.springboot.riot.data.summoner.dto.SummonerDto
import com.springboot.riot.data.summoner.dto.UserMatchInfoDto

interface UserInfoService {
	//소환사정보
	fun summonerInfo(summonerName: String): SummonerDto?
	//매치리스트
	fun matchDetailList(summonerDto: SummonerDto): UserMatchInfoDto?

}