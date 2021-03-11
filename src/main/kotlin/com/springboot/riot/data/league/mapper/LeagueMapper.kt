package com.springboot.riot.data.league.mapper

import com.springboot.riot.data.dto.LeagueEntryDto

interface LeagueMapper {

    fun insertLeagueEntry(leagueEntryDto: LeagueEntryDto): Int

}