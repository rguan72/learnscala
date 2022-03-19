import scala.collection.mutable
import scala.util.Random
import scala.io.StdIn.readLine
import minesweeper.Minesweeper

@main def m(height: Int = 13, width: Int = 18, numBombs: Int = 16, seed: Int = new Random().nextInt) = 
    val random = Random(seed)
    var minesweeper = Minesweeper(height, width, numBombs, random)
    println(minesweeper.visualBoard)
    while !minesweeper.isLost && !minesweeper.isVictorious do
        print("What square to you wish to tap? Type column then row, like b 3: ")
        val square = readLine()
        val col = square.split(" ")(0).toCharArray
        val row = square.split(" ")(1).toInt
        minesweeper = minesweeper.action(col(0), row)
        if minesweeper.isLost then
            println("Oops, you lost")
        if minesweeper.isVictorious then
            println("Congrats! You won!")
        println(minesweeper.visualBoard)
