package com.springboot.riot.data.champion.mapper

import com.springboot.riot.data.champion.dto.ChampionDataDto
import com.springboot.riot.data.champion.dto.ChampionImageDto
import com.springboot.riot.data.champion.dto.ChampionInfoDto
import com.springboot.riot.data.champion.dto.ChampionStatsDto
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ChampionMapper {

    fun insertChampionBasic(championDataDto: ChampionDataDto): Int

    fun insertChampionImage(championDataDto: ChampionImageDto): Int

    fun insertChampionInfo(championDataDto: ChampionInfoDto): Int

    fun insertChampionStats(championDataDto: ChampionStatsDto): Int

    fun insertChampionTags(map: HashMap<String, String>): Int

    fun deleteChampionTags(str: String): Int

}