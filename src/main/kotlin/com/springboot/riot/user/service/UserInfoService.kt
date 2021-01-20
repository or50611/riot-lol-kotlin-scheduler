package com.springboot.riot.user.service

import com.springboot.riot.user.dto.SummonerDto
import com.springboot.riot.user.dto.UserMatchInfoDto

interface UserInfoService {
	//소환사정보
	fun summonerInfo(summonerName: String): SummonerDto?
	//매치리스트
	fun matchDetailList(summonerDto: SummonerDto): UserMatchInfoDto?

	fun totalCount(): Int?
	
}