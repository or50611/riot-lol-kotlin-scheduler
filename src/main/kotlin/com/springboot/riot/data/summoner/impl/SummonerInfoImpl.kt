package com.springboot.riot.data.summoner.impl

import com.springboot.riot.data.dto.LeagueEntryListDto
import com.springboot.riot.data.league.mapper.LeagueMapper
import com.springboot.riot.data.summoner.dto.*
import com.springboot.riot.data.summoner.dto.v5.MatchListV5Dto
import com.springboot.riot.data.summoner.dto.v5.MatchTimeV5Dto
import com.springboot.riot.data.summoner.dto.v5.MatchV5Dto
import com.springboot.riot.data.summoner.dto.v5.SummonerMatchV5Dto
import com.springboot.riot.data.summoner.mapper.SummonerMapper
import com.springboot.riot.data.summoner.service.SummonerService
import com.springboot.riot.data.summoner.vo.SummonerInfoVo
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SummonerInfoImpl: SummonerService {

    var logger: Logger = LoggerFactory.getLogger(SummonerService::class.java)

    @Autowired
    lateinit var summonerMapper: SummonerMapper

    @Autowired
    lateinit var leagueMapper: LeagueMapper

    private val restTemplate: RestTemplate

    constructor (restTemplateBuilder: RestTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build()
    }

    var httpEntity: HttpEntity<SummonerDto> = RiotApiUtil.setHeaders()

    override fun summonerJsonInfoV5() {
        
        val summonerInfoVo: List<SummonerInfoVo> = summonerMapper.selectSummonerList()

        var matchListV5Dto: MatchListV5Dto?
        var summonerMatchV5Dto: SummonerMatchV5Dto = SummonerMatchV5Dto()

        var dataMap: HashMap<String, Any?>

        summonerInfoVo.forEach { vo ->

            val puuid = vo.puuid
            //매치리스트
            val matchListEntity: ResponseEntity<MatchListV5Dto> = restTemplate.exchange(Globals.API_MATCH_LISTS_V5 + puuid + "/ids?start=0&count=5", HttpMethod.GET, httpEntity, MatchListV5Dto::class.java)
            matchListV5Dto = matchListEntity.body
            matchListV5Dto?.forEach { matchId ->

                summonerMatchV5Dto.matchId = matchId
                summonerMatchV5Dto.puuid = puuid

                dataMap = HashMap()
                dataMap["puuid"] = puuid
                dataMap["matchId"] = matchId

                val count = summonerMapper.selectMatchReferenceOneV5(summonerMatchV5Dto)


                if(count == 0) {

                    logger.info("=======================================================")
                    logger.info("START MATCH_ID : {}", matchId)

                    val basicCount = summonerMapper.selectMatchBasicOneV5(dataMap)

                    if(basicCount == 0){
                        //게임상세정보
                        val matchEntity: ResponseEntity<MatchV5Dto> = restTemplate.exchange(Globals.API_MATCH_INFO_V5 + matchId, HttpMethod.GET, httpEntity, MatchV5Dto::class.java)
                        val matchV5Dto: MatchV5Dto? = matchEntity.body
                        matchV5Dto?.info?.let { info ->

                            info.matchId = matchId
                            //매치 기본정보
                            summonerMapper.insertMatchBasicV5(info)

                            if(info.queueId != 420){
                                return@forEach
                            }

                            //매치 잠가자
                            info?.participants?.forEach { participants ->
                                participants.matchId = info.matchId
                                participants.gameId = info.gameId

                                participants.perks?.let { perks ->
                                    perks.statPerks?.let {statPerks ->
                                        participants.statPerkDefense = statPerks.defense
                                        participants.statPerkFlex = statPerks.flex
                                        participants.statPerkOffense = statPerks.offense
                                    }

                                    perks.styles?.forEach { styles ->


                                        styles.selections?.forEachIndexed { index, selections ->
                                            selections.matchId = participants.matchId
                                            selections.gameId = participants.gameId
                                            selections.description = styles.description
                                            selections.participantId = participants.participantId
                                            selections.style = styles.style
                                            selections.perkOrder = index

                                            if(index == 0){
                                                when(styles.description) {
                                                    "primaryStyle" -> participants.perkPrimaryStyle = selections.perk
                                                    "subStyle" -> participants.perkSubStyle = selections.perk
                                                }
                                            }

                                            summonerMapper.insertMatchPartPerksSelectionV5(selections)
                                        }
                                    }

                                }

                                //매치참가자 챌린지
                                participants.challenges?.let { challenges ->
                                    challenges.matchId = participants.matchId
                                    challenges.gameId = participants.gameId
                                    challenges.participantId = participants.participantId

                                    summonerMapper.insertMatchPartChallengesV5(challenges)
                                }

                                summonerMapper.insertMatchInfoParticipantsV5(participants)
                            }

                            info?.teams?.forEach { teams ->
                                teams.matchId = matchId
                                teams.gameId = info.gameId

                                summonerMapper.insertMatchInfoTeamV5(teams)

                                teams.bans?.forEach { bans ->
                                    bans.matchId = teams.matchId
                                    bans.gameId = teams.gameId
                                    bans.teamId = teams.teamId

                                    summonerMapper.insertMatchInfoTeamBansV5(bans)
                                }

                                teams.getDataProperties()?.forEach { e ->
                                    e.value.matchId = matchId
                                    e.value.gameId = info.gameId
                                    e.value.teamId = teams.teamId
                                    e.value.objective = e.key

                                    summonerMapper.insertMatchInfoTeamObjectivesV5(e.value)

                                }
                            }

                            //매치타임라인정보
                            val timeLineEntity: ResponseEntity<MatchTimeV5Dto> = restTemplate.exchange(Globals.API_MATCH_INFO_V5 + matchId + "/timeline", HttpMethod.GET, httpEntity, MatchTimeV5Dto::class.java)
                            val matchTimeV5Dto: MatchTimeV5Dto? = timeLineEntity.body

                            matchTimeV5Dto?.info.let { info ->
                                info?.frames?.forEach { frames ->

                                    frames.getDataProperties()?.forEach { participantFrames ->
                                        participantFrames.value.let { value ->


                                            value.matchId = matchId
                                            value.gameId = info.gameId
                                            value.frameId = participantFrames.key.toIntOrNull() ?: 0
                                            value.parentTimestamp = frames.timestamp
                                            value.x = value.position?.x ?: 0
                                            value.y = value.position?.y ?: 0

                                            summonerMapper.insertMatchTimePartFrameV5(value)

                                            value.championStats?.let { championStats ->
                                                championStats.matchId = value.matchId
                                                championStats.gameId = value.gameId
                                                championStats.frameId = value.frameId
                                                championStats.parentTimestamp = value.parentTimestamp

                                                summonerMapper.insertMatchTimePartFrameChampStatV5(championStats)
                                            }

                                            value.damageStats?.let { damageStats ->
                                                damageStats.matchId = value.matchId
                                                damageStats.gameId = value.gameId
                                                damageStats.frameId = value.frameId
                                                damageStats.parentTimestamp = value.parentTimestamp

                                                summonerMapper.insertMatchTimePartFrameDamageStatV5(damageStats)
                                            }

                                        }
                                    }

                                    frames.events?.forEach { events ->
                                        events.matchId = matchId
                                        events.gameId = info.gameId
                                        events.parentTimestamp = frames.timestamp

                                        events.type?.let { type ->
                                            when {
                                                type.startsWith("SKILL") -> summonerMapper.insertMatchTimeEventSkillV5(events)
                                                type.startsWith("ITEM") -> summonerMapper.insertMatchTimeEventItemV5(events)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    summonerMapper.insertSummonerMatchV5(summonerMatchV5Dto)
                    logger.info("END MATCH_ID : {}", matchId)
                }
            }
        }



    }

    override fun summonerJsonInfoV4() {

        val summonerInfoVo: List<SummonerInfoVo> = summonerMapper.selectSummonerList()

        var matchListDto: MatchListDto?
        var dataMap: HashMap<String, Any?>

        summonerInfoVo.forEach { vo ->
            //매치리스트
            val matchListEntity: ResponseEntity<MatchListDto> = restTemplate.exchange(Globals.API_MATCH_LISTS + vo.accountId + "?endIndex=5", HttpMethod.GET, httpEntity, MatchListDto::class.java)
            matchListDto = matchListEntity.body

            matchListDto?.matches?.forEach { matchInfo ->
                if(matchInfo.queue != 420){
                    return@forEach
                }


                dataMap = HashMap()
                dataMap["accountId"] = vo.accountId
                dataMap["gameId"] = matchInfo.gameId

                val count = summonerMapper.selectMatchReferenceOne(dataMap)

                if(count == 0){

                    logger.info("=======================================================")
                    logger.info("START GAME_ID : {}", matchInfo.gameId)
                    matchInfo.accountId = vo.accountId

                    val basicCount = summonerMapper.selectMatchBasicOne(dataMap)

                    val laneList = mutableListOf<HashMap<String, Any>>()
                    if(basicCount == 0){
                        //게임상세정보
                        val matchEntity: ResponseEntity<MatchDto> = restTemplate.exchange(Globals.API_MATCH_INFO + matchInfo.gameId, HttpMethod.GET, httpEntity, MatchDto::class.java)
                        val matchDto: MatchDto? = matchEntity.body
                        matchDto?.let { it ->
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

                                participantIdentities.player?.let { player ->
                                    player.gameId = it.gameId
                                    player.participantId = participantIdentities.participantId

                                    val leagueEntryListDto: LeagueEntryListDto?
                                    val leagueEntryEntity: ResponseEntity<LeagueEntryListDto> = restTemplate.exchange(Globals.API_LEAGUE_ENTRY_INFO + participantIdentities.player.summonerId, HttpMethod.GET, httpEntity, LeagueEntryListDto::class.java)
                                    leagueEntryListDto = leagueEntryEntity.body

                                    leagueEntryListDto?.forEach { entry ->
                                        if(entry.queueType == "RANKED_SOLO_5x5"){
                                            player.tier = entry.tier
                                            player.rank = entry.rank

                                            entry.gameId = player.gameId
                                            entry.participantId = player.participantId
                                            entry.accountId = player.accountId
                                            leagueMapper.insertLeagueEntry(entry)
                                        }
                                    }

                                    summonerMapper.insertMatchParticipantIdentities(player)



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

                            val xMin = -120.0
                            val xMax = 14870.0
                            val yMin = -120.0
                            val yMax = 14980.0
                            val mapMin = 0.0
                            val mapMax = 512.0

                            var scaleX: Double
                            var scaleY: Double

                            val positionList = summonerMapper.selectParticipantPositionList(matchInfo.gameId)
                            val itemList = summonerMapper.selectParticipantItemList(matchInfo.gameId)

                            var checkList = mutableListOf<String>()
                            summonerMapper.selectParticipantBasicList(matchInfo.gameId).forEach { basic ->
                                if(basic.participantId == 6) checkList = mutableListOf()
                                var top = 0
                                var mid = 0
                                var jungle = 0
                                var botCarry = 0
                                var botSupport = 0
                                var etc = 0

                                val filterList = positionList.filter { filter -> basic.participantId == filter.participantId }.toList()

                                itemList.forEach { item ->
                                    if(item.participantId == basic.participantId){
                                        when(item.itemId){
                                            3854,3858,3862,3850 -> botSupport = 5
                                            1035,1039 -> jungle = 5
                                        }
                                    }
                                }

                                filterList.forEach { position ->
                                    scaleX = (position.x - xMin) / (xMax - xMin) * (mapMax - mapMin)
                                    scaleY = mapMax - ((position.y - yMin) / (yMax - yMin) * (mapMax - mapMin))

                                    when {
                                        scaleX > 195 && scaleX < 265 && scaleY > 250 && scaleY < 310 -> mid++
                                        scaleX > 170 && scaleX < 340 && scaleY > 170 && scaleY < 340 -> mid++
                                        scaleX > 250 && scaleX < 320 && scaleY > 200 && scaleY < 260 -> mid++
                                        scaleX > 320 && scaleX < 385 && scaleY > 125 && scaleY < 190 -> mid++
                                        scaleX > 125 && scaleX < 190 && scaleY > 320 && scaleY < 385 -> mid++

                                        scaleX > 20 && scaleX < 60 && scaleY > 30 && scaleY < 270 -> top++
                                        scaleX > 20 && scaleX < 150 && scaleY > 20 && scaleY < 160 -> top++
                                        scaleX > 30 && scaleX < 270 && scaleY > 20 && scaleY < 60 -> top++

                                        scaleX > 240 && scaleX < 460 && scaleY > 435 && scaleY < 485 -> {
                                            botCarry++
                                            botSupport++
                                        }
                                        scaleX > 370 && scaleX < 490 && scaleY > 355 && scaleY < 480 -> {
                                            botCarry++
                                            botSupport++
                                        }
                                        scaleX > 440 && scaleX < 490 && scaleY > 265 && scaleY < 455 -> {
                                            botCarry++
                                            botSupport++
                                        }

                                        scaleX > 0 && scaleX < 170 && scaleY > 340 && scaleY < 512 -> etc++
                                        scaleX > 340 && scaleX < 265 && scaleY > 0 && scaleY < 170 -> etc++
                                        else -> jungle++
                                    }

                                }

                                val laneMap = mutableMapOf("TOP" to top, "MID" to mid, "JUNGLE" to jungle, "BOT_CARRY" to botCarry, "BOT_SUPPORT" to botSupport)

                                checkList.forEach { check ->
                                    laneMap.remove(check)
                                }

                                val sortList = laneMap.toList().sortedWith(compareByDescending { sort -> sort.second }).first()
                                checkList.add(sortList.first)

                                var laneSeq = 0
                                when(sortList.first) {
                                    "TOP" -> laneSeq = 1
                                    "JUNGLE" -> laneSeq = 2
                                    "MID" -> laneSeq = 3
                                    "BOT_CARRY" -> laneSeq = 4
                                    "BOT_SUPPORT" -> laneSeq = 5
                                }

                                val mMap = HashMap<String,Any>()
                                mMap["gameId"] = it.gameId
                                mMap["participantId"] = basic.participantId
                                mMap["lane"] = sortList.first
                                mMap["laneSeq"] = laneSeq
                                mMap["champion"] = basic.championId
                                mMap["accountId"] = basic.accountId ?: ""

                                summonerMapper.updateParticipantLane(mMap)

                                laneList.add(mMap)
                            }
                        }
                    } else {
                        summonerMapper.updateMatchEnemyChampion(dataMap)
                        logger.info("중복게임. ENEMY_CHAMPION UPDATE")
                    }


                    summonerMapper.insertMatchReference(matchInfo)

                    val findSummonerMap = laneList.firstOrNull{ it["accountId"] == matchInfo.accountId}

                    laneList.forEach { lane ->
                        if(lane["accountId"] != findSummonerMap?.get("accountId")){
                            if(findSummonerMap?.get("lane") == lane["lane"]) {
                                val uMap = HashMap<String,Any?>()
                                uMap["gameId"] = matchInfo.gameId
                                uMap["lane"] = findSummonerMap?.get("lane")
                                uMap["enemyChampion"] = lane["champion"]
                                uMap["accountId"] = matchInfo.accountId ?: ""
                                summonerMapper.updateSummonerMatchLane(uMap)
                            }
                        }
                    }

                    logger.info("END GAME_ID : {}", matchInfo.gameId)
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