package com.springboot.riot.data.items.controller

import com.springboot.riot.data.items.service.ItemDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ItemDataController {

    @Autowired
    lateinit var itemDataService: ItemDataService

    @RequestMapping("/item")
    fun itemInfo(): String {
        itemDataService.itemJsonInfo()
        return ""
    }
}