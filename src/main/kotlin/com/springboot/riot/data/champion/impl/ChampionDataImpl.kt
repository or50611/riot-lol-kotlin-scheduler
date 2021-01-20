package com.springboot.riot.data.champion.impl

import com.google.gson.Gson
import com.springboot.riot.data.champion.dto.ChampionDataDto
import com.springboot.riot.data.champion.dto.ChampionDto
import com.springboot.riot.data.champion.mapper.ChampionMapper
import com.springboot.riot.data.champion.service.ChampionDataService
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import com.springboot.riot.global.common.RiotFileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest

@Service
class ChampionDataImpl : ChampionDataService {

    @Autowired
    lateinit var championMapper: ChampionMapper

    override fun championJsonInfo() {
        val version = "11.1.1"

        val request:HttpServletRequest = RiotApiUtil.getCurrentRequest()
        val gson:Gson = Gson()
        var championDto: ChampionDto? = ChampionDto()
        val input:InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version+"/data/ko_KR/champion.json")
        championDto = gson.fromJson(InputStreamReader(input), ChampionDto::class.java)

        val uploadPath: String = request.servletContext.getRealPath("riotImage/champion/")
        val imageDataPath: String = Globals.URL_JSON_DATA_PATH+version+"/img/champion/"

        //챔피언이미지데이터
        championDto.getAdditionalProperties()?.forEach { e ->
            if(e.key == "Aatrox"){
                RiotFileUtil.imageDownload(imageDataPath,uploadPath,e.key)
                println("key : "+e.key)
            }
        }

        var tagsMap:HashMap<String,String>
        championDto.getAdditionalProperties()?.values?.forEach { dataDto ->
            println("id : "+dataDto.key)
            dataDto.image?.key = dataDto.key
            dataDto.info?.key = dataDto.key
            dataDto.stats?.key = dataDto.key

//            championMapper.insertChampionBasic(dataDto)
//            dataDto.image?.let { championMapper.insertChampionImage(it) }
//            dataDto.info?.let { championMapper.insertChampionInfo(it) }
//            dataDto.stats?.let { championMapper.insertChampionStats(it) }

            dataDto.key?.let { championMapper.deleteChampionTags(it) }
            dataDto.tags?.forEach { str ->
                tagsMap = HashMap()
                dataDto.key?.let { tagsMap.put("key", it) }
                tagsMap["tag"] = str

                championMapper.insertChampionTags(tagsMap)
            }


        }
    }
}