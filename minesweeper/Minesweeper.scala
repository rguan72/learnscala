package minesweeper

import scala.collection.mutable
import scala.util.Random
import scala.collection.mutable.ArrayBuffer

object MinesweeperGameLoop:
    def play(opts: MinesweeperOptions, printDep: (Any) => Unit, readLineDep: () => String): Unit = 
        val random = Random(opts.seed)
        var myMinesweeper = Minesweeper(opts.height, opts.width, opts.numBombs, random)
        val printlnDep = (x: Any) => { printDep(x); printDep("\n") }
        printlnDep(myMinesweeper.visualBoard)
        while isGameNotEnded(myMinesweeper) do
            myMinesweeper = oneGameIter(myMinesweeper, printDep, readLineDep)

    def oneGameIter(myMinesweeper: Minesweeper, printDep: (Any) => Unit, readLineDep: () => String): Minesweeper =
        val printlnDep = (x: Any) => { printDep(x); printDep("\n") }
        printDep("What square do you wish to tap? Type column then row, like b 3: ")
        val square = readLineDep()
        val (col, row) = (square.split(" ")(0).toCharArray, square.split(" ")(1).toInt)
        val myNewMinesweeper = myMinesweeper.action(col(0), row)
        if myNewMinesweeper.isLost then
            printlnDep("Oops, you lost")
        if myNewMinesweeper.isVictorious then
            printlnDep("Congrats! You won!")
        printlnDep(myNewMinesweeper.visualBoard)
        myNewMinesweeper

    def isGameNotEnded(myMinesweeper: Minesweeper): Boolean =
        !myMinesweeper.isLost && !myMinesweeper.isVictorious


