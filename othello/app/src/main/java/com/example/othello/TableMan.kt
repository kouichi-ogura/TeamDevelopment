package com.example.othello

import androidx.appcompat.app.AppCompatActivity

class TableMan {
    private var cellXnum:Int = 8    // X方向のマス数
    private var cellYnum:Int = 8    // Y方向のマス数

    // 盤面
    public var board = Array(cellXnum) { IntArray(cellYnum){common.CELL_EMPTY} }
        private set

    public fun Initialize( cellXnum:Int = 8, cellYnum:Int = 8 ){
        // 盤面のサイズ決定
        this.cellXnum = cellXnum
        this.cellYnum = cellYnum

        // 盤面の初期化
        this.board = Array(cellXnum) {IntArray(cellYnum){common.CELL_EMPTY}}
    }

    public fun InitialPlacement() {
        // 初期配置
        PutStone(3, 3, common.CELL_BLACK)
        PutStone(3, 4, common.CELL_WHITE)
        PutStone(4, 3, common.CELL_WHITE)
        PutStone(4, 4, common.CELL_BLACK)
    }

    public fun CountStone(color:Int):Int{
        var StoneNum = 0

        for (i in 0..7) {
            for (j in 0..7) {
                if ( board[i][j] == color ){
                    println("${i} ${j} ${color}")
                    StoneNum ++
                }
            }
        }
        return StoneNum
    }

    // 色指定で石を置く
    public fun PutStone( cellX:Int, cellY:Int, color:Int ):Boolean{
        if ( cellXnum <= cellX ) return false
        if ( cellYnum <= cellY ) return false

        this.board[cellX][cellY] = color
        return true
    }

    // 白石を置く
    public fun PutStoneWhite( cellX:Int, cellY:Int ):Boolean{
        return PutStone(cellX, cellY, common.CELL_WHITE)
    }

    // 黒石を置く
    public fun PutStoneBlack( cellX:Int, cellY:Int ):Boolean{
        return PutStone(cellX, cellY, common.CELL_BLACK)
    }

    // Debug用途
    public fun ExportBoard(){
        for (i in 0..7) {
            for (j in 0..7) {
                print("${board[i][j]} ")
            }
            print("\n")
        }
        print("\n")
    }
}

