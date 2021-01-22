package com.springboot.riot.data.version.mapper

import org.apache.ibatis.annotations.Mapper

@Mapper
interface VersionMapper {

    fun insertVersionInfo(dataMap: HashMap<String,String>): Int

}