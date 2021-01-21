package com.springboot.riot.data.rune.controller

import com.springboot.riot.data.rune.service.RuneDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RuneDataController {

    @Autowired
    lateinit var runeDataService: RuneDataService

    @RequestMapping("/rune")
    fun itemInfo(): String {
        runeDataService.runeJsonInfo()
        return ""
    }
}