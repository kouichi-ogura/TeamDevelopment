import androidx.appcompat.app.AppCompatActivity

class TableMan {
    var cellXnum:Int = 8  // X方向のマスの数
    var cellYnum:Int = 8  // Y方向のマスの数

    // 盤面
    val board = Array(8) { IntArray(8) }
    //var board: Array<Int?> = arrayOfNulls(8)

    public fun Initialize(cellXnum:Int = 8, cellYnum:Int = 8 ){
        // 盤面のサイズ決定
        this.cellXnum = cellXnum
        this.cellYnum = cellYnum
        //this.board = Array(8) { IntArray(8) }

        // 盤面の初期化
        //Array(8, {index ->0})
        for (i in 0..7) {
            for (j in 0..7) {
                board[i][j] = 0
            }
        }

        // 初期配置
        board[3][3] = 1
        board[3][4] = 2
        board[4][3] = 2
        board[4][4] = 1
    }

    //    public fun GetBoard():Array{
//        return board
//    }
//
//    public fun SetBoard(board:Array){
//        this.board = board
//    }
//
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
