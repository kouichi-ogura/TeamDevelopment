package com.example.othello

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.MotionEvent
//import TableMan

class GameMan {

    companion object {
        const val Empty = 0
        const val Black = 1
        const val White = 2
        const val boardSize = 8
    }

//   val boardArray = Array(boardSize) { IntArray(boardSize) }// 盤用配列

    var currentturn: Int = Black // 手番保持用変数

    var x: Int = 1 // 取得したx座標
    var y: Int = 1 // 取得したy座標

    private var cellXnum:Int = 8    // X方向のマス数
    private var cellYnum:Int = 8    // Y方向のマス数

    private var tm = TableMan()

    init {
        tm.Initialize()
    }

    public fun putStone(x:Int, y:Int): Boolean {
        //TODO:置けるかチェック
        //IsPut()
        //  return false

        // 置けるならテーブル更新
        tm.PutStone(x, y, currentturn)

        // TODO:次の手番判定
        // 下記は暫定処理
        if  (currentturn == Black){
            currentturn == White
        }else{
            currentturn == Black
        }

        return true
    }

    public  fun getTable(): Array<IntArray> {
        return tm.board
    }

    public fun getWhiteStoneNum(): Int {
        return 10
    }

    public fun getBlackStoneNum(): Int {
        return 20
    }


    // 盤用配列を初期化(初期のまっさらな盤面)
    //fun clearBoard() {
    //    tm.Initialize()
    //}

    // 盤用配列を初期化(初期盤面)
    fun initBoard() {
        tm.Initialize()
        tm.InitialPlacement()
    }

    // 石が置かれた後の盤面を作成する
    fun makeBoard(): Boolean
    {
        //すでに石が起これていいた場合は置けない
        if (tm.board[x][y] ==White||tm.board[x][y] ==Black){
            return false
        }

        // 石をおいておかれた後の盤面を作成する
        if(!reverseStone())
        {
            return false
        }

        // 相手が石が置けるかチェック

        // 石が置かれた場合手番を入れ替える
        if(currentturn == Black)
        {
            currentturn = White
        }

        // ゲーム終了かチェック


        return true
    }

    fun reverseStone(): Boolean
    {
        var count: Int = 0

        var Oppstone: Int = 0 // 現在の手番
        var Turnstone: Int = 0 // 相手の手番

        if (currentturn == Black){
            Turnstone = Black
            Oppstone = White
        }
        else
        {
            Turnstone = White
            Oppstone = Black
        }
        //左に左方向にひっくり返すものがあればひっくり返す
        do{
            if(x-count >= 0) {
                when (tm.board[x-count][y]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //右に石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x-count){
                                tm.board[x-i][y] = Turnstone
                            }

                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //左上方向にひっくり返すものがあればひっくり返す
        do{
            if(x-count >= 0 &&y-count >= 0) {
                when (tm.board[x-1][y-1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //相手自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x-count){
                                tm.board[x-i][y-i] = Turnstone
                            }

                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //上にひっくり返すものがあればひっくり返す
        do{
            if(y-count >= 0) {
                when (tm.board[x][y-1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x-count){
                                tm.board[x][y-i] = Turnstone
                            }
                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //右上にひっくり返すものがあればひっくり返す
        do{
            if(x+count < boardSize-1 &&y-count >= 0) {
                when (tm.board[x+1][y-1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x-count){
                                tm.board[x+i][y-i] = Turnstone
                            }
                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)


        //右にひっくり返すものがあればひっくり返す
        do{
            if(x+count < boardSize-1) {
                when (tm.board[x+1][y]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x-count){
                                tm.board[x+i][y] = Turnstone
                            }
                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //右下にひっくり返すものがあればひっくり返す
        do{
            if(x+count < boardSize-1 && y+count < boardSize-1) {
                when (tm.board[x+1][y+1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x+count){
                                tm.board[x+1][y+1] = Turnstone
                            }

                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //下にひっくり返すものがあればひっくり返す
        do{
            if(y+count < boardSize-1) {
                when (tm.board[x][y+1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x+count){
                                tm.board[x][y+i] = Turnstone
                            }

                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        //左下にひっくり返すものがあればひっくり返す
        do{
            if(x-count >= 0) {
                when (tm.board[x-1][y+1]) {
                    Empty -> //空白マスの場合
                    {
                        count =boardSize; //空白の場合は終了
                    }
                    Oppstone -> // 相手の石の場合
                    {
                        count++
                    }
                    Turnstone -> //　自分の石の場合
                    {
                        if (count == 0)
                        {
                            count =boardSize; //隣に自分の石がある場合強制終了
                        }
                        else
                        {
                            for (i in x..x+count){
                                tm.board[x-i][y+i] = Turnstone
                            }

                        }
                    }
                }
            }
            else
            {
                break
            }

        }while(count<boardSize)

        return true
    }

}