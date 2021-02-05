package com.springboot.riot.data.summoner.contoller

import com.springboot.riot.data.summoner.service.SummonerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SummonerController {

    @Autowired
    lateinit var summonerService: SummonerService

    @RequestMapping("/summoner")
    fun summonerInfo(): String {
        summonerService.summonerJsonInfo()

        return ""
    }

}