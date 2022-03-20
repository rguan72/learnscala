import scala.io.StdIn.readLine
import scala.util.Random
import minesweeper.MinesweeperGameLoop
import minesweeper.MinesweeperOptions

@main def m(height: Int = 13, width: Int = 18, numBombs: Int = 16, seed: Int = new Random().nextInt) = 
    val opts = MinesweeperOptions(height, width, numBombs, seed)
    MinesweeperGameLoop.play(opts, print, readLine)
