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
            var matchListEntity: ResponseEntity<MatchListDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + vo.accountId + "?endIndex=2", HttpMethod.GET, httpEntity, MatchListDto::class.java)
            matchListDto = matchListEntity.body

            matchListDto?.matches?.forEach { matchInfo ->
                println("----------------- matchInfo.gameId : "+matchInfo.gameId)
                dataMap = HashMap()
                dataMap["accountId"] = vo.accountId
                dataMap["gameId"] = matchInfo.gameId

//                if(vo.accountId == "B_tQuW5NqQoI4I4beO_Qy48xqR-00TjfhJuY1pwytoCRPOI") {
//                    return@forEach
//                }

                val count = summonerMapper.selectMatchReferenceOne(dataMap)

                if(count == 0){
                    matchInfo.accountId = vo.accountId
                    summonerMapper.insertMatchReference(matchInfo)

                    val basicCount = summonerMapper.selectMatchBasicOne(dataMap)

                    if(basicCount == 0){
                        //게임상세정보
                        var matchEntity: ResponseEntity<MatchDto> = restTemplate.exchange(Globals.API_MATCH_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchDto::class.java)
                        var matchDto: MatchDto? = matchEntity.body

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
                                println(participants.participantId)
                                participants.gameId = it.gameId
                                participants.stats?.gameId = it.gameId
                                participants.timeline?.gameId = it.gameId

                                summonerMapper.insertMatchParticipant(participants)

                                participants.stats?.let { stats -> summonerMapper.insertMatchParticipantStats(stats) }

                                participants.timeline?.let { timeLine ->
                                    summonerMapper.insertMatchParticipantTimeLine(timeLine)

                                    var dataMap: HashMap<String,Any>
                                    timeLine?.javaClass?.declaredFields?.forEach { field ->
                                        field.isAccessible = true
                                        var any:Any? = field.get(timeLine)

                                        if(any is MatchParticipantTimeLineDeltaDto && any != null) {
                                            dataMap = HashMap()
                                            dataMap["gameId"] = timeLine.gameId
                                            dataMap["participantId"] = timeLine.participantId
                                            dataMap["minDelta"] = field.name
                                            dataMap["min0010"] = any.min0010
                                            dataMap["min1020"] = any.min1020

                                            summonerMapper.insertMatchParticipantTimeLineDelta(dataMap)
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

                        println("matchInfo.gameId : "+matchInfo.gameId)
                        //매치타임라인정보
                        val timeLineEntity: ResponseEntity<MatchTimeLineDto> = restTemplate.exchange(Globals.API_MATCH_TIMELINE_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchTimeLineDto::class.java)
                        val matchTimeLineDto: MatchTimeLineDto? = timeLineEntity.body

                        matchTimeLineDto?.frames?.forEach { frames ->
                            frames.getAdditionalProperties()?.forEach { participantFrames ->
                                participantFrames.value.gameId = matchInfo.gameId
                                println("participantFrames.key : "+participantFrames.key)
                                participantFrames.value.frameId = participantFrames.key.toIntOrNull() ?: 0
                                participantFrames.value.parentTimestamp = frames.timestamp

                                participantFrames.value.position?.gameId = matchInfo.gameId
                                participantFrames.value.position?.frameId = participantFrames.key.toIntOrNull() ?: 0
                                participantFrames.value.position?.parentTimestamp = frames.timestamp

                                summonerMapper.insertMatchTimeLineParticipant(participantFrames.value)
                                participantFrames.value.position?.let { summonerMapper.insertMatchTimeLineParticipantPosition(it) }
                            }

                            frames.events?.forEach { events ->
                                events.gameId = matchInfo.gameId
                                events.parentTimestamp = frames.timestamp
                                events.position?.gameId = events.gameId
                                events.position?.parentTimestamp = events.parentTimestamp

                                events.type?.let { type ->
                                    when {
                                        type.startsWith("SKILL") -> summonerMapper.insertMatchTimeLineEventSkill(events)
                                        type.startsWith("ITEM") -> summonerMapper.insertMatchTimeLineEventItem(events)
                                        type.startsWith("WARD") -> summonerMapper.insertMatchTimeLineEventWard(events)
                                        type.startsWith("ELITE") -> {
                                            summonerMapper.insertMatchTimeLineEventMonster(events)
                                            events.position?.let { summonerMapper.insertMatchTimeLineEventPosition(it) }
                                        }
                                        type.startsWith("CHAMPION") -> {
                                            summonerMapper.insertMatchTimeLineEventChampion(events)
                                            events.position?.let { summonerMapper.insertMatchTimeLineEventPosition(it) }
                                            events.assistingParticipantIds?.forEach { id ->
                                                insertAssisData(id,events)
                                            }
                                        }
                                        type.startsWith("BUILDING") -> {
                                            summonerMapper.insertMatchTimeLineEventBuilding(events)
                                            events.position?.let { summonerMapper.insertMatchTimeLineEventPosition(it) }
                                            events.assistingParticipantIds?.forEach { id ->
                                                insertAssisData(id,events)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
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