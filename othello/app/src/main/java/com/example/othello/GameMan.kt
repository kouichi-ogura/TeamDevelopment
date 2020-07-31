package com.example.othello

class GameMan {
    var currentturn: Int = Common.CELL_BLACK // 手番保持用変数
    var x: Int = 1 // 取得したx座標
    var y: Int = 1 // 取得したy座標

    private var tm = TableMan()

    init {
        tm.initialize()
    }

    // 盤用配列を初期化(初期盤面)
    fun initBoard() {
        tm.initialize()
        tm.initialPlacement()
        currentturn = Common.CELL_BLACK
    }

    // UIからの呼び出しで石を置く
    fun putStone(x: Int, y: Int): Int {

        //置けるかチェック
        if (!isPut(x, y, currentturn)) {
            return Common.PUT_NG
        }

        // 置いた石を基準に挟める方向はひっくり返す
        reverseStone(x, y, currentturn)

        // 次の手番判定
        if (!setNextTurn())
        {
            // どちらも置けなかったらゲーム終了
            return Common.PUT_OK_END
        }
        return Common.PUT_OK_CONTINUE
    }

    // 盤面を返す
    fun getTable(): Array<IntArray> {
        return tm.board
    }

    // 白色石の数を返す
    fun getWhiteStoneNum(): Int {
        return tm.countStone(Common.CELL_WHITE)
    }

    // 黒色石の数を返す
    fun getBlackStoneNum(): Int {
        return tm.countStone(Common.CELL_BLACK)
    }

    // 次手番を返す
    fun getNextTurn(): Int {
        return currentturn
    }

    // すでに置いてあるかチェック
    private fun isAlreadyPut(x: Int, y: Int): Boolean {
        return tm.board[x][y] != Common.CELL_EMPTY
    }

    // 手番交代
    private fun swapTurn()
    {
        // 石が置かれた場合手番を入れ替える
        if (currentturn == Common.CELL_BLACK) {
            currentturn = Common.CELL_WHITE
        }else {
            currentturn = Common.CELL_BLACK
        }
    }

    // 次の手番を設定
    private fun setNextTurn():Boolean{
        // 相手が置けるかチェック
        swapTurn()
        if ( ! IsAnyPut(currentturn)){
            // 自分が置けるかチェック
            swapTurn()
            if ( ! IsAnyPut(currentturn)) {
                // どちらも置けない
                return false
            }
        }
        return true
    }

    // どこか石を置く場所があるかチェック
    private fun IsAnyPut(color: Int): Boolean {
        for (i in 0..Common.BOARD_SIZE - 1) {
            for (k in 0..Common.BOARD_SIZE - 1) {
                if (isPut(i, k, color)) {
                    return true
                }
            }
        }
        return false
    }

    // 指定座標に石が置けるかチェック
    private fun isPut(x: Int, y: Int, color: Int): Boolean {
        // すでに石が置かれているかチェック
        if (isAlreadyPut(x, y)) {
            return false
        }

        //挟むことができるかチェック
        var count: Int = 1
        var Oppstone: Int = 0 // 現在の手番
        var Turnstone: Int = 0 // 相手の手番
        var countFlag: Boolean = true // 続けて架空人するかのフラグ

        if (color == Common.CELL_BLACK) {
            Turnstone = Common.CELL_BLACK
            Oppstone = Common.CELL_WHITE
        } else {
            Turnstone = Common.CELL_WHITE
            Oppstone = Common.CELL_BLACK
        }

        count = 1
        countFlag = true
        //左に左方向にひっくり返すものがあれるか確認
        while (countFlag) {
            if (x - count >= 0) {
                when (tm.board[x - count][y]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //左上判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (x - count >= 0 && y - count >= 0) {
                when (tm.board[x - count][y - count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }


        //上判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (y - count >= 0) {
                when (tm.board[x][y - count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //右上判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (x + count < Common.BOARD_SIZE && y - count >= 0) {
                when (tm.board[x + count][y - count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //右判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (x + count < Common.BOARD_SIZE) {
                when (tm.board[x + count][y]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //右下判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (x + count < Common.BOARD_SIZE && y + count < Common.BOARD_SIZE) {
                when (tm.board[x + count][y + count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //下判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (y + count < Common.BOARD_SIZE) {
                when (tm.board[x][y + count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }

        //左下判定
        count = 1
        countFlag = true
        while (countFlag) {
            if (x-count >= 0 && y+count < Common.BOARD_SIZE) {
                when (tm.board[x - count][y + count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else {
                            return true
                        }
                    }
                }
            } else {
                countFlag = false; //空白の場合は終了
            }
        }
        return false
    }

    private fun reverseStone(x: Int, y: Int, color: Int): Boolean {
        var count: Int = 0

        var Oppstone: Int = 0 // 現在の手番
        var Turnstone: Int = 0 // 相手の手番
        var countFlag: Boolean = true // 続けて架空人するかのフラグ

        if (color == Common.CELL_BLACK){
            Turnstone = Common.CELL_BLACK
            Oppstone = Common.CELL_WHITE
        }
        else
        {
            Turnstone = Common.CELL_WHITE
            Oppstone = Common.CELL_BLACK
        }

        count= 1
        countFlag = true
        //左に左方向にひっくり返すものがあれるか確認
        while(countFlag) {
            if (x - count >= 0) {
                when (tm.board[x - count][y]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x-i, y, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }

        }

        count= 1
        countFlag = true
        //左上にひっくり返すものがあれるか確認
        while(countFlag) {
            if ( x- count >= 0 && y - count >= 0) {
                when (tm.board[x - count][y-count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x-i, y-i, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }

        }

        count= 1
        countFlag = true
        //上方向にひっくり返すものがあればひっくり返す
        while(countFlag) {
            if (y - count >= 0) {
                when (tm.board[x][y -count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x, y-i,  Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }

        //右上にひっくり返すものがあればひっくり返す
        count= 1
        countFlag = true
        while(countFlag) {
            if(x+count < Common.BOARD_SIZE &&y-count >= 0) {
                when (tm.board[x+count][y-count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x+i, y-i, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }

        //右判定
        count= 1
        countFlag = true
        while(countFlag) {
            if(x+count < Common.BOARD_SIZE) {
                when (tm.board[x+count][y]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x+i, y, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }

        //右下判定
        count= 1
        countFlag = true
        while(countFlag) {
            if(x+count < Common.BOARD_SIZE && y+count < Common.BOARD_SIZE) {
                when (tm.board[x+count][y+count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x+i, y+i, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }

        //下判定
        count= 1
        countFlag = true
        while(countFlag) {
            if(y+count < Common.BOARD_SIZE) {
                when (tm.board[x][y+count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x, y+i, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }

        //左下判定
        count= 1
        countFlag = true
        while(countFlag) {
            if(x-count >= 0 &&y+count < Common.BOARD_SIZE) {
                when (tm.board[x-count][y+count]) {
                    Common.CELL_EMPTY -> //空白マスの場合
                    {
                        countFlag = false; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 1) {
                            countFlag = false; //隣に自分の石がある場合がある場合強制終了
                        } else
                        {
                            for (i in 0..count){
                                tm.putStone(x-i, y+i, Turnstone)
                                countFlag = false // 裏返したらチェックを抜ける
                            }
                        }
                    }
                }
            }
            else
            {
                countFlag = false; //空白の場合は終了
            }
        }
        return true
    }
}
