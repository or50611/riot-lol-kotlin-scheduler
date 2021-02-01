package com.springboot.riot.data.version.impl

import com.google.gson.Gson
import com.springboot.riot.data.version.dto.VersionDto
import com.springboot.riot.data.version.mapper.VersionMapper
import com.springboot.riot.data.version.service.VersionDataService
import com.springboot.riot.global.Globals
import com.springboot.riot.global.common.RiotApiUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.InputStreamReader

@Service
class VersionDataImpl: VersionDataService {

    @Autowired
    lateinit var versionMapper: VersionMapper

    override fun versionJsonInfo() {

        val gson: Gson = Gson()
        val versionDto: VersionDto
        val input: InputStream = RiotApiUtil.getUrl(Globals.URL_VERSION_JSON)

        versionDto = gson.fromJson(InputStreamReader(input), VersionDto::class.java)

        var dataMap: HashMap<String,String>
        versionDto.n?.javaClass?.declaredFields?.forEach { field ->
            field.isAccessible = true
            var any:Any = field.get(versionDto.n)

            dataMap = HashMap()
            dataMap["title"] = field.name
            dataMap["version"] = any.toString()

            versionMapper.insertVersionInfo(dataMap)

        }
    }
}