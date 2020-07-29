package com.example.othello

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //GameManager
    private var gameManager : GameMan = GameMan()

    //盤面サイズ
    private val squareNum : Int = Common.BOARD_SIZE

    //マスの状態 [CELL_ENPTY:空き、CELL_BLACK:黒、CELL_WHITE:白]
    var territory = Array(squareNum) {IntArray(squareNum)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初期化
        gameManager.initBoard()
        territory = gameManager.getTable()
        drawScore(gameManager.getWhiteStoneNum(), gameManager.getBlackStoneNum())

        //手番描画
        drawTurn(gameManager.getNextTurn())

        var myView = MyView(this)

        //「リセット」ボタン押下
        findViewById<Button>(R.id.resetButton).setOnClickListener{
            //初期化
            gameManager.initBoard()
            territory = gameManager.getTable()
            drawScore(gameManager.getWhiteStoneNum(), gameManager.getBlackStoneNum())

            myView.invalidate()

            //手番描画
            drawTurn(gameManager.getNextTurn())
        }

        //「終了」ボタン押下
        findViewById<Button>(R.id.endButton).setOnClickListener{
            finishAndRemoveTask()
        }

        findViewById<LinearLayout>(R.id.boardLayout).addView(myView)
    }

    // Viewを継承したクラス
    internal inner class MyView(context: Context) : View(context) {

        private val dm = DisplayMetrics()

        init {
            // 画面下のボタンエリアを含めない画面サイズを取得
            windowManager.defaultDisplay.getRealMetrics(dm)
        }

        // 画面サイズ
        // 幅高どちらも求めるが、幅のほうが短いという前提のもと、以降は幅しか使用しない
        private val displayWidth    = dm.widthPixels
        private val displayHeight   = dm.heightPixels

        private var paint: Paint = Paint()

        // 描画するラインの開始/終了位置
        private val startXPos       = 10f
        private val startYPos       = 10f
        private val endXPos         = displayWidth - startXPos
        private val endYPos         = displayWidth - startYPos

        // タップされたセルのX/Y位置
        var cellX : Int     = 0
        var cellY : Int     = 0

        override fun onDraw(canvas: Canvas){

            // 盤面の色設定
            paint.color = Color.argb(255, 70, 130, 40)

            // 盤面のStyle(=塗りつぶし)設定
            paint.style = Paint.Style.FILL

            // 盤面の描画
            canvas.drawRect(startXPos, startYPos, endXPos, endYPos, paint)

            // 盤面上の線色設定
            paint.color = Color.argb(255, 0, 0, 0)

            // squareNum * squareNumの縦横線を描画
            val twoPoint: FloatArray = floatArrayOf(startXPos, startYPos, endXPos, endYPos)
            drawBorderLine(twoPoint, squareNum, canvas, paint)

            //コマ描画
            for(y in 0 until squareNum){
                for(x in 0 until squareNum){
//                    drawPiece(canvas, territory[y][x], y, x)
                    drawPiece(canvas, territory[x][y], y, x)
                }
            }
        }

        override fun onTouchEvent(event: MotionEvent) :Boolean {
            var touchX : Int    = 0
            var touchY : Int    = 0

            // 画面タップ位置を取得
            when (event.getAction()) {
                MotionEvent.ACTION_DOWN -> {
                    touchX = event.getX().toInt()
                    touchY = event.getY().toInt()
                }
            }

            // タップ位置が盤面内ならタップ位置を座標に変換して保持
            if ((touchX >= startXPos.toInt() && touchX <= endXPos.toInt()) &&
                (touchY >= startYPos.toInt() && touchY <= endYPos.toInt())) {
                val twoPoint: FloatArray = floatArrayOf(startXPos, startYPos, endXPos, endYPos)
                changePosToCoodinate(twoPoint, squareNum, touchX, touchY)
            }

            //タップしたセルにコマを置く,
            if(gameManager.putStone(cellX, cellY)){
                territory = gameManager.getTable()
                drawScore(gameManager.getWhiteStoneNum(), gameManager.getBlackStoneNum())
                //手番描画
                drawTurn(gameManager.getNextTurn())
            }
            invalidate()

            return super.onTouchEvent(event)
        }

        //コマ描画
        // State   : [CELL_EMPTY, CELL_BLACK, CELL_WHITE],
        // CellY/X : マス位置, 左上[0,0]～右下[7,7],
        fun drawPiece(canvas: Canvas, state : Int, cellY : Int, cellX : Int){
            var paintBlackPiece: Paint = Paint()
            var paintWhitePiece: Paint = Paint()

            //色設定
            paintBlackPiece.color = Color.argb(255, 0, 0, 0)
            paintWhitePiece.color = Color.argb(255, 255, 255, 255)

            //太さ
            paintBlackPiece.strokeWidth = 10f
            paintWhitePiece.strokeWidth = 10f

            //ストローク（枠線と塗りつぶし設定）
            paintBlackPiece.style = Paint.Style.FILL
            paintWhitePiece.style = Paint.Style.FILL

            //滑らか設定
            paintBlackPiece.isAntiAlias = true
            paintWhitePiece.isAntiAlias = true


            //盤面全体のサイズ,
            val boardSize : Float = endXPos - startXPos
            //1マスのサイズ,
            var areaSize : Float = boardSize/squareNum
            //端からの offset,
            val offset : Float = startXPos
            //コマの半径,
            var r : Float = (areaSize/2)*0.7f

            when(state) {
                //空マス
                Common.CELL_EMPTY -> {}
                //黒コマ
                Common.CELL_BLACK -> canvas.drawCircle(offset + cellX*areaSize + areaSize/2,
                                        offset + cellY*areaSize + areaSize/2, r, paintBlackPiece)
                //白コマ
                Common.CELL_WHITE -> canvas.drawCircle(offset + cellX*areaSize + areaSize/2,
                                        offset + cellY*areaSize + areaSize/2, r, paintWhitePiece)
                //
                else -> {}
            }
        }

        // 2点のXY座標を受け取り、線を描画
        fun drawBorderLine(twoPoint: FloatArray, squareNum: Int, canvas: Canvas, paint: Paint) {
            paint.strokeWidth = 2f

            // 横線を描画
            for (i in 0..squareNum) {
                canvas.drawLine(twoPoint[0], twoPoint[1] + ((twoPoint[2] - twoPoint[0]) / squareNum) * i, twoPoint[2], twoPoint[0] + ((twoPoint[2] - twoPoint[0]) / squareNum) * i, paint)
            }
            // 縦線を描画
            for (i in 0..squareNum) {
                canvas.drawLine(twoPoint[0] + ((twoPoint[2] - twoPoint[0]) / squareNum) * i, twoPoint[1], twoPoint[0] + ((twoPoint[2] - twoPoint[0]) / squareNum) * i, twoPoint[2], paint)
            }
        }

        // タップ位置を受け取り、座標に変換
        fun changePosToCoodinate(twoPoint: FloatArray, squareNum: Int, touchX: Int, touchY: Int) {
            // 引数のタップ位置からマージンを引き、マス幅で割ることで座標(XYともに0始まり)を計算
            // 例1 タップ位置が70の場合、 (70 - 10) / 80 = 0となり、X座標は0
            // 例2 タップ位置が115の場合、(115 - 10) / 80 = 1となり、X座標は1
            // 例3 タップ位置が170の場合、(170 - 10) / 80 = 2となり、X座標は2
            //     つまり、盤面内のちょうど線上をタップした場合、Xは線の右マス、Yは線の下マスとする
            cellX = ((touchX - twoPoint[0]) / ((twoPoint[2] - twoPoint[0]) / squareNum)).toInt()
            cellY = ((touchY - twoPoint[1]) / ((twoPoint[2] - twoPoint[0]) / squareNum)).toInt()
        }
    }

    //スコア描画
    fun drawScore(whiteScore : Int, blackScore:Int){
        findViewById<TextView>(R.id.whiteScoreView).text = "○：${whiteScore}個"
        findViewById<TextView>(R.id.blackScoreView).text = "●：${blackScore}個"
    }

    //メッセージ描画
    fun drawMsg(msg : String){
        findViewById<TextView>(R.id.msgView).text = msg
    }

    //手番描画
    fun drawTurn(turn : Int){
        if(turn == Common.CELL_BLACK)
            drawMsg(" 黒の番です")
        else
            drawMsg(" 白の番です")
    }

    //タッチイベント
    override fun onTouchEvent(event: MotionEvent) :Boolean {

        when(event.action){
            /*
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            */
        }
        return super.onTouchEvent(event)
    }

}