package com.springboot.riot.data.summoner_spells.controller

import com.springboot.riot.data.summoner_spells.service.SummonerSpellsDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SummonerSpellsDataController {

    @Autowired
    lateinit var summonerSpellsDataService: SummonerSpellsDataService

    @RequestMapping("/summonerSpells")
    fun summonerSpellsInfo(): String {
        summonerSpellsDataService.summonerSpellsJsonInfo()
        return ""
    }
}