package com.springboot.riot.data.version.controller

import com.springboot.riot.data.version.service.VersionDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class VersionDataController {

    @Autowired
    lateinit var versionDataService: VersionDataService

    @RequestMapping("/version")
    fun versionInfo(): String {
        versionDataService.versionJsonInfo()
        return ""
    }


}