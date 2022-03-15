// make an entire game with i/o?
// Can use the element class to assist with i/o
// print out the board and everything in text lmao
package minesweeper

import scala.collection.mutable

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
// flipping rows and columns somewhere
    def revealCoordinateAndConnectedZeros(colIndex: Int, rowIndex: Int): Minesweeper = 
        val mutableBoard = board.map(_.to(mutable.ArrayBuffer)).to(mutable.ArrayBuffer)
        setTileToRevealed(mutableBoard, colIndex, rowIndex)
        if mutableBoard(rowIndex)(colIndex).value == '0' then
            val stack = mutable.Stack.empty[(Int, Int)]
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
