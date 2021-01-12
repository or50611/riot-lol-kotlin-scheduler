package com.springboot.riot.user.impl

import com.springboot.riot.user.service.UserInfoService
import com.springboot.riot.user.dto.SummonerDto
import org.springframework.stereotype.Service
import com.springboot.riot.user.dto.UserMatchInfoDto
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import com.springboot.riot.global.common.RiotApiUtil
import org.springframework.web.client.HttpClientErrorException
import org.springframework.http.ResponseEntity
import com.springboot.riot.global.Globals
import org.springframework.http.HttpMethod
import com.google.gson.Gson
import com.springboot.riot.global.error.RiotErrorBodyDto
import com.springboot.riot.user.dto.MatchlistDto
import java.util.ArrayList
import com.springboot.riot.user.dto.UserMatchListInfoDto
import com.springboot.riot.user.dto.ParticipantStatsDto
import com.springboot.riot.user.dto.MatchReferenceDto
import com.springboot.riot.user.dto.MatchDto
import com.springboot.riot.user.dto.ParticipantIdentityDto
import com.springboot.riot.user.dto.setWinFlag

@Service
class UserInfoImpl : UserInfoService{
	
	private val restTemplate: RestTemplate
	
	constructor (restTemplateBuilder: RestTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build()
	}
	
	var httpEntity: HttpEntity<SummonerDto> = RiotApiUtil.setHeaders()
	
	override fun summonerInfo(summonerName: String): SummonerDto? {
		var summonerDto: SummonerDto? = SummonerDto()
		
		try {
			//소환사정보
			var summonerEntity: ResponseEntity<SummonerDto> = restTemplate.exchange(Globals.API_SUMMONER_NAME + summonerName, HttpMethod.GET, httpEntity, SummonerDto::class.java)
				summonerDto = summonerEntity.getBody()
				summonerDto?.status = summonerEntity.getStatusCode()
			
		} catch (e: HttpClientErrorException) {
			var gson = Gson()
			var	errorStatusDto: RiotErrorBodyDto = gson.fromJson(e.getResponseBodyAsString(), RiotErrorBodyDto::class.java)
			summonerDto?.status = e.getStatusCode()
			summonerDto?.errorBodyDto = errorStatusDto
		}
		
		return summonerDto;
	}
	
	override fun matchDetailList(summonerDto: SummonerDto): UserMatchInfoDto? {
		var userMatchInfoDto: UserMatchInfoDto? = UserMatchInfoDto()
		var matchlistDto: MatchlistDto?
		
		try {
			
			//매치리스트
			var matchlistEntity: ResponseEntity<MatchlistDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + summonerDto.accountId + "?endIndex=20", HttpMethod.GET, httpEntity, MatchlistDto::class.java)
			matchlistDto = matchlistEntity.getBody()
			userMatchInfoDto?.status = matchlistEntity.getStatusCode()
			
			var items: ArrayList<UserMatchListInfoDto> = ArrayList<UserMatchListInfoDto>();
			var userMatchListInfoDto: UserMatchListInfoDto
			
			var killAllSum: Int
			var deathAllSum: Int
			var assistAllSum: Int
			
			matchlistDto?.matches?.forEach { matchInfo ->
				killAllSum = 0
				deathAllSum = 0
				assistAllSum = 0
				
				//-------------------- 소환사게임리스트정보 start
				userMatchListInfoDto = UserMatchListInfoDto()
				userMatchListInfoDto.gameId = matchInfo.gameId
				//-------------------- 소환사게임리스트정보 end
				
				//게임상세정보
				var matchEntity: ResponseEntity<MatchDto> = restTemplate.exchange(Globals.API_MATCH_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchDto::class.java)
				var matchDto: MatchDto? = matchEntity.getBody()
				
				var findMatchDto = matchDto?.participantIdentities?.firstOrNull { summonerDto.accountId == it.player?.accountId }
				findMatchDto?.let {
					userMatchListInfoDto.participantId = findMatchDto.participantId
				}
				
				//참가자정보
				matchDto?.participants?.forEach { participantDto ->
					
					if(userMatchListInfoDto.participantId == participantDto.participantId){
						//-------------------- 소환사 게임리스트정보
						userMatchListInfoDto.championId = participantDto.championId
						userMatchListInfoDto.teamId = participantDto.teamId
						userMatchListInfoDto.spell1Id = participantDto.spell1Id
						userMatchListInfoDto.spell2Id = participantDto.spell2Id
						userMatchListInfoDto.win = participantDto.stats?.win ?: false
						userMatchListInfoDto.kills = participantDto.stats?.kills ?: 0
						userMatchListInfoDto.deaths = participantDto.stats?.deaths ?: 0
						userMatchListInfoDto.assists = participantDto.stats?.assists ?: 0
						//-------------------- 소환사 게임리스트정보
					}
					
					//킬,데스,어시
					killAllSum += participantDto.stats?.kills ?: 0
					deathAllSum += participantDto.stats?.deaths ?: 0
					assistAllSum += participantDto.stats?.assists ?: 0
				}
				
				var killStat = userMatchListInfoDto.kills.toDouble() / killAllSum.toDouble() * 3.0
				var deathStat = userMatchListInfoDto.deaths.toDouble() / deathAllSum.toDouble() * -3.0
				var assistStat = userMatchListInfoDto.assists.toDouble() / assistAllSum.toDouble()
				var activeScoreStat = 1.0 + Math.round((killStat + assistStat + deathStat) * 100.0) / 100.0
				
				userMatchListInfoDto.activeScore = Math.round(activeScoreStat * 100.0) / 100.0
				
				items.add(userMatchListInfoDto)
				
			}
			
			var participantSize = matchlistDto?.matches?.size?.toDouble() ?: 0.0
			var killUserSum = 0.0
			var deathUserSum = 0.0
			var assistUserSum = 0.0
			var activeScoreUserSum = 0.0
			
			items.forEach{ e ->
				userMatchInfoDto?.setWinFlag(e.win)
				killUserSum += e.kills
				deathUserSum += e.deaths
				assistUserSum += e.assists
				activeScoreUserSum += e.activeScore
			}
			
			userMatchInfoDto?.avgKill = Math.round(killUserSum / participantSize * 10.0) / 10.0
			userMatchInfoDto?.avgDeath = Math.round(deathUserSum / participantSize * 10.0) / 10.0
			userMatchInfoDto?.avgAssist = Math.round(assistUserSum / participantSize * 10.0) / 10.0
			userMatchInfoDto?.avgActiveScore = Math.round(activeScoreUserSum / participantSize * 100.0) / 100.0
			
			userMatchInfoDto?.items = items
			
		} catch (e: HttpClientErrorException) {
			var gson = Gson()
			var	errorStatusDto: RiotErrorBodyDto = gson.fromJson(e.getResponseBodyAsString(), RiotErrorBodyDto::class.java)
			userMatchInfoDto?.status = e.getStatusCode()
			userMatchInfoDto?.errorBodyDto = errorStatusDto
		}
				
		return userMatchInfoDto
	}
	
}