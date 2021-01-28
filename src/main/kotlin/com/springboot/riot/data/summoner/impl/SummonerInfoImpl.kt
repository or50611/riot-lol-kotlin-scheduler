package com.springboot.riot.data.summoner.impl

import com.springboot.riot.data.summoner.dto.*
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
import java.time.LocalDateTime

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
        var dataMap: HashMap<String, Any?>
        summonerInfoVo.forEach { vo ->
            //매치리스트
            val matchListEntity: ResponseEntity<MatchListDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + vo.accountId + "?endIndex=2", HttpMethod.GET, httpEntity, MatchListDto::class.java)
            matchListDto = matchListEntity.body

            matchListDto?.matches?.forEach { matchInfo ->
                dataMap = HashMap()
                dataMap["accountId"] = vo.accountId
                dataMap["gameId"] = matchInfo.gameId

                val count = summonerMapper.selectMatchReferenceOne(dataMap)

                if(count == 0){
                    var time: LocalDateTime = LocalDateTime.now()

                    println("=======================================================")
                    println("START : "+time+", GAME_ID : "+matchInfo.gameId)
                    matchInfo.accountId = vo.accountId

                    val basicCount = summonerMapper.selectMatchBasicOne(dataMap)

                    if(basicCount == 0){
                        //게임상세정보
                        val matchEntity: ResponseEntity<MatchDto> = restTemplate.exchange(Globals.API_MATCH_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchDto::class.java)
                        val matchDto: MatchDto? = matchEntity.body

                        matchDto?.let {
                            summonerMapper.insertMatchBasic(it)

                            it.teams?.forEach { teams ->
                                teams.gameId = it.gameId
                                summonerMapper.insertMatchTeamStats(teams)

                                teams.bans?.forEach { bans ->
                                    bans.gameId = teams.gameId
                                    bans.teamId = teams.teamId
                                    summonerMapper.insertMatchTeamBans(bans)
                                }
                            }

                            it.participants?.forEach { participants ->
                                participants.gameId = it.gameId
                                participants.stats?.gameId = it.gameId
                                participants.timeline?.gameId = it.gameId

                                summonerMapper.insertMatchParticipant(participants)

                                participants.stats?.let { stats -> summonerMapper.insertMatchParticipantStats(stats) }

                                participants.timeline?.let { timeLine ->
                                    summonerMapper.insertMatchParticipantTimeLine(timeLine)

                                    var deltaMap: HashMap<String,Any>
                                    timeLine.javaClass.declaredFields.forEach { field ->
                                        field.isAccessible = true
                                        val any:Any? = field.get(timeLine)

                                        if(any is MatchParticipantTimeLineDeltaDto) {
                                            deltaMap = HashMap()
                                            deltaMap["gameId"] = timeLine.gameId
                                            deltaMap["participantId"] = timeLine.participantId
                                            deltaMap["minDelta"] = field.name
                                            deltaMap["min0010"] = any.min0010
                                            deltaMap["min1020"] = any.min1020

                                            summonerMapper.insertMatchParticipantTimeLineDelta(deltaMap)
                                        }
                                    }
                                }
                            }

                            it.participantIdentities?.forEach { participantIdentities ->

                                participantIdentities.player?.gameId = it.gameId
                                participantIdentities.player?.participantId = participantIdentities.participantId

                                participantIdentities.player?.let { player -> summonerMapper.insertMatchParticipantIdentities(player) }
                            }
                        }

                        //매치타임라인정보
                        val timeLineEntity: ResponseEntity<MatchTimeLineDto> = restTemplate.exchange(Globals.API_MATCH_TIMELINE_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchTimeLineDto::class.java)
                        val matchTimeLineDto: MatchTimeLineDto? = timeLineEntity.body

                        matchTimeLineDto?.frames?.forEach { frames ->
                            frames.getAdditionalProperties()?.forEach { participantFrames ->
                                participantFrames.value.gameId = matchInfo.gameId
                                participantFrames.value.frameId = participantFrames.key.toIntOrNull() ?: 0
                                participantFrames.value.parentTimestamp = frames.timestamp
                                participantFrames.value.x = participantFrames.value.position?.x ?: 0
                                participantFrames.value.y = participantFrames.value.position?.y ?: 0

                                summonerMapper.insertMatchTimeLineParticipant(participantFrames.value)
                            }

                            frames.events?.forEach { events ->
                                events.gameId = matchInfo.gameId
                                events.parentTimestamp = frames.timestamp
                                events.position?.gameId = events.gameId
                                events.position?.parentTimestamp = events.parentTimestamp

                                events.type?.let { type ->
                                    when {
                                        type.startsWith("SKILL") -> summonerMapper.insertMatchTimeLineEventSkill(events)
                                        type.startsWith("ITEM") -> {
                                            summonerMapper.insertMatchTimeLineEventItem(events)
//                                            if(type != "ITEM_DESTROYED"){
//                                            }
                                        }
                                        type.startsWith("WARD") -> summonerMapper.insertMatchTimeLineEventWard(events)
                                        type.startsWith("ELITE") -> {
                                            events.x = events.position?.x ?: 0
                                            events.y = events.position?.y ?: 0
                                            summonerMapper.insertMatchTimeLineEventMonster(events)
                                        }
                                        type.startsWith("CHAMPION") -> {
                                            events.x = events.position?.x ?: 0
                                            events.y = events.position?.y ?: 0
                                            summonerMapper.insertMatchTimeLineEventChampion(events)
                                            events.assistingParticipantIds?.forEach { id ->
                                                insertAssisData(id,events)
                                            }
                                        }
                                        type.startsWith("BUILDING") -> {
                                            events.x = events.position?.x ?: 0
                                            events.y = events.position?.y ?: 0
                                            summonerMapper.insertMatchTimeLineEventBuilding(events)
                                            events.assistingParticipantIds?.forEach { id ->
                                                insertAssisData(id,events)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    summonerMapper.insertMatchReference(matchInfo)
                    time = LocalDateTime.now()
                    println("END : "+time+", GAME_ID : "+matchInfo.gameId)
                }
            }
        }
    }
    fun insertAssisData(id: Int, events: MatchTimeLineEventDto) {
        val dataMap: HashMap<String, Any> = HashMap()
        dataMap["gameId"] = events.gameId
        dataMap["parentTimestamp"] = events.parentTimestamp
        dataMap["timestamp"] = events.timestamp
        dataMap["participantId"] = id
        summonerMapper.insertMatchTimeLineEventAssist(dataMap)
    }
}