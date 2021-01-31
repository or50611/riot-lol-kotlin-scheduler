package com.springboot.riot.data.summoner.mapper

import com.springboot.riot.data.summoner.dto.*
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

}