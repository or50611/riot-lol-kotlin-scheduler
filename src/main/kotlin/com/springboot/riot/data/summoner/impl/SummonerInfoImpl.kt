package com.springboot.riot.data.summoner.impl

import com.springboot.riot.data.summoner.dto.MatchDto
import com.springboot.riot.data.summoner.dto.MatchListDto
import com.springboot.riot.data.summoner.dto.SummonerDto
import com.springboot.riot.data.summoner.mapper.SummonerMapper
import com.springboot.riot.data.summoner.service.SummonerService
import com.springboot.riot.data.summoner.vo.SummonerInfoVo
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SummonerInfoImpl: SummonerService {

    @Autowired
    lateinit var summonerMapper: SummonerMapper

    private val restTemplate: RestTemplate

    constructor (restTemplateBuilder: RestTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build()
    }

    var httpEntity: HttpEntity<SummonerDto> = RiotApiUtil.setHeaders()

    override fun summonerJsonInfo() {

        val summonerInfoVo: List<SummonerInfoVo> = summonerMapper.selectSummonerList()

        var matchListDto: MatchListDto?
        var dataMap = HashMap<String, Any?>()
        summonerInfoVo.forEach { vo ->

            if(vo.activityNm == "Faker") {
                //매치리스트
                var matchListEntity: ResponseEntity<MatchListDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + vo.accountId + "?endIndex=2", HttpMethod.GET, httpEntity, MatchListDto::class.java)
                matchListDto = matchListEntity.body

                matchListDto?.matches?.forEach { matchInfo ->
                    dataMap["accountId"] = vo.accountId
                    dataMap["gameId"] = matchInfo.gameId

                    val result = summonerMapper.selectMatchReferenceOne(dataMap)

                    if(result == 0){
                        matchInfo.accountId = vo.accountId
//                        summonerMapper.insertMatchReference(matchInfo)

                        //게임상세정보
                        var matchEntity: ResponseEntity<MatchDto> = restTemplate.exchange(Globals.API_MATCH_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchDto::class.java)
                        var matchDto: MatchDto? = matchEntity.body

                        matchDto?.let {
                            summonerMapper.insertMatchBasic(it)

                            it.teams?.forEach { teams ->
                                teams.gameId = it.gameId
//                                summonerMapper.insertMatchTeamStats(teams)

                                teams.bans?.forEach { bans ->
                                    bans.gameId = teams.gameId
                                    bans.teamId = teams.teamId
//                                    summonerMapper.insertMatchTeamBans(bans)
                                }
                            }

                            it.participants?.forEach { participants ->
                                participants.gameId = it.gameId
                                participants.stats?.gameId = it.gameId
                                participants.timeline?.gameId = it.gameId

//                                summonerMapper.insertMatchParticipant(participants)

//                                participants.stats?.let { stats -> summonerMapper.insertMatchParticipantStats(stats) }

//                                participants.timeline?.let { timeLine -> summonerMapper.insertMatchParticipantTimeLine(timeLine) }

                            }
                        }
                    }
                }
            }
        }
    }
}