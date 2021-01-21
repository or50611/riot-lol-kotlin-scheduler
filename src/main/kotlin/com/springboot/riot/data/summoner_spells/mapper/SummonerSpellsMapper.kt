package com.springboot.riot.data.summoner_spells.mapper

import com.springboot.riot.data.summoner_spells.dto.SummonerSpellsDataDto
import com.springboot.riot.data.summoner_spells.dto.SummonerSpellsImageDto
import org.apache.ibatis.annotations.Mapper


@Mapper
interface SummonerSpellsMapper {

    fun insertSummonerSpellsBasic(summonerSpellsDataDto: SummonerSpellsDataDto): Int

    fun insertSummonerSpellsImage(summonerSpellsImageDto: SummonerSpellsImageDto): Int

}
