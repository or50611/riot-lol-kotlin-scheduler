package com.springboot.riot.global.common

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import com.springboot.riot.global.Globals

class RiotApiUtil {
	
	companion object {
		fun <T> setHeaders(): HttpEntity<T> {
			var headers = HttpHeaders()
			headers.set(HttpHeaders.ORIGIN, Globals.RIOT_URL)
			headers.set(Globals.X_RIOT_TOKEN, Globals.API_KEY)
					
			return HttpEntity<T>(headers)
		}
	}
	
}