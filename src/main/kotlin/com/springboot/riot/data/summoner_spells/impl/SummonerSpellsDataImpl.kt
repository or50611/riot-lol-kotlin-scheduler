package com.springboot.riot.data.summoner_spells.impl

import com.google.gson.Gson
import com.springboot.riot.data.summoner_spells.dto.SummonerSpellsDto
import com.springboot.riot.data.summoner_spells.mapper.SummonerSpellsMapper
import com.springboot.riot.data.summoner_spells.service.SummonerSpellsDataService
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import com.springboot.riot.global.common.RiotFileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest

@Service
class SummonerSpellsDataImpl: SummonerSpellsDataService {

    @Autowired
    lateinit var summonerSpellsMapper: SummonerSpellsMapper

    override fun summonerSpellsJsonInfo() {
        val version = "11.1.1"

        val request: HttpServletRequest = RiotApiUtil.getCurrentRequest()
        val gson: Gson = Gson()
        var summonerSpellsDto: SummonerSpellsDto? = SummonerSpellsDto()
        val input: InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version+"/data/ko_KR/summoner.json")
        summonerSpellsDto = gson.fromJson(InputStreamReader(input), SummonerSpellsDto::class.java)

        val uploadPath: String = request.servletContext.getRealPath("riotImage/summoner_spell/")
        val imageDataPath: String = Globals.URL_JSON_DATA_PATH+version+"/img/spell/"

        //소환사스킬 데이터, 이미지
        summonerSpellsDto.getDataProperties()?.forEach { e ->
            e.value.image?.key = e.value.key

            summonerSpellsMapper.insertSummonerSpellsBasic(e.value)
            e.value.image?.let { summonerSpellsMapper.insertSummonerSpellsImage(it) }

            RiotFileUtil.imageDownload(imageDataPath,uploadPath,e.key+".png")
            println("key : "+e.key)
        }

    }
}