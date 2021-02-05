package com.springboot.riot.global.common

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import com.springboot.riot.global.Globals
import com.springboot.riot.global.RiotApiKey
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.servlet.http.HttpServletRequest

class RiotApiUtil {
	
	companion object {
		fun <T> setHeaders(): HttpEntity<T> {
			var headers = HttpHeaders()
			headers.set(HttpHeaders.ORIGIN, Globals.RIOT_URL)
			headers.set(Globals.X_RIOT_TOKEN, RiotApiKey.API_KEY)
					
			return HttpEntity<T>(headers)
		}

		fun getUrl(url: String): InputStream {
			val urlObject: URL? = URL(url)
			val urlConnection: HttpURLConnection = urlObject?.openConnection() as HttpURLConnection
			return urlConnection.inputStream
		}

		fun getCurrentRequest(): HttpServletRequest {
			val sra: ServletRequestAttributes = RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes
			return sra.request
		}
	}
	
}