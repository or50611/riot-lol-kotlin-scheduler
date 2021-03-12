package com.springboot.riot.data.league.mapper

import com.springboot.riot.data.dto.LeagueEntryDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface LeagueMapper {

    fun insertLeagueEntry(leagueEntryDto: LeagueEntryDto): Int

}