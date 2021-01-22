package com.springboot.riot.data.summoner

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import com.springboot.riot.data.summoner.dto.SummonerDto
import org.springframework.beans.factory.annotation.Autowired
import com.springboot.riot.data.summoner.service.UserInfoService
import com.google.gson.Gson
import com.springboot.riot.data.summoner.dto.UserMatchInfoDto
import org.springframework.web.bind.annotation.RequestMapping

@RestController
class UserInfoController {
	
	@Autowired
	lateinit var userInfoService: UserInfoService
	
	@RequestMapping("/summonerName")
	fun summonerName(@RequestParam(value = "summonerName")name: String): ResponseEntity<Any> {
		val gson = Gson()
		var result: String?
		var userMatchInfoDto: UserMatchInfoDto? = UserMatchInfoDto()
		
		val summonerDto: SummonerDto? = userInfoService.summonerInfo(name)
		summonerDto?.let{
			if(summonerDto.status != HttpStatus.OK){
				result = gson.toJson(summonerDto.errorBodyDto)

				val status = summonerDto.status?: HttpStatus.BAD_REQUEST
				return ResponseEntity<Any>(result, status)
			}
			
			userMatchInfoDto = userInfoService.matchDetailList(summonerDto)
			userMatchInfoDto?.let{
				if(userMatchInfoDto?.status != HttpStatus.OK){
					result = gson.toJson(userMatchInfoDto?.errorBodyDto)
							
					val status = userMatchInfoDto?.status?: HttpStatus.BAD_REQUEST
					return ResponseEntity<Any>(result, status)
				}
			}
			
			userMatchInfoDto?.name = summonerDto.name
		}
		
		result = gson.toJson(userMatchInfoDto)
		
		return ResponseEntity<Any>(result,HttpStatus.OK)
	}

}