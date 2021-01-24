package com.springboot.riot.data.summoner.impl

import com.springboot.riot.data.summoner.service.UserInfoService
import com.springboot.riot.data.summoner.dto.SummonerDto
import org.springframework.stereotype.Service
import com.springboot.riot.data.summoner.dto.UserMatchInfoDto
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
import com.springboot.riot.data.summoner.dto.MatchListDto
import java.util.ArrayList
import com.springboot.riot.data.summoner.dto.UserMatchListInfoDto
import com.springboot.riot.data.summoner.dto.MatchDto
import com.springboot.riot.data.summoner.mapper.SummonerMapper
import org.springframework.beans.factory.annotation.Autowired

@Service
class UserInfoImpl : UserInfoService {

	@Autowired
	lateinit var userMapper: SummonerMapper

	
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
		var matchListDto: MatchListDto?
		
		try {
			
			//매치리스트
			var matchListEntity: ResponseEntity<MatchListDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + summonerDto.accountId + "?endIndex=20", HttpMethod.GET, httpEntity, MatchListDto::class.java)
			matchListDto = matchListEntity.body
			userMatchInfoDto?.status = matchListEntity.statusCode
			
			var items: ArrayList<UserMatchListInfoDto> = ArrayList<UserMatchListInfoDto>();
			var userMatchListInfoDto: UserMatchListInfoDto
			
			var killAllSum: Int
			var deathAllSum: Int
			var assistAllSum: Int

			matchListDto?.matches?.forEach { matchInfo ->
				killAllSum = 0
				deathAllSum = 0
				assistAllSum = 0
				
				//-------------------- 소환사게임리스트정보 start
				userMatchListInfoDto = UserMatchListInfoDto()
				userMatchListInfoDto.gameId = matchInfo.gameId
				userMatchListInfoDto.queue = matchInfo.queue
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
