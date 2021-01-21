package com.springboot.riot.data.items.impl

import com.google.gson.Gson
import com.springboot.riot.data.items.dto.ItemDto
import com.springboot.riot.data.items.mapper.ItemMapper
import com.springboot.riot.data.items.service.ItemDataService
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import com.springboot.riot.global.common.RiotFileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader
import javax.servlet.http.HttpServletRequest

@Service
class ItemDataImpl: ItemDataService {

    @Autowired
    lateinit var itemMapper: ItemMapper

    override fun itemJsonInfo() {
        val version = "11.1.1"

        val request: HttpServletRequest = RiotApiUtil.getCurrentRequest()
        val gson: Gson = Gson()
        var itemDto: ItemDto? = ItemDto()
        val input: InputStream = RiotApiUtil.getUrl(Globals.URL_JSON_DATA_PATH+version+"/data/ko_KR/item.json")
        itemDto = gson.fromJson(InputStreamReader(input), ItemDto::class.java)

        val uploadPath: String = request.servletContext.getRealPath("riotImage/item/")
        val imageDataPath: String = Globals.URL_JSON_DATA_PATH+version+"/img/item/"

        //아이템이미지데이터
        itemDto.getDataProperties()?.forEach { e ->
            if(e.key == "1001"){
                RiotFileUtil.imageDownload(imageDataPath,uploadPath,e.key+".png")
            }
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

    }
}