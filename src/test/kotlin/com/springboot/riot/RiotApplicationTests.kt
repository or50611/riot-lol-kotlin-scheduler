package com.springboot.riot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import org.junit.jupiter.api.BeforeEach
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.http.MediaType
import org.junit.jupiter.api.Assertions

@WebAppConfiguration
@SpringBootTest
class RiotApplicationTests {
	
	lateinit var mockMvc: MockMvc
	
	@Autowired
	lateinit var webApplicationContext: WebApplicationContext
	
	@BeforeEach
	fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .build();
    }
	
	@Test
	fun 소환사명이_존재하지않는경우_404(){
		var name: String = "존재하지않는소환사이름.............."
		var uri: String = "/summonerName"
		var mvcResult: MvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri+"?summonerName="+name)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn()
		
		var status = mvcResult.getResponse().getStatus()
		Assertions.assertEquals(404, status)
	}
	
}
