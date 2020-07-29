package com.example.othello

class Common {
    companion object {
        const val CELL_EMPTY            = 0    // セル空白
        const val CELL_BLACK            = 1    // セル黒
        const val CELL_WHITE            = 2    // セル白
        const val BOARD_SIZE            = 8    // 盤面サイズ
        const val PUT_OK_END            = 0    // 石が置けてゲーム終了
        const val PUT_OK_CONTINUE       = 1    // 石が置けてゲーム継続
        const val PUT_NG                = 2    // 石が置けない
    }
}