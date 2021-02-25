package com.springboot.riot.global.batch

import com.springboot.riot.data.champion.service.ChampionDataService
import com.springboot.riot.data.items.service.ItemDataService
import com.springboot.riot.data.rune.service.RuneDataService
import com.springboot.riot.data.summoner.service.SummonerService
import com.springboot.riot.data.summoner_spells.service.SummonerSpellsDataService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class BatchBaseScheduler {

    var logger: Logger = LoggerFactory.getLogger(BatchBaseScheduler::class.java)

    @Autowired
    lateinit var championDataService: ChampionDataService

    @Autowired
    lateinit var itemDataService: ItemDataService

    @Autowired
    lateinit var runeDataService: RuneDataService

    @Autowired
    lateinit var summonerSpellsDataService: SummonerSpellsDataService

    @Autowired
    lateinit var summonerService: SummonerService

    @Scheduled(fixedDelay = 3600000)
    fun championDataUpdate() {
        logger.info("championDataUpdate 호출 : {} ", Date())
        championDataService.championJsonInfo()
    }

    @Scheduled(fixedDelay = 3600000)
    fun itemDataUpdate() {
        logger.info("itemDataUpdate 호출 : {} ", Date())
        itemDataService.itemJsonInfo()
    }

    @Scheduled(fixedDelay = 3600000)
    fun runeDataUpdate() {
        logger.info("runeDataUpdate 호출 : {} ", Date())
        runeDataService.runeJsonInfo()
    }

    @Scheduled(fixedDelay = 3600000)
    fun summonerSpellsDataUpdate() {
        logger.info("summonerSpellsDataUpdate 호출 : {} ", Date())
        summonerSpellsDataService.summonerSpellsJsonInfo()
    }

    @Scheduled(fixedDelay = 600000)
    fun matchUpdate() {
        logger.info("matchUpdate 호출 : {} ", Date())
        summonerService.summonerJsonInfo()
    }

}