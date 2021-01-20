package com.springboot.riot.data.items.mapper

import com.springboot.riot.data.items.dto.ItemDataDto
import com.springboot.riot.data.items.dto.ItemGoldDto
import com.springboot.riot.data.items.dto.ItemImageDto
import com.springboot.riot.data.items.dto.ItemMapsDto
import org.apache.ibatis.annotations.Mapper


@Mapper
interface ItemMapper {

    fun insertItemBasic(itemDataDto: ItemDataDto): Int
    fun insertItemImage(itemDataDto: ItemImageDto): Int
    fun insertItemGold(itemDataDto: ItemGoldDto): Int
    fun insertItemMaps(itemDataDto: ItemMapsDto): Int

    fun insertItemTags(map: HashMap<String, String>): Int
    fun insertItemInto(map: HashMap<String, String>): Int
    fun insertItemStats(map: HashMap<String, String>): Int

    fun deleteItemTags(str: String): Int
    fun deleteItemInto(str: String): Int
    fun deleteItemStats(str: String): Int
}