class Minesweeper(
    val board: Vector[Vector[Tile]]
):
    def width: Int = board(0).size
    def height: Int = board.size
    override def toString: String = board.mkString("\n")
    def == (that: Minesweeper): Boolean =
        this.board == that.board
    def this(height: Int, width: Int, numBombs: Int, random: Random) = 
        this {

            def getNeighborsInit(colIndex: Int, rowIndex: Int, height: Int, width: Int): Seq[(Int, Int)] = 
                val shifts = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
                for (colShift, rowShift) <- shifts if inBoundsInit(colIndex + colShift, rowIndex + rowShift, height, width) yield
                    (colIndex + colShift, rowIndex + rowShift)

            def inBoundsInit(colIndex: Int, rowIndex: Int, height: Int, width: Int): Boolean = 
                colIndex >= 0 && colIndex < width && rowIndex >= 0 && rowIndex < height

            val emptyBoard = Vector.fill(height * width)(Tile('0', false))
            val bombIndices = random.shuffle(0 until height * width).take(numBombs).toSet
            val boardWithBombs = emptyBoard.zipWithIndex.map((tile, index) => bombIndices.contains(index) match {
                case true => Tile('b', false)
                case false => Tile('0', false)
            })
            val boardWithBombs2D = boardWithBombs.grouped(width).toVector
            boardWithBombs2D.zipWithIndex.map((row, rowIndex) => row.zipWithIndex.map((tile, colIndex) => {
                val neighborBombs: Seq[Boolean] = for (neighborColIndex, neighborRowIndex) <- getNeighborsInit(colIndex, rowIndex, height, width) yield
                    boardWithBombs2D(neighborRowIndex)(neighborColIndex).value == 'b'
                val numNeighborBombs: Int = neighborBombs.count(_ == true)
                tile.value match {
                    case 'b' => Tile('b', false)
                    case _ => { val str = numNeighborBombs.toString; Tile(str(0), false) }
                }
            }))
        }

    def action(col: Char, row: Int): Minesweeper =
        val colIndex: Int = col - 'a'
        val rowIndex = height - row
        val newBoard = revealCoordinateAndConnectedZeros(colIndex, rowIndex)
        if newBoard.isLost then
            newBoard.revealAllBombs()
        else
            newBoard

    def isVictorious: Boolean = 
        val eachTileSuccess = 
            for 
                row <- board
                tile <- row
            yield 
                (tile.value != 'b' && tile.revealed) || (tile.value == 'b' && !tile.revealed)
        eachTileSuccess.forall(_ == true)

    def isLost: Boolean = 
        val eachTileLost = 
            for 
                row <- board
                tile <- row
            yield
                tile.value == 'b' && tile.revealed
        eachTileLost.exists(_ == true)

    def visualBoard: String =             
        val maxLeftMargin = height.toString.length
        val boardWithoutBottomLabel = board.zipWithIndex.map((row, index) => {
                val indexLabel = height - index
                val tileLabels = row.map(tile => tile.revealed match {
                    case true => tile.value.toString
                    case false => "*"
                })
                val leftMargin = maxLeftMargin - indexLabel.toString.length
                s"${" " * leftMargin}$indexLabel| ${tileLabels.mkString(" ")}"
            }
        )
        val lastChar = ('a' + width).toChar
        val bottomLength = width * 2 - 1
        val bottomDivider = s"${" " * (maxLeftMargin + 2)}${"_" * bottomLength}"
        val bottomLabel = s"${" " * (maxLeftMargin + 2)}${('a' until lastChar).mkString(" ")}"
        (boardWithoutBottomLabel :+ bottomDivider :+ bottomLabel).mkString("\n")

    def revealCoordinateAndConnectedZeros(colIndex: Int, rowIndex: Int): Minesweeper = 
        val originalTile = board(rowIndex)(colIndex)
        val mutableBoard = board.map(_.to(mutable.ArrayBuffer)).to(mutable.ArrayBuffer)        
        setTileToRevealed(mutableBoard, colIndex, rowIndex)
        val stack = mutable.Stack.empty[(Int, Int)]
        if originalTile.value == '0' && !originalTile.revealed then
            stack.push((colIndex, rowIndex))
        while !stack.isEmpty do
            val (currColIndex, currRowIndex) = stack.pop()
            for (neighborColIndex, neighborRowIndex) <- getNeighbors(currColIndex, currRowIndex) do
                revealNeighborAndPushIfZeroNotRevealed(mutableBoard, stack, neighborColIndex, neighborRowIndex)
        Minesweeper(mutableBoard.map(_.toVector).toVector)

    def revealNeighborAndPushIfZeroNotRevealed(mutableBoard: mutable.ArrayBuffer[mutable.ArrayBuffer[Tile]], stack: mutable.Stack[(Int, Int)], neighborColIndex: Int, neighborRowIndex: Int): Unit =
        if !mutableBoard(neighborRowIndex)(neighborColIndex).revealed then
            setTileToRevealed(mutableBoard, neighborColIndex, neighborRowIndex)
            if mutableBoard(neighborRowIndex)(neighborColIndex).value == '0' then
                stack.push((neighborColIndex, neighborRowIndex))

    def revealAllBombs(): Minesweeper =
        Minesweeper(board.map(row => row.map(tile => tile.value match { 
            case 'b' => Tile(tile.value, true)
            case _ => tile
        })))

    def setTileToRevealed(mutableBoard: mutable.ArrayBuffer[mutable.ArrayBuffer[Tile]], colIndex: Int, rowIndex: Int): Unit = 
        val tile = mutableBoard(rowIndex)(colIndex)
        mutableBoard(rowIndex)(colIndex) = Tile(tile.value, true)

    def getNeighbors(colIndex: Int, rowIndex: Int): Seq[(Int, Int)] = 
        val shifts = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
        for (colShift, rowShift) <- shifts if inBounds(colIndex + colShift, rowIndex + rowShift) yield
            (colIndex + colShift, rowIndex + rowShift)

    def inBounds(colIndex: Int, rowIndex: Int): Boolean = 
        colIndex >= 0 && colIndex < width && rowIndex >= 0 && rowIndex < height

end Minesweeper

case class Tile(
    value: Char,
    revealed: Boolean
)

case class MinesweeperOptions(
    height: Int,
    width: Int,
    numBombs: Int,
    seed: Int
)
