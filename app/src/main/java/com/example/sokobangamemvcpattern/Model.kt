package com.example.sokobangamemvcpattern

class Model(mainActivity: MainActivity) {



    companion object{
        const val EMPTY: Int = 0
        const val BOX: Int = 3
        const val HOLE: Int = 4
        const val PLAYER: Int = 1
        const val GO_UP: Int = 11
        const val GO_DOWN: Int = 14
        const val GO_lEFT: Int = 13
        const val GO_RIGHT: Int = 12

        private var boyColumn: Int = 0 // стоблик
        private var boyRow: Int = 0 // строка
        const val LEFT = "left"
        const val RIGHT = "right"
        const val UP = "up"
        const val DOWN = "down"
    }
    private var levels = Levels(mainActivity)
    private var currentLvl = 1

    private var thisLevel: Array<Array<Int>> = emptyArray()

    private var countBox: Int = 0
    private var countGoal: Int = 0
    private var isFinished = false

    private var isBoxTriggered: Boolean = false

    var actions: ArrayList<Int> = ArrayList()

    private var listOfBoxes: ArrayList<Pair<Int, Int>> = ArrayList()
    private var listCurrent: ArrayList<Pair<Int, Int>> = ArrayList()


    fun isFinishGame(){
        if (isBoxTriggered) {
            listOfBoxes.clear()
            find(BOX)
            check()
            winGame()
        }
    }

     fun winGame() {
        for (i in listCurrent.indices) {
            if (thisLevel[listCurrent[i].first][listCurrent[i].second] != PLAYER
                && thisLevel[listCurrent[i].first][listCurrent[i].second] != BOX) {
                thisLevel[listCurrent[i].first][listCurrent[i].second] = HOLE
            }
        }
    }

    fun initialization() {
        listOfBoxes.clear()
        listCurrent.clear()

        thisLevel = emptyArray()
        thisLevel = Array(levels.getLvl(currentLvl).size) { Array(0) { 0 } }

        for (i in levels.getLvl(currentLvl).indices) {
            thisLevel[i] = Array(levels.getLvl(currentLvl)[i].size) { 0 }
        }

        for (r in levels.getLvl(currentLvl).indices) {
            for (c in levels.getLvl(currentLvl)[r].indices) {
                thisLevel[r][c] = levels.getLvl(currentLvl)[r][c]
            }
        }

        find(HOLE)
        find(PLAYER)
        listCurrent.sortWith(compareBy({ it.first }, { it.second }))
    }

    fun getCanvas(): Array<Array<Int>> {
        return thisLevel
    }

     fun moveUp() {
        if (thisLevel[boyRow - 1][boyColumn] == EMPTY || thisLevel[boyRow - 1][boyColumn] == EMPTY) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow - 1][boyColumn] = PLAYER
            isBoxTriggered = false
            boyRow -= 1
        } else if (thisLevel[boyRow - 1][boyColumn] == BOX && (thisLevel[boyRow - 2][boyColumn] == EMPTY || thisLevel[boyRow - 2][boyColumn] == HOLE)) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow - 1][boyColumn] = PLAYER
            thisLevel[boyRow - 2][boyColumn] = BOX
            isBoxTriggered = true
            boyRow -= 1
        }
         winGame()

     }



     fun moveDown() {
        if (thisLevel[boyRow + 1][boyColumn] == EMPTY || thisLevel[boyRow + 1][boyColumn] == HOLE) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow + 1][boyColumn] = PLAYER
            isBoxTriggered = false
            boyRow += 1
        } else if (thisLevel[boyRow + 1][boyColumn] == BOX && (thisLevel[boyRow + 2][boyColumn] == EMPTY || thisLevel[boyRow + 2][boyColumn] == HOLE)) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow + 1][boyColumn] = PLAYER
            thisLevel[boyRow + 2][boyColumn] = BOX
            isBoxTriggered = true
            boyRow += 1
        }
         winGame()

    }

     fun moveRight() {
        if (thisLevel[boyRow][boyColumn + 1] == EMPTY || thisLevel[boyRow][boyColumn + 1] == HOLE) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow][boyColumn + 1] = PLAYER
            isBoxTriggered = false
            boyColumn += 1
        } else if (thisLevel[boyRow][boyColumn + 1] == BOX && (thisLevel[boyRow][boyColumn + 2] == EMPTY || thisLevel[boyRow][boyColumn + 2] == HOLE)) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow][boyColumn + 1] = PLAYER
            thisLevel[boyRow][boyColumn + 2] = BOX
            isBoxTriggered = true
            boyColumn += 1
        }
         winGame()
    }

     fun moveLeft() {

        if (thisLevel[boyRow][boyColumn - 1] == EMPTY || thisLevel[boyRow][boyColumn - 1] == HOLE) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow][boyColumn - 1] = PLAYER
            isBoxTriggered = false
            boyColumn -= 1
        } else if (thisLevel[boyRow][boyColumn - 1] == BOX && (thisLevel[boyRow][boyColumn - 2] == EMPTY || thisLevel[boyRow][boyColumn - 2] == HOLE)) {
            thisLevel[boyRow][boyColumn] = EMPTY
            thisLevel[boyRow][boyColumn - 1] = PLAYER
            thisLevel[boyRow][boyColumn - 2] = BOX
            isBoxTriggered = true
            boyColumn -= 1
        }
         winGame()
    }

    private fun find(item: Int): Pair<Int, Int> {
        for (r in thisLevel.indices) {
            for (col in thisLevel[r].indices) {
                when (item) {
                    PLAYER -> {
                        if (thisLevel[r][col] == PLAYER) {
                            boyRow = r
                            boyColumn = col
                            return Pair(r, col)
                        }
                    }
                    HOLE -> {
                        if (thisLevel[r][col] == item) {
                            listCurrent.add(Pair(r, col))
                            countGoal += 1
                        }
                    }
                    BOX -> {
                        if (thisLevel[r][col] == item) {
                            listOfBoxes.add(Pair(r, col))
                            countBox += 1
                        }
                    }
                }
            }
        }
        return Pair(0, 0)
    }

    private fun check() {
        listOfBoxes.sortWith(compareBy({ it.first }, { it.second }))

        if (listOfBoxes.equals(listCurrent)) {
            isFinished = true
            gameFinished()
        }
    }

    fun getGameFinished(): Boolean {
        return isFinished
    }

    fun getCurrentLevel(): Int {
        return currentLvl
    }

    fun chooseNextLvl() {
        if (currentLvl != 6) currentLvl += 1
        else currentLvl = 1
    }

    fun choosePrevLvl() {
        if (currentLvl != 1) currentLvl -= 1
        else currentLvl = 3
    }

    private fun gameFinished() {
        chooseNextLvl()
        initialization()
        isFinished = false
    }


}