package com.springboot.riot.data.items.impl

import com.google.gson.Gson
import com.springboot.riot.data.items.dto.ItemDto
import com.springboot.riot.data.items.mapper.ItemMapper
import com.springboot.riot.data.items.service.ItemDataService
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
class ItemDataImpl: ItemDataService {

    var logger: Logger = LoggerFactory.getLogger(ItemDataService::class.java)

    @Autowired
    lateinit var versionMapper: VersionMapper

    @Autowired
    lateinit var itemMapper: ItemMapper

    override fun itemJsonInfo() {

        val findVersion = versionMapper.selectVersionList().firstOrNull { it.title == "item" }

        val vGson = Gson()
        val versionDto: VersionDto
        val vInput: InputStream = RiotApiUtil.getUrl(Globals.URL_VERSION_JSON)

        versionDto = vGson.fromJson(InputStreamReader(vInput), VersionDto::class.java)

        versionDto.n.let { version ->
            if(version?.item == findVersion?.version) {
                return
            }
            logger.info("ITEM 업데이트 시작 : {}", version?.item)

            val gson = Gson()
            var itemDto: ItemDto? = ItemDto()
            val input: InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version?.item+"/data/ko_KR/item.json")
            itemDto = gson.fromJson(InputStreamReader(input), ItemDto::class.java)

            val uploadPath: String = RiotApiUtil.getDirPath("riotImage/item/")
            val imageDataPath: String = Globals.URL_JSON_DATA_PATH+version?.item+"/img/item/"

            //아이템이미지데이터
            itemDto.getDataProperties()?.forEach { e ->
                RiotFileUtil.imageDownload(imageDataPath,uploadPath,e.key+".png")
                println("key : "+e.key)
            }

            var dataMap:HashMap<String,String>
            itemDto.getDataProperties()?.forEach { e ->
                println("key : "+e.key)
                e.value.key = e.key
                e.value.gold?.key = e.key
                e.value.image?.key = e.key
                e.value.maps?.key = e.key

                itemMapper.insertItemBasic(e.value)
                e.value.gold?.let { itemMapper.insertItemGold(it) }
                e.value.image?.let { itemMapper.insertItemImage(it) }
                e.value.maps?.let { itemMapper.insertItemMaps(it) }

                itemMapper.deleteItemTags(e.key)
                e.value.tags?.forEach { str ->
                    dataMap = HashMap()
                    dataMap["key"] = e.key
                    dataMap["tag"] = str
                    itemMapper.insertItemTags(dataMap)
                }

                itemMapper.deleteItemInto(e.key)
                e.value.into?.forEach { str ->
                    dataMap = HashMap()
                    dataMap["key"] = e.key
                    dataMap["into"] = str
                    itemMapper.insertItemInto(dataMap)
                }

                itemMapper.deleteItemStats(e.key)
                e.value.getStatsProperties()?.forEach { stats ->
                    dataMap = HashMap()
                    dataMap["key"] = e.key
                    dataMap["statsNm"] = stats.key
                    dataMap["stats"] = stats.value.toString()
                    itemMapper.insertItemStats(dataMap)
                }

            }

            val itemMap = mutableMapOf("title" to "item", "version" to version?.item)

            versionMapper.updateVersionInfo(itemMap);

            logger.info("ITEM 업데이트 종료 : {}", version?.item)
        }
    }
}