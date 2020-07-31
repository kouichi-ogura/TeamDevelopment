package com.example.othello

class TableMan {
    private var cellXnum:Int = 8    // X方向のマス数
    private var cellYnum:Int = 8    // Y方向のマス数

    // 盤面の配列
    var board = Array(cellXnum) { IntArray(cellYnum){Common.CELL_EMPTY} }
        private set

    // 初期化
    fun initialize( cellXnum:Int = 8, cellYnum:Int = 8 ){
        // 盤面のサイズ決定
        this.cellXnum = cellXnum
        this.cellYnum = cellYnum

        // 盤面の初期化
        this.board = Array(cellXnum) {IntArray(cellYnum){Common.CELL_EMPTY}}
    }

    // 初期配置
    fun initialPlacement() {
        // 初期配置
        putStone(3, 3, Common.CELL_BLACK)
        putStone(3, 4, Common.CELL_WHITE)
        putStone(4, 3, Common.CELL_WHITE)
        putStone(4, 4, Common.CELL_BLACK)

        // デバッグ用
        // deubugBoard()
    }

    // 石の数を数える
    fun countStone(color:Int):Int{
        var stoneNum = 0

        for (i in 0..7) {
            for (j in 0..7) {
                if ( board[i][j] == color ){
                    stoneNum ++
                }
            }
        }
        return stoneNum
    }

    // 白石を置く
    fun putStoneWhite( cellX:Int, cellY:Int ):Boolean{
        return putStone(cellX, cellY, Common.CELL_WHITE)
    }

    // 黒石を置く
    fun putStoneBlack( cellX:Int, cellY:Int ):Boolean{
        return putStone(cellX, cellY, Common.CELL_BLACK)
    }

    // 色指定で石を置く
    fun putStone( cellX:Int, cellY:Int, color:Int ):Boolean{
        if ( cellXnum <= cellX ) return false
        if ( cellYnum <= cellY ) return false

        this.board[cellX][cellY] = color
        return true
    }

    // Debug：盤面をレイアウト
    fun deubugBoard(){
        this.board = Array(cellXnum) {IntArray(cellYnum){Common.CELL_WHITE}}
        putStone(0, 0, Common.CELL_EMPTY)
        putStone(0, 2, Common.CELL_BLACK)
    }

    // Debug:盤面の情報を出力
    fun debugExportBoard(){
        for (i in 0..7) {
            for (j in 0..7) {
                print("${board[i][j]} ")
            }
            print("\n")
        }
        print("\n")
    }
}
