package com.springboot.riot.data.version.mapper

import com.springboot.riot.data.version.vo.VersionVo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface VersionMapper {

    fun insertVersionInfo(dataMap: HashMap<String,String>): Int

    fun updateVersionInfo(dataMap: MutableMap<String,String?>): Int

    fun selectVersionList(): List<VersionVo>

}