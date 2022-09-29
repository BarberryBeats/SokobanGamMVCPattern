package com.example.sokobangamemvcpattern

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class Levels(activity: MainActivity) {
    private val mActivity: Activity = activity

    private val lvl1: Array<Array<Int>> = arrayOf(
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
        arrayOf(2, 1, 2, 0, 0, 0, 2, 2),
        arrayOf(2, 0, 0, 0, 0, 0, 2, 2),
        arrayOf(2, 0, 2, 0, 2, 0, 2, 2),
        arrayOf(2, 0, 2, 3, 2, 0, 2, 2),
        arrayOf(2, 0, 0, 0, 2, 4, 2, 2),
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
    )

    private val lvl2: Array<Array<Int>> = arrayOf(
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
        arrayOf(2, 1, 0, 0, 2, 0, 0, 2),
        arrayOf(2, 0, 0, 3, 0, 0, 0, 2),
        arrayOf(2, 0, 0, 2, 0, 4, 0, 2),
        arrayOf(2, 0, 0, 2, 2, 2, 0, 2),
        arrayOf(2, 0, 0, 0, 0, 0, 0, 2),
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
    )

    private val lvl3: Array<Array<Int>> = arrayOf(
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
        arrayOf(2, 1, 0, 0, 2, 0, 0, 2),
        arrayOf(2, 0, 3, 0, 0, 0, 0, 2),
        arrayOf(2, 0, 0, 2, 0, 4, 0, 2),
        arrayOf(2, 0, 0, 2, 2, 2, 0, 2),
        arrayOf(2, 0, 0, 0, 0, 0, 0, 2),
        arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
    )

    fun getLvl(lvl: Int): Array<Array<Int>> {

        return when (lvl) {
            1 -> lvl1
            2 -> lvl2
            3 -> lvl3
            4 -> convert(loadLevelFromFile("level4.sok"))
            5 -> convert(loadLevelFromFile("level5.sok"))
            6 -> convert(loadLevelFromFile("level6.sok"))
            else -> lvl1
        }
    }

    private fun loadLevelFromFile(fileName: String): String {
            val myInputStream: InputStream = mActivity.assets.open(fileName)
            val buffer = ByteArray(myInputStream.available())

            myInputStream.read(buffer)
            myInputStream.close()

            val text = String(buffer)

            var line = ""
            for (i in text.indices) {
                if (text[i] in '0'..'4') {
                    line += text[i]
                }
                if (text[i] == '\n' || text[i] == 'A' || i == text.lastIndex) {
                    line += 'A'
                }
            }

            return line
    }

    private fun convert(text: String): Array<Array<Int>> {
        var size = 0
        var rowLine = ""

        for (i in text.indices) {
            val symbol: Char = text[i]
            if (symbol == 'A') {
                size++
            }
        }

        val stringArray: Array<String> = Array(size) {"0"}
        val array: Array<Array<Int>> = Array(size) {Array(0) {0} }

        size = 0
        var sizeLine = 0

        for (i in text.indices) {
            val symbol: Char = text[i]
            if (symbol != 'A') {
                rowLine += symbol
                sizeLine++
            }
            if (symbol == 'A') {
                array[size] = Array(sizeLine) {0}
                stringArray[size] = rowLine
                rowLine = ""
                sizeLine = 0
                size++
            }
        }

        for (i in stringArray.indices) {
            for (j in stringArray[i].indices) {
                val symbol: Char = stringArray[i][j]
                array[i][j] = symbol.digitToInt()
            }
        }

        return array
    }
}