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

    fun insertMatchTimeLineParticipantPosition(matchTimeLinePositionDto: MatchTimeLinePositionDto): Int

}