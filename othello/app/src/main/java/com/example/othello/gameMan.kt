package com.example.myapplication
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


class gameMan {

    companion object {
        const val Empty = 0
        const val Black = 1
        const val White = 2
        const val boardSize = 8
    }

   val boardArray = Array(boardSize) { IntArray(boardSize) }// 盤用配列

    var currentturn: Int = Black // 手番保持用変数

    var x: Int = 1 // 取得したx座標
    var y: Int = 1 // 取得したy座標



    // 盤用配列を初期化(初期のまっさらな盤面)
    fun clearBord() {
        for (i in 0..7) {
            for (k in 0..7) {
                boardArray[i][k] = 0
            }
        }
    }

    // 盤用配列を初期化(初期盤面)
    fun initBord() {
        for (i in 0..7) {
            for (k in 0..7) {
                boardArray[i][k] = 0
            }
        }
        boardArray[3][3] = White
        boardArray[4][4] = White
        boardArray[4][3] = Black
        boardArray[3][4] = Black
    }

    // 石が置かれた後の盤面を作成する
    fun makeBord(): Boolean
    {
        //すでに石が起これていいた場合は置けない
        if (boardArray[x][y] ==White||boardArray[x][y] ==Black){
            return false
        }

        // 石をおいておかれた後の盤面を作成する
        if(!reverseStone())
        {
            return false
        }

        // 石が置かれた場合手番を入れ替える
        if(currentturn == Black)
        {
            currentturn = White
        }

        return true
    }

    fun reverseStone(): Boolean
    {
        var count: Int = 0

        var Oppstone: Int = 0 // 現在の手番の石
        var Turnstone: Int = 0 // 相手の手番の石

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
                when (boardArray[x-1][y]) {
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
                                boardArray[x-i][y] = Turnstone
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
                when (boardArray[x-1][y-1]) {
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
                                boardArray[x-i][y-i] = Turnstone
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
                when (boardArray[x][y-1]) {
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
                                boardArray[x][y-i] = Turnstone
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
                when (boardArray[x+1][y-1]) {
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
                                boardArray[x+i][y-i] = Turnstone
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
                when (boardArray[x+1][y]) {
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
                                boardArray[x+i][y] = Turnstone
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
                when (boardArray[x+1][y+1]) {
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
                                boardArray[x+1][y+1] = Turnstone
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
                when (boardArray[x][y+1]) {
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
                                boardArray[x][y+i] = Turnstone
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
                when (boardArray[x-1][y+1]) {
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
                                boardArray[x-i][y+i] = Turnstone
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