import androidx.appcompat.app.AppCompatActivity

class TableMan {
    // 定数
    companion object {
        const val Empty:Int = 0
        const val Black:Int = 1
        const val White:Int = 2
    }

    var cellXnum:Int = 8  // X方向のマス数
    var cellYnum:Int = 8  // Y方向のマス数
    var nextTurn:Int = Black

    // 盤面
    var board = Array(cellXnum) { IntArray(cellYnum){Empty} }

    public fun Initialize( cellXnum:Int = 8, cellYnum:Int = 8 ){
        // 盤面のサイズ決定
        this.cellXnum = cellXnum
        this.cellYnum = cellYnum

        // 盤面の初期化
        this.board = Array(cellXnum) {IntArray(cellYnum){Empty}}

        // 初期配置
        PutStone(3, 3, Black)
        PutStone(3, 4, White)
        PutStone(4, 3, White)
        PutStone(4, 4, Black)
    }

    //public fun SetBoard(board:Array){
    //    this.board = board
    //}

    //public fun GetBoard():Array{
    //    return board
    //}

    // 次の手番を設定
    public fun SetNextTurn( nextTurn:Int ){
        this.nextTurn = nextTurn
    }

    // 次の手番を取得
    public fun GetNextTurn():Int{
        return this.nextTurn
    }

    // オプション機能：任意の場所に石を置く
    public fun PutStone( cellX:Int, cellY:Int, color:Int ){
        this.board[cellX][cellY] = color
    }

    // Debug用途
    public fun ExportBoard(){
        for (i in 0..7) {
            for (j in 0..7) {
                print("${board[i][j]} ")
            }
            print("\n")
        }
    }
}
