package connectfour

fun main() {
    println("Connect Four")
    println("First player's name:")
    val p1 = readln()
    println("Second player's name:")
    val p2 = readln()
    val row: Int
    val col: Int
    val regex = Regex("[xX]")

    while (true) {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val dim = readln().replace("\\s".toRegex(), "")
            .split(regex).toMutableList()


        if (dim.size == 1 || dim[1] == "" || dim[0] == "" ||
            !(dim[0].all { Character.isDigit(it) }) ||
            !(dim[1].all { Character.isDigit(it) })
        ) {
            if (dim[0] == "" && dim.size == 1) {
                row = 6
                col = 7
                break
            }
            println("Invalid input")
            continue
        } else {
            if (dim[0].toInt() > 9 || dim[0].toInt() < 5) {
                println("Board rows should be from 5 to 9")
            }
            if (dim[1].toInt() > 9 || dim[1].toInt() < 5) {
                println("Board columns should be from 5 to 9")
            }

            if (dim[0].toInt() in 5..9 &&
                dim[1].toInt() in 5..9
            ) {
                row = dim[0].toInt()
                col = dim[1].toInt()
                break
            }
        }
    }
    var gmsIn: String
    val gms: Int
    while (true) {
        println(
            "Do you want to play single or multiple games?\n" +
                    "For a single game, input 1 or press Enter\n" +
                    "Input a number of games:"
        )
        gmsIn = readln()
        if (gmsIn == "") {
            gms = 1
            break
        } else if (!gmsIn.all { Character.isDigit(it) }) {
            println("Invalid input")
        } else if (gmsIn.toInt() < 1) {
            println("Invalid input")
        } else {
            gms = gmsIn.toInt()
            break
        }
    }

    println("$p1 VS $p2")
    println("$row X $col board")

//    var remain = MutableList(col) { row }
    val board = MutableList(row) { MutableList(col) { " " } }

    var scr1 = 0
    var scr2 = 0

    if (gms == 1) {
        println("Single game")
        drawBoard(row, col, board)
        turns(p1, p2, row, col, true)
        println("Game Over!")
    } else {
        println("Total $gms games")
        for (i in 1..gms) {
            println("Game #$i")
            drawBoard(row, col, board)
            if (i % 2 == 1) {
                when (turns(p1, p2, row, col, true)) {
                    "p1 win" -> scr1 += 2
                    "p2 win" -> scr2 += 2
                    "draw" -> {
                        scr1++
                        scr2++
                    }
                }
            } else {
                when (turns(p1, p2, row, col, false)) {
                    "p1 win" -> scr1 += 2
                    "p2 win" -> scr2 += 2
                    "draw" -> {
                        scr1++
                        scr2++
                    }
                }
            }

            println("Score\n" + "$p1: $scr1 $p2: $scr2")
            if (i == gms) println("Game over!")
        }
    }
}

