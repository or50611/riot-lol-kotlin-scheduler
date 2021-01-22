package com.springboot.riot.data.summoner.impl

import com.springboot.riot.data.summoner.mapper.SummonerMapper
import com.springboot.riot.data.summoner.service.SummonerService
import com.springboot.riot.data.summoner.vo.SummonerInfoVo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SummonerInfoImpl: SummonerService {

    @Autowired
    lateinit var summonerMapper: SummonerMapper

    override fun summonerJsonInfo() {

        val summonerInfoVo: List<SummonerInfoVo> = summonerMapper.selectSummonerList()

        summonerInfoVo.forEach { vo ->

            if(vo.activityNm == "Faker") {


            }
        }
    }
}