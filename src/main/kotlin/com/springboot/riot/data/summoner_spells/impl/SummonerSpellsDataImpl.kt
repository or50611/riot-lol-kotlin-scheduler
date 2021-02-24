package com.springboot.riot.data.summoner_spells.impl

import com.google.gson.Gson
import com.springboot.riot.data.summoner_spells.dto.SummonerSpellsDto
import com.springboot.riot.data.summoner_spells.mapper.SummonerSpellsMapper
import com.springboot.riot.data.summoner_spells.service.SummonerSpellsDataService
import com.springboot.riot.data.version.dto.VersionDto
import com.springboot.riot.data.version.mapper.VersionMapper
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import com.springboot.riot.global.common.RiotFileUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest

@Service
class SummonerSpellsDataImpl: SummonerSpellsDataService {

    var logger: Logger = LoggerFactory.getLogger(SummonerSpellsDataService::class.java)

    @Autowired
    lateinit var versionMapper: VersionMapper

    @Autowired
    lateinit var summonerSpellsMapper: SummonerSpellsMapper

    override fun summonerSpellsJsonInfo() {

        val findVersion = versionMapper.selectVersionList().firstOrNull { it.title == "summoner" }

        val vGson: Gson = Gson()
        val versionDto: VersionDto
        val vInput: InputStream = RiotApiUtil.getUrl(Globals.URL_VERSION_JSON)

        versionDto = vGson.fromJson(InputStreamReader(vInput), VersionDto::class.java)

        versionDto.n.let { version ->
            if (version?.summoner == findVersion?.version) {
                return
            }
            logger.info("SUMMONER SPELL 업데이트 시작 : {}", version?.summoner)

            val gson: Gson = Gson()
            var summonerSpellsDto: SummonerSpellsDto? = SummonerSpellsDto()
            val input: InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version?.summoner+"/data/ko_KR/summoner.json")
            summonerSpellsDto = gson.fromJson(InputStreamReader(input), SummonerSpellsDto::class.java)

            val uploadPath: String = RiotApiUtil.getDirPath("riotImage/summoner_spell/")
            val imageDataPath: String = Globals.URL_JSON_DATA_PATH+version?.summoner+"/img/spell/"

            //소환사스킬 데이터, 이미지
            summonerSpellsDto.getDataProperties()?.forEach { e ->
                e.value.image?.key = e.value.key

                summonerSpellsMapper.insertSummonerSpellsBasic(e.value)
                e.value.image?.let { summonerSpellsMapper.insertSummonerSpellsImage(it) }

                RiotFileUtil.imageDownload(imageDataPath,uploadPath,e.key+".png")
            }

            val dataMap = mutableMapOf("title" to "summoner", "version" to version?.summoner)

            versionMapper.updateVersionInfo(dataMap);

            logger.info("SUMMONER SPELL 업데이트 종료 : {}", version?.summoner)
        }


    }
}