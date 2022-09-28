package com.example.sokobangamemvcpattern

import java.io.*
import java.net.ServerSocket


class Server(port: Int) {
     private fun loadLevelFromFile(fileName: String): String? {
        return try {
            val file = File(fileName)
            val size = file.length().toInt()
            var array: CharArray? = CharArray(size)
            val `in` = FileInputStream(fileName)
            var unicode: Int
            var index = 0
            while (`in`.read().also { unicode = it } != -1) {
                val symbol = unicode.toChar()
                if (symbol in '0'..'4') {
                    array!![index] = symbol
                    index += 1
                } else if (symbol == '\n') {
                    array!![index] = 'A'
                    index += 1
                }
            }
            if (array!![index] != '\n') {
                array[index] = 'A'
                index += 1
            }
            val text = String(array, 0, index)
            `in`.close()
            array = null
            text
        } catch (fne: FileNotFoundException) {
            println(fne)
            null
        } catch (ioe: IOException) {
            println(ioe)
            null
        }
    }

    init {
        try {
            val serverSocket = ServerSocket(port)
            while (true) {
                val socket = serverSocket.accept()
                println("socket $socket")
                println("-------------------$socket")
                val inputStream = socket.getInputStream()
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val `in` = BufferedReader(inputStreamReader)
                val level = `in`.readLine()
                println("level = $level")
                val outputStream = socket.getOutputStream()
                val out = PrintWriter(outputStream)
                val prefixFileName = "levels/"
                val endFileName = ".sok"
                val answer = loadLevelFromFile(prefixFileName + level + endFileName)
                out.println(answer)
                out.flush()
                socket.close()
            }
        } catch (ioe: IOException) {
            println(ioe)
        }
    }
}