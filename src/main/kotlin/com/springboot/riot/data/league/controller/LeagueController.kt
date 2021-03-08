package com.springboot.riot.data.league.controller

import com.springboot.riot.data.league.service.LeagueDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LeagueController {

    @Autowired
    lateinit var leagueDataService: LeagueDataService

    @RequestMapping("/league/entries")
    fun leagueEntriesInfo(): String {
        leagueDataService.leagueEntriesJsonInfo()
        return ""
    }

}