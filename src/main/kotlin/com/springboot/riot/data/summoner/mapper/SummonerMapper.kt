package com.springboot.riot.data.summoner.mapper

import com.springboot.riot.data.summoner.dto.*
import com.springboot.riot.data.summoner.dto.v5.*
import com.springboot.riot.data.summoner.vo.SummonerInfoVo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SummonerMapper {

    fun selectSummonerList(): List<SummonerInfoVo>

    fun selectMatchReferenceOne(dataMap: HashMap<String, Any?>): Int

    fun selectMatchBasicOne(dataMap: HashMap<String, Any?>): Int

    fun insertMatchReference(matchReferenceDto: MatchReferenceDto): Int

    fun insertMatchBasic(matchDto: MatchDto): Int

    fun insertMatchTeamStats(matchTeamStatsDto: MatchTeamStatsDto): Int

    fun insertMatchTeamBans(matchTeamBansDto: MatchTeamBansDto): Int

    fun insertMatchParticipant(matchParticipantDto: MatchParticipantDto): Int

    fun insertMatchParticipantStats(matchParticipantStatsDto: MatchParticipantStatsDto): Int

    fun insertMatchParticipantTimeLine(matchParticipantTimeLineDto: MatchParticipantTimeLineDto): Int

    fun insertMatchParticipantTimeLineDelta(dataMap: HashMap<String, Any>): Int

    fun insertMatchParticipantIdentities(matchPlayerDto: MatchPlayerDto): Int

    fun insertMatchTimeLineParticipant(matchTimeLineParticipantDto: MatchTimeLineParticipantDto): Int

    fun insertMatchTimeLineEventSkill(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventItem(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventChampion(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventWard(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventBuilding(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventMonster(matchTimeLineEventDto: MatchTimeLineEventDto): Int

    fun insertMatchTimeLineEventPosition(matchTimeLineEventPositionDto: MatchTimeLinePositionDto): Int

    fun insertMatchTimeLineEventAssist(assistList: HashMap<String,Any>): Int

    //라인확인
    fun selectParticipantBasicList(gameId: Long): List<MatchParticipantDto>
    fun selectParticipantPositionList(gameId: Long): List<MatchTimeLineParticipantDto>
    fun selectParticipantItemList(gameId: Long): List<MatchTimeLineEventDto>
    fun updateParticipantLane(map: HashMap<String,Any>): Int
    fun updateSummonerMatchLane(map: HashMap<String,Any?>): Int

    //적챔피언업데이트
    fun updateMatchEnemyChampion(map: HashMap<String,Any?>): Int

    //MATCH V5
    fun selectMatchReferenceOneV5(summonerMatchV5Dto: SummonerMatchV5Dto): Int
    fun selectMatchBasicOneV5(dataMap: HashMap<String, Any?>): Int
    fun insertMatchBasicV5(matchInfoV5Dto: MatchInfoV5Dto): Int
    fun insertMatchInfoParticipantsV5(matchInfoParticipantsV5Dto: MatchInfoParticipantsV5Dto): Int
    fun insertMatchPartPerksSelectionV5(matchPartPerksSelectionV5Dto: MatchPartPerksSelectionV5Dto): Int
    fun insertMatchInfoTeamBansV5(matchInfoTeamBansV5Dto: MatchInfoTeamBansV5Dto): Int
    fun insertMatchInfoTeamObjectivesV5(matchInfoTeamObjectivesV5Dto: MatchInfoTeamObjectivesV5Dto): Int
    fun insertMatchPartChallengesV5(matchPartChallengesV5Dto: MatchPartChallengesV5Dto): Int
    fun insertMatchInfoTeamV5(matchInfoTeamV5Dto: MatchInfoTeamV5Dto): Int
    fun insertSummonerMatchV5(summonerMatchV5Dto: SummonerMatchV5Dto): Int

}