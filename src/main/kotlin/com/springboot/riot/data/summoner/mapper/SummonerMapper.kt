package com.springboot.riot.data.summoner.mapper

import com.springboot.riot.data.summoner.vo.SummonerInfoVo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface SummonerMapper {

    fun selectSummonerList(): List<SummonerInfoVo>

}