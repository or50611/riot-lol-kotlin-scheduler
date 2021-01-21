package com.springboot.riot.data.rune.impl

import com.google.gson.Gson
import com.springboot.riot.data.rune.dto.RuneDto
import com.springboot.riot.data.rune.mapper.RuneMapper
import com.springboot.riot.data.rune.service.RuneDataService
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import com.springboot.riot.global.common.RiotFileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest

@Service
class RuneDataImpl: RuneDataService {

    @Autowired
    lateinit var runeMapper: RuneMapper

    override fun runeJsonInfo() {
        val version = "11.1.1"

        val request: HttpServletRequest = RiotApiUtil.getCurrentRequest()
        val gson: Gson = Gson()

        var runeDto: RuneDto
        val input: InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version+"/data/ko_KR/runesReforged.json")
        val uploadPath: String = request.servletContext.getRealPath("riotImage/rune/")
        val imageDataPath: String = Globals.URL_JSON_DATA_PATH+"img/"

        var iconSplit: String? = null
        runeDto = gson.fromJson(InputStreamReader(input), RuneDto::class.java)

        runeDto.forEach { runeDto ->
            iconSplit = runeDto.icon?.split("/")?.last()
            runeDto.fileNm = iconSplit

            runeDto.fileNm?.let { RiotFileUtil.imageDownload(imageDataPath+runeDto.icon?.replace(it,""), uploadPath, it) }

            runeMapper.insertRuneInfo(runeDto)

            runeDto.slots?.forEach { slots ->
                slots.runes?.forEach { runes ->
                    iconSplit = runes.icon?.split("/")?.last()

                    runes.parentId = runeDto.id
                    runes.fileNm = iconSplit

                    runes.fileNm?.let { RiotFileUtil.imageDownload(imageDataPath+runes.icon?.replace(it,""), uploadPath, it) }

                    runeMapper.insertRuneInfo(runes)
                }
            }
        }
    }
}