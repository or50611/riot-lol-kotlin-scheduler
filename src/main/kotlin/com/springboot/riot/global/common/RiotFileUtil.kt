package com.springboot.riot.global.common

import com.springboot.riot.global.Globals
import java.io.*
import java.net.URL

class RiotFileUtil {

    companion object {
        fun imageDownload(imageDataPath:String, uploadPath:String, championName:String) {
            val fileName = "$championName.png"
            val url:URL = URL(imageDataPath+fileName)
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

            val fileData:File = File(uploadPath,fileName)
            println("$uploadPath$fileName")
            if(!fileData.exists()) {
                val fos: FileOutputStream = FileOutputStream("$uploadPath$fileName")
                fos.write(response)
                fos.close()
            }

        }
    }
}