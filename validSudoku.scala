@main def m() = 
    println("Hello world")
    val board0 = Vector(Vector('5', '3', '.'), Vector('6', '.', '.'), Vector('.', '9', '8'))
    println(s"Basic case. Expected: true, Actual: ${validSudoku(board0)}")
    val board1 = Vector(Vector('5', '3', '.'), Vector('6', '.', '6'), Vector('.', '9', '8'))
    println(s"Check rows for repeats. Expected: false, Actual: ${validSudoku(board1)}")
    val board2 = Vector(Vector('5', '3', '.'), Vector('.', '2', '.'), Vector('.', '2', '4'))
    println(s"Check cols for repeats. Expected: false, Actual: ${validSudoku(board2)}")
    val board3 = Vector(
        Vector('8', '3', '.', '.', '7', '.'), 
        Vector('6', '.', '.', '1', '9', '5'), 
        Vector('.', '9', '8', '.', '.', '.'),
        Vector('1', '.', '.', '.', '6', '.'),
        Vector('4', '.', '.', '8', '.', '3'),
        Vector('7', '.', '.', '.', '2', '.')
    )
    println(s"Check subBoxes for repeats. Expected: false, Actual: ${validSudoku(board3)}")
    val board4 = Vector(
         Vector('5','3','.','.','7','.','.','.','.')
        ,Vector('6','.','.','1','9','5','.','.','.')
        ,Vector('.','9','8','.','.','.','.','6','.')
        ,Vector('8','.','.','.','6','.','.','.','3')
        ,Vector('4','.','.','8','.','3','.','.','1')
        ,Vector('7','.','.','.','2','.','.','.','6')
        ,Vector('.','6','.','.','.','.','2','8','.')
        ,Vector('.','.','.','4','1','9','.','.','5')
        ,Vector('.','.','.','.','8','.','.','7','9')
    )
    println(s"Overall correct case. Expected: true, Actual: ${validSudoku(board4)}")

def validSudoku(board: Vector[Vector[Char]]): Boolean = 
    val boardRows = board
    val boardColumns = getBoardColumns(board)
    val boardSubBoxes = getBoardSubBoxes(board)
    sudokuSectionValid(boardRows) && sudokuSectionValid(boardColumns) && sudokuSectionValid(boardSubBoxes)

def getBoardColumns(board: Vector[Vector[Char]]): Vector[Vector[Char]] = 
    (for i <- 0 until board.size yield
        board.map(_(i))
    ).toVector

def getBoardSubBoxes(board: Vector[Vector[Char]]): Vector[Vector[Char]] = 
    val subBoxSize = 3
    (for 
        rowIdx <- 0 until board(0).size / subBoxSize
        colIdx <- 0 until board.size / subBoxSize
    yield
        board.slice(rowIdx * subBoxSize, (rowIdx + 1) * subBoxSize).flatMap(_.slice(colIdx * subBoxSize, (colIdx + 1) * subBoxSize))
    ).toVector

def sudokuSectionValid(sudokuSections: Vector[Vector[Char]]): Boolean = 
    val eachSectionValid = for sudokuSection <- sudokuSections yield
        val sectionElements = sudokuSection.filterNot(_ == '.')
        sectionElements == sectionElements.distinct
    eachSectionValid.forall(_ == true)