package com.springboot.riot.global.common

import java.io.*
import java.net.URL

class RiotFileUtil {

    companion object {
        fun imageDownload(imageDataPath:String, uploadPath:String, imgNm:String) {
            val url:URL = URL(imageDataPath + imgNm)
            val input: InputStream = BufferedInputStream(url.openStream())
            val out: ByteArrayOutputStream = ByteArrayOutputStream()
            val buf = ByteArray(1024)
            var n:Int = 0

            while (n != -1) {
                out.write(buf, 0, n)
                n = input.read(buf)
            }
            input.close()
            out.close()

            val response = out.toByteArray()

            val fileDir:File = File(uploadPath)
            if(!fileDir.isDirectory){
                fileDir.mkdirs()
            }

            val fileData:File = File(uploadPath, imgNm)
            println("$uploadPath$imgNm")
            val fos: FileOutputStream = FileOutputStream("$uploadPath$imgNm")
            fos.write(response)
            fos.close()
            if(!fileData.exists()) {
            }

        }
    }
}