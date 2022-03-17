// make an entire game with i/o?
// Can use the element class to assist with i/o
// print out the board and everything in text lmao
package minesweeper

import scala.collection.mutable
import scala.util.Random

object MinesweeperDriver:
    def main(args: Array[String]): Unit = 
        println("hello world")
    def henlo: String = "henlo"
end MinesweeperDriver
// nice constructor for Minesweeper in Minesweeper driver

class Minesweeper(
    val board: Vector[Vector[Tile]]
):
    def width: Int = board(0).size
    def height: Int = board.size
    def this(height: Int, width: Int, numBombs: Int, random: Random) = 
        this {
            val emptyBoard = Vector.fill(height * width)(Tile('0', false))
            val bombIndices = random.shuffle(0 until height * width).take(numBombs).toSet
            val boardWithBombs = emptyBoard.zipWithIndex.map((tile, index) => bombIndices.contains(index) match {
                case true => Tile('b', false)
                case false => Tile('0', false)
            })
            val boardWithBombs2D = boardWithBombs.grouped(width).toVector
            def getNeighborsInit(colIndex: Int, rowIndex: Int, height: Int, width: Int): Seq[(Int, Int)] = 
                val shifts = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
                for (colShift, rowShift) <- shifts if inBoundsInit(colIndex + colShift, rowIndex + rowShift, height, width) yield
                    (colIndex + colShift, rowIndex + rowShift)
            def inBoundsInit(colIndex: Int, rowIndex: Int, height: Int, width: Int): Boolean = 
                colIndex >= 0 && colIndex < width && rowIndex >= 0 && rowIndex < height

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
    override def toString: String = board.mkString("\n")
    def == (that: Minesweeper): Boolean = 
        this.board == that.board

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

    def action(col: Char, row: Int): Minesweeper = 
        val colIndex: Int = col - 'a'
        val rowIndex = height - row
        revealCoordinateAndConnectedZeros(colIndex, rowIndex)

    def visualBoard: String = 
        val boardWithoutBottomLabel = board.zipWithIndex.map((row, index) => {
                val indexLabel = height - index
                val tileLabels = row.map(tile => tile.revealed match {
                    case true => tile.value.toString
                    case false => "*"
                })
                s"$indexLabel| ${tileLabels.mkString(" ")}"
            }
        )
        val lastChar = ('a' + width).toChar
        val bottomLength = width * 2 - 1
        val bottomDivider = s"   ${"_" * bottomLength}"
        val bottomLabel = s"   ${('a' until lastChar).mkString(" ")}"
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
                if !mutableBoard(neighborRowIndex)(neighborColIndex).revealed then
                    setTileToRevealed(mutableBoard, neighborColIndex, neighborRowIndex)
                    if mutableBoard(neighborRowIndex)(neighborColIndex).value == '0' then
                        stack.push((neighborColIndex, neighborRowIndex))
        Minesweeper(mutableBoard.map(_.toVector).toVector)

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
