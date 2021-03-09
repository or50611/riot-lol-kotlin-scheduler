package com.springboot.riot.data.league.impl

import com.springboot.riot.data.dto.LeagueEntryDto
import com.springboot.riot.data.league.service.LeagueDataService
import com.springboot.riot.data.summoner.dto.SummonerDto
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class LeagueServiceImpl: LeagueDataService {

    private val restTemplate: RestTemplate

    constructor (restTemplateBuilder: RestTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build()
    }

    var httpEntity: HttpEntity<SummonerDto> = RiotApiUtil.setHeaders()

    override fun leagueEntriesJsonInfo() {

        var leagueEntryDto: LeagueEntryDto?

        val matchListEntity: ResponseEntity<LeagueEntryDto> = restTemplate.exchange(Globals.API_LEAGUE_ENTRY_INFO + "{encryptedSummonerId}", HttpMethod.GET, httpEntity, LeagueEntryDto::class.java)
        leagueEntryDto = matchListEntity.body
    }


}