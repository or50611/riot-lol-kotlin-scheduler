package com.springboot.riot.data.champion.mapper

import com.springboot.riot.data.champion.dto.*
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChampionMapper {
    //챔피온기본
    fun insertChampionBasic(championDataDto: ChampionDataDto): Int

    fun insertChampionImage(championImageDto: ChampionImageDto): Int

    fun insertChampionInfo(championInfoDto: ChampionInfoDto): Int

    fun insertChampionStats(championStatsDto: ChampionStatsDto): Int

    fun insertChampionTags(map: HashMap<String, String>): Int

    fun deleteChampionTags(str: String): Int

    //스킬
    fun insertChampionSpellBasic(championSpellsDto: ChampionSpellsDto): Int

    fun insertChampionSpellImage(championSpellsImageDto: ChampionSpellsImageDto): Int

    fun insertChampionPassiveBasic(championPassiveDto: ChampionPassiveDto): Int

    fun insertChampionPassiveImage(championPassiveImageDto: ChampionPassiveImageDto): Int

}