fun turns(
    pl1: String, pl2: String, r: Int, c: Int, tur: Boolean
): String {
    var pl = pl1
    var inn: String
    var h = 0
    var p1w = 0
    var p2w = 0
    var score = ""
    var disc = ""
    var board = MutableList(r) { MutableList(c) { " " } }
    var turn1 = tur
    while (true) {

        pl = if (turn1) {
            pl1
        } else {
            pl2
        }

        disc = if (turn1) "o" else "*"

        h = 0
        for (l in 0 until c) {
            if (board[0][l] != " ") {
                h++
            }
        }

        if (h == c) {
            println("It is a draw")
            score += "draw"
            return score
        }

        p1w = 0
        p2w = 0

        win1@ for (x in 0 until r) {
            for (y in 0 until c) {
                if (y < board[0].size - 3 && board[x][y] == "o") {
                    p1w++
                    if (board[x][y + 1] == "o") {
                        p1w++
                        if (board[x][y + 2] == "o") {
                            p1w++
                            if (board[x][y + 3] == "o") {
                                p1w++
//                                print("R")
                                break@win1
                            }
                        }
                    }
                    p1w = 1
                    if (x < board.size - 3 && board[x + 1][y + 1] == "o") {
                        p1w++
                        if (board[x + 2][y + 2] == "o") {
                            p1w++
                            if (board[x + 3][y + 3] == "o") {
                                p1w++
//                                print("RD")
                                break@win1
                            }
                        }
                    }
                }
                p1w = 0
                if (x < board.size - 3 && board[x][y] == "o") {
                    p1w++
                    if (board[x + 1][y] == "o") {
                        p1w++
                        if (board[x + 2][y] == "o") {
                            p1w++
                            if (board[x + 3][y] == "o") {
                                p1w++
//                                print("D")
                                break@win1
                            }
                        }
                    }
                    p1w = 1
                    if (y > 2 && board[x + 1][y - 1] == "o") {
                        p1w++
                        if (board[x + 2][y - 2] == "o") {
                            p1w++
                            if (board[x + 3][y - 3] == "o") {
                                p1w++
//                                print("LD")
                                break@win1
                            }
                        }
                    }
                }
            }
        }

        win2@ for (x in 0 until r) {
            for (y in 0 until c) {
                if (y < board[0].size - 3 && board[x][y] == "*") {
                    p2w++
                    if (board[x][y + 1] == "*") {
                        p2w++
                        if (board[x][y + 2] == "*") {
                            p2w++
                            if (board[x][y + 3] == "*") {
                                p2w++
                                break@win2
                            }
                        }
                    }
                    p2w = 1
                    if (x < board.size - 3 && board[x + 1][y + 1] == "*") {
                        p2w++
                        if (board[x + 2][y + 2] == "*") {
                            p2w++
                            if (board[x + 3][y + 3] == "*") {
                                p2w++
                                break@win2
                            }
                        }
                    }
                }
                p2w = 0
                if (x < board.size - 3 && board[x][y] == "*") {
                    p2w++
                    if (board[x + 1][y] == "*") {
                        p2w++
                        if (board[x + 2][y] == "*") {
                            p2w++
                            if (board[x + 3][y] == "*") {
                                p2w++
                                break@win2
                            }
                        }
                    }
                    p2w = 1
                    if (y > 2 && board[x + 1][y - 1] == "*") {
                        p2w++
                        if (board[x + 2][y - 2] == "*") {
                            p2w++
                            if (board[x + 3][y - 3] == "*") {
                                p2w++
                                break@win2
                            }
                        }
                    }
                }
            }
        }

//        println(p1w)
//        println(p2w)

        if (p1w == 4) {
            println("Player $pl1 won")
            score += "p1 win"
            return score
        }

        if (p2w == 4) {
            println("Player $pl2 won")
            score += "p2 win"
            return score
        }

        println("$pl's turn:")
        inn = readln()
        if (inn == "end") {
            println("Game over!")
            break
        } else if (inn == "" || !inn.all { Character.isDigit(it) }) {
            println("Incorrect column number")
            continue
        } else {
            if (inn.toInt() > c || inn.toInt() < 1) {
                println("The column number is out of range (1 - $c)")
                continue
            } else if (inn.toInt() in 1..c) {
                if (board[0][inn.toInt() - 1] == " ") {
                    for (i in r downTo 1) {
                        if (board[i - 1][inn.toInt() - 1] == " ") {
                            if (turn1) {
                                board[i - 1][inn.toInt() - 1] = "o"
                            } else {
                                board[i - 1][inn.toInt() - 1] = "*"
                            }
                            break
                        }
                    }
                    drawBoard(r, c, board)
                    turn1 = !turn1
                } else {
                    println("Column ${inn.toInt()} is full")
                    continue
                }
            }
        }
    }
    return score
}

// rem :MutableList<Int>,
fun drawBoard(
    rows: Int, cols: Int, board: MutableList<MutableList<String>>
) {
//    if(rem[inp] != 0) {
//    println(board)
    for (r in 0..rows + 1) {
        for (c in 1..cols + 1) {
            if (r == 0) {
                if (c == cols + 1) {
                    println()
                } else {
                    print(" $c")
                }
            } else if (r == rows + 1) {
                when (c) {
                    1 -> print("╚═")
                    cols + 1 -> println("╝")
                    else -> print("╩═")
                }
            } else if (c == cols + 1) {
                println("║")
            } else {
                print("║")
                print(board[r - 1][c - 1])
            }
        }
    }
}

//fun check(inp: Int): Boolean{
//    if ()
//}
