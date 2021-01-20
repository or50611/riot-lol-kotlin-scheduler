package com.springboot.riot.data.champion.controller

import com.springboot.riot.data.champion.service.ChampionDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ChampionDataController {

    @Autowired
    lateinit var championDataService: ChampionDataService

    @RequestMapping("/champion")
    fun championInfo(): String {
        championDataService.championJsonInfo()
        return ""
    }

}