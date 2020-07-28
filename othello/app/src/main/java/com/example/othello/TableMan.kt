package com.example.othello

class TableMan {
    private var cellXnum:Int = 8    // X方向のマス数
    private var cellYnum:Int = 8    // Y方向のマス数

    // 盤面
    public var board = Array(cellXnum) { IntArray(cellYnum){common.CELL_EMPTY} }
        private set

    public fun initialize( cellXnum:Int = 8, cellYnum:Int = 8 ){
        // 盤面のサイズ決定
        this.cellXnum = cellXnum
        this.cellYnum = cellYnum

        // 盤面の初期化
        this.board = Array(cellXnum) {IntArray(cellYnum){common.CELL_EMPTY}}
    }

    public fun initialPlacement() {
        // 初期配置
        putStone(3, 3, common.CELL_BLACK)
        putStone(3, 4, common.CELL_WHITE)
        putStone(4, 3, common.CELL_WHITE)
        putStone(4, 4, common.CELL_BLACK)
    }

    public fun countStone(color:Int):Int{
        var stoneNum = 0

        for (i in 0..7) {
            for (j in 0..7) {
                if ( board[i][j] == color ){
                    println("${i} ${j} ${color}")
                    stoneNum ++
                }
            }
        }
        return stoneNum
    }

    // 色指定で石を置く
    public fun putStone( cellX:Int, cellY:Int, color:Int ):Boolean{
        if ( cellXnum <= cellX ) return false
        if ( cellYnum <= cellY ) return false

        this.board[cellX][cellY] = color
        return true
    }

    // 白石を置く
    public fun putStoneWhite( cellX:Int, cellY:Int ):Boolean{
        return putStone(cellX, cellY, common.CELL_WHITE)
    }

    // 黒石を置く
    public fun putStoneBlack( cellX:Int, cellY:Int ):Boolean{
        return putStone(cellX, cellY, common.CELL_BLACK)
    }

    // Debug用途
    public fun exportBoard(){
        for (i in 0..7) {
            for (j in 0..7) {
                print("${board[i][j]} ")
            }
            print("\n")
        }
        print("\n")
    }
}

