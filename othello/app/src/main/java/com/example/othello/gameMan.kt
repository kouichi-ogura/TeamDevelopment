package com.example.othello

class GameMan {
    var currentturn: Int = common.CELL_BLACK // 手番保持用変数

    var x: Int = 1 // 取得したx座標
    var y: Int = 1 // 取得したy座標

    private var tm = TableMan()

    init {
        tm.initialize()
    }

    public fun putStone(x:Int, y:Int): Boolean {
        //TODO:置けるかチェック
        //IsPut()
        //  return false

        // 置けるならテーブル更新
        tm.putStone(x, y, currentturn)

        // TODO:次の手番判定
        // 下記は暫定処理
        if  (currentturn == common.CELL_BLACK){
            currentturn = common.CELL_WHITE
        }else{
            currentturn = common.CELL_BLACK
        }
        return true
    }

    public  fun getTable(): Array<IntArray> {
        return tm.board
    }

    public fun getWhiteStoneNum(): Int {
        return tm.countStone(common.CELL_WHITE)
    }

    public fun getBlackStoneNum(): Int {
        return tm.countStone(common.CELL_BLACK)
    }

    public fun getNextTurn(): Int {
        return currentturn
    }

     // 盤用配列を初期化(初期盤面)
    fun initBoard() {
        tm.initialize()
        tm.initialPlacement()
    }

    // 石が置かれた後の盤面を作成する
    fun makeBoard(): Boolean
    {
        //すでに石が起これていいた場合は置けない
        if (tm.board[x][y] ==common.CELL_WHITE||tm.board[x][y] ==common.CELL_BLACK){
            return false
        }

        // 石をおいておかれた後の盤面を作成する
        if(!reverseStone())
        {
            return false
        }

        // 相手が石が置けるかチェック

        // 石が置かれた場合手番を入れ替える
        if(currentturn == common.CELL_BLACK)
        {
            currentturn = common.CELL_WHITE
        }

        // ゲーム終了かチェック


        return true
    }

    // 次に相手が石が置ける場所があるか判定する
    fun Isskip(color: Int):Boolean
    {
        for (i in 0..common.BOARD_SIZE-1) {
            for (k in 0..common.BOARD_SIZE-1) {
               if( isPut(i,k,color))
               {
                   return true
               }
            }
        }
        return false
    }

    fun isPut(x:Int ,y:Int ,color:Int): Boolean
    {
        var count: Int = 1

        var Oppstone: Int = 0 // 現在の手番
        var Turnstone: Int = 0 // 相手の手番
        var countFlag: Boolean = true // 続けて架空人するかのフラグ

        if (color == common.CELL_BLACK){
            Turnstone = common.CELL_BLACK
            Oppstone = common.CELL_WHITE
        }
        else
        {
            Turnstone = common.CELL_WHITE
            Oppstone = common.CELL_BLACK
        }

        count= 1
        
        //左に左方向にひっくり返すものがあれるか確認
        while(countFlag) {
            if (x - count >= 0) {
                when (tm.board[x - count][y]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //左上判定
        count = 1
        while(countFlag) {
            if(x-count >= 0 && y-count >= 0) {
                when (tm.board[x-count][y-count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }


        //上判定
        count= 1
        while(countFlag) {
            if(y-count >= 0) {
                when (tm.board[x][y-count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //右上判定
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE &&y-count >= 0) {
                when (tm.board[x+count][y-count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //右判定
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE) {
                when (tm.board[x+count][y]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //右下判定
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE && y+count < common.BOARD_SIZE) {
                when (tm.board[x+count][y+count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //下判定
        count= 1
        while(countFlag) {
            if(y+count < common.BOARD_SIZE) {
                when (tm.board[x][y+count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }

        //左下判定
        count= 1
        while(countFlag) {
            if(x-count >= 0) {
                when (tm.board[x-count][y+count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    fun reverseStone(): Boolean
    {
        var count: Int = 0

        var Oppstone: Int = 0 // 現在の手番
        var Turnstone: Int = 0 // 相手の手番
        var countFlag: Boolean = true // 続けて架空人するかのフラグ

        if (currentturn == common.CELL_BLACK){
            Turnstone = common.CELL_BLACK
            Oppstone = common.CELL_WHITE
        }
        else
        {
            Turnstone = common.CELL_WHITE
            Oppstone = common.CELL_BLACK
        }

        count= 1
        //左に左方向にひっくり返すものがあれるか確認
        while(countFlag) {
            if (x - count >= 0) {
                when (tm.board[x - count][y]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x-i][y] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        count= 1
        //上方向にひっくり返すものがあればひっくり返す
        while(countFlag) {
            if (y - count >= 0) {
                when (tm.board[count][y -count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x][y-i] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        //右上にひっくり返すものがあればひっくり返す
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE &&y-count >= 0) {
                when (tm.board[x+count][y-count]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x+i][y-i] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        //右判定
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE) {
                when (tm.board[x+1][y]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x+i][y] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        //右下判定
        count= 1
        while(countFlag) {
            if(x+count < common.BOARD_SIZE && y+count < common.BOARD_SIZE) {
                when (tm.board[x+1][y+1]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x+i][y+i] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        //下判定
        count= 1
        while(countFlag) {
            if(y+count < common.BOARD_SIZE) {
                when (tm.board[x][y+1]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x+i][y-i] = Turnstone
                            }
                        }
                    }
                }
            }
        }

        //左下判定
        count= 1
        while(countFlag) {
            if(x-count >= 0 &&y+count < common.BOARD_SIZE) {
                when (tm.board[x-1][y+1]) {
                    common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.board[x-i][y+i] = Turnstone
                            }
                        }
                    }
                }
            }
        }
        return true
    }
}
