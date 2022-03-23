import scala.util.Random
import scala.collection.mutable
import minesweeper.Minesweeper
import minesweeper.MinesweeperGameLoop
import minesweeper.MinesweeperOptions
import minesweeper.Tile


object MinesweeperTest:
    def main(args: Array[String]): Unit = 
        println("Test indexing")
        val board0 = Vector(
            Vector(Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false))
        )
        val expected0 = Minesweeper(Vector(
            Vector(Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', true))
        ))
        assert(expected0 == Minesweeper(board0).action('b', 1))

        val board1 = Vector(
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
        )
        val expected = Minesweeper(Vector(
            Vector(Tile('1', true), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
        ))
        assert(expected == Minesweeper(board1).action('a', 3))

        val expected2 = Minesweeper(Vector(
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', true)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
        ))
        assert(expected2 == Minesweeper(board1).action('c', 2))
        // println(s"Expected: $expected2, Actual: ${Minesweeper(board1).action('c', 2)}")

        val board2 = Vector(
            Vector(Tile('1', true), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', true), Tile('1', true), Tile('b', false)),
            Vector(Tile('b', false), Tile('1', true), Tile('1', true)),
        )
        assert(Minesweeper(board2).isVictorious)

        val board3 = Vector(
            Vector(Tile('1', true), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', true), Tile('1', true), Tile('b', false)),
            Vector(Tile('b', false), Tile('1', false), Tile('1', true)),
        )
        assert(!Minesweeper(board3).isVictorious)

        val board4 = Vector(
            Vector(Tile('1', true), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', true), Tile('1', true), Tile('b', true)),
            Vector(Tile('b', false), Tile('1', true), Tile('1', true)),
        )
        assert(!Minesweeper(board4).isVictorious)

        val board5 = Vector(
            Vector(Tile('1', true), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', true), Tile('1', true), Tile('b', true)),
            Vector(Tile('b', false), Tile('1', true), Tile('1', true)),
        )
        assert(Minesweeper(board5).isLost)

        val board6 = Vector(
            Vector(Tile('1', true), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', true), Tile('1', true), Tile('b', false)),
            Vector(Tile('b', false), Tile('1', true), Tile('1', true)),
        )
        assert(!Minesweeper(board6).isLost)

        val graphBoard = Vector(
            Vector(Tile('0', false), Tile('0', false), Tile('1', true),  Tile('1', false)),
            Vector(Tile('1', true), Tile('0', false), Tile('1', false), Tile('b', false)),
            Vector(Tile('0', false), Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', true), Tile('1', false)),
            Vector(Tile('b', false), Tile('1', false), Tile('1', true), Tile('1', false)),
        )
        val expectedGraph = Minesweeper(Vector(
            Vector(Tile('0', true), Tile('0', true), Tile('1', true),  Tile('1', false)),
            Vector(Tile('1', true), Tile('0', true), Tile('1', true), Tile('b', false)),
            Vector(Tile('0', true), Tile('1', true), Tile('1', true), Tile('1', false)),
            Vector(Tile('1', true), Tile('1', true), Tile('1', true), Tile('1', false)),
            Vector(Tile('b', false), Tile('1', false), Tile('1', true), Tile('1', false)),
        ))
        assert(Minesweeper(graphBoard).action('a', 5) == expectedGraph)

        val graphBoard1 = Vector(
            Vector(Tile('0', true), Tile('0', false)),
            Vector(Tile('0', false), Tile('0', false))
        )
        assert(Minesweeper(graphBoard1).action('a', 2) == Minesweeper(graphBoard1))

        val graphBoard2 = Vector(
            Vector(Tile('0', false), Tile('0', false), Tile('1', true),  Tile('1', false)),
            Vector(Tile('1', true), Tile('0', false), Tile('1', false), Tile('b', false)),
            Vector(Tile('1', false), Tile('0', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', true), Tile('0', false)),
            Vector(Tile('b', false), Tile('1', false), Tile('0', false), Tile('0', false)),
        )
        val graphBoard2expected = Minesweeper(Vector(
            Vector(Tile('0', false), Tile('0', false), Tile('1', true),  Tile('1', false)),
            Vector(Tile('1', true), Tile('0', false), Tile('1', false), Tile('b', false)),
            Vector(Tile('1', false), Tile('0', false), Tile('1', true), Tile('1', true)),
            Vector(Tile('1', false), Tile('1', true), Tile('1', true), Tile('0', true)),
            Vector(Tile('b', false), Tile('1', true), Tile('0', true), Tile('0', true)),
        ))
        assert(Minesweeper(graphBoard2).action('d', 2) == graphBoard2expected)

        val visual0 = Vector(
            Vector(Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false))
        )
        val expectedVisual0 = Vector("2| * *", "1| * *", "   ___", "   a b").mkString("\n")
        assert(Minesweeper(visual0).visualBoard == expectedVisual0)

        val visual1 = Vector(
            Vector(Tile('0', true), Tile('0', true), Tile('1', true),  Tile('1', false)),
            Vector(Tile('1', true), Tile('0', true), Tile('1', true), Tile('b', false)),
            Vector(Tile('0', true), Tile('1', true), Tile('1', true), Tile('1', false)),
            Vector(Tile('1', true), Tile('1', true), Tile('1', true), Tile('1', false)),
            Vector(Tile('b', false), Tile('1', false), Tile('1', true), Tile('1', false)),
        )
        val expectedVisual1 = Vector("5| 0 0 1 *", "4| 1 0 1 *", "3| 0 1 1 *", "2| 1 1 1 *", "1| * * 1 *", "   _______", "   a b c d").mkString("\n")
        assert(Minesweeper(visual1).visualBoard == expectedVisual1)

        val visual2 = Vector(
            Vector(Tile('2', true), Tile('b', true)),
            Vector(Tile('b', true), Tile('2', false))
        )
        val expectedVisual2 = Vector("2| 2 b", "1| b *", "   ___", "   a b").mkString("\n")
        assert(Minesweeper(visual2).visualBoard == expectedVisual2)

        assert(Minesweeper(1, 1, 0, Random(1)) == Minesweeper(Vector(Vector(Tile('0', false)))))

        val visual3 = Vector(
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
            Vector(Tile('0', true)),
        )
        val expectedVisual3 = Vector("11| 0", "10| 0", " 9| 0", " 8| 0", " 7| 0", " 6| 0", " 5| 0", " 4| 0", " 3| 0", " 2| 0", " 1| 0", "    _", "    a").mkString("\n")
        assert(Minesweeper(visual3).visualBoard == expectedVisual3)

        val visual4 = Vector(
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
            Vector(Tile('0', true), Tile('0', true)),
        )
        val expectedVisual4 = Vector("11| 0 0", "10| 0 0", " 9| 0 0", " 8| 0 0", " 7| 0 0", " 6| 0 0", " 5| 0 0", " 4| 0 0", " 3| 0 0", " 2| 0 0", " 1| 0 0", "    ___", "    a b").mkString("\n")
        assert(Minesweeper(visual4).visualBoard == expectedVisual4)

        val visual5 = Vector(
            Vector(Tile('2', true), Tile('b', false)),
            Vector(Tile('b', false), Tile('2', false))
        )
        val expectedVisual5 = Vector("2| 2 b", "1| b *", "   ___", "   a b").mkString("\n")
        assert(Minesweeper(visual5).action('a', 1).visualBoard == expectedVisual5)

        val expectedSetup0 = Minesweeper(Vector(
            Vector(Tile('1', false), Tile('1', false)),
            Vector(Tile('b', false), Tile('2', false)),
            Vector(Tile('b', false), Tile('2', false)),
        ))
        assert(Minesweeper(3, 2, 2, Random(1)) == expectedSetup0)

        val expectedSetup1 = Minesweeper(Vector(
            Vector(Tile('1',false), Tile('2',false), Tile('b',false), Tile('1',false), Tile('0',false)),
            Vector(Tile('1',false), Tile('b',false), Tile('2',false), Tile('1',false), Tile('0',false)),
            Vector(Tile('2',false), Tile('3',false), Tile('2',false), Tile('1',false), Tile('0',false)),
            Vector(Tile('b',false), Tile('2',false), Tile('b',false), Tile('1',false), Tile('0',false)),
        ))
        assert(Minesweeper(4, 5, 4, Random(1)) == expectedSetup1)

        val printedVals0 = mutable.ArrayBuffer.empty[String]
        val printDep0 = (x: Any) => { printedVals0.addOne(x.toString); () }

        val readLineDepIt0 = Iterator("a 1", "b 1")
        val readLineDep0 = () => readLineDepIt0.next()

        val opts0 = MinesweeperOptions(1, 2, 0, 0)
        MinesweeperGameLoop.play(opts0, printDep0, readLineDep0)
        val expectedInteg0 = mutable.ArrayBuffer(
            Vector("1| * *", "   ___", "   a b").mkString("\n"),
            "\n",
            "What square do you wish to tap? Type column then row, like b 3: ",
            "Congrats! You won!",
            "\n",
            Vector("1| 0 0", "   ___", "   a b").mkString("\n"),
            "\n"
        )
        assert(printedVals0 == expectedInteg0)

        val printedVals1 = mutable.ArrayBuffer.empty[String]
        val printDep1 = (x: Any) => { printedVals1.addOne(x.toString); () }

        val readLineDepIt1 = Iterator("b 3", "i 3", "g 11")
        val readLineDep1 = () => readLineDepIt1.next()
        val opts1 = MinesweeperOptions(13, 18, 16, 3)
        MinesweeperGameLoop.play(opts1, printDep1, readLineDep1)

        val test1printboard1 = """13| * * * * * * * * * * * * * * * * * *
12| * * * * * * * * * * * * * * * * * *
11| * * * * * * * * * * * * * * * * * *
10| * * * * * * * * * * * * * * * * * *
 9| * * * * * * * * * * * * * * * * * *
 8| * * * * * * * * * * * * * * * * * *
 7| * * * * * * * * * * * * * * * * * *
 6| * * * * * * * * * * * * * * * * * *
 5| * * * * * * * * * * * * * * * * * *
 4| * * * * * * * * * * * * * * * * * *
 3| * * * * * * * * * * * * * * * * * *
 2| * * * * * * * * * * * * * * * * * *
 1| * * * * * * * * * * * * * * * * * *
    ___________________________________
    a b c d e f g h i j k l m n o p q r"""
        val test1printboard2 = """13| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
12| 1 1 1 1 1 1 1 2 1 1 0 0 0 0 0 0 1 1
11| * * * * * * * * * 2 1 0 0 0 0 1 2 *
10| * * * * * * * * * * 1 0 0 0 0 1 * *
 9| * * * * * * * * * * 2 0 0 0 0 1 1 1
 8| * * * * * * * * * * 1 0 0 0 0 0 0 0
 7| * * * * * * * * * 2 1 0 0 0 0 0 0 0
 6| * * * * * 1 1 2 1 1 0 0 1 1 1 0 0 0
 5| 1 1 1 1 * 1 0 0 0 0 1 1 2 * 1 0 0 0
 4| 0 0 0 1 * 1 1 1 1 0 1 * 2 1 1 0 0 0
 3| 0 0 0 1 1 1 1 * 1 0 1 1 1 0 0 0 0 0
 2| 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 0
 1| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
    ___________________________________
    a b c d e f g h i j k l m n o p q r"""
        val test1printboard3 = """13| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
12| 1 1 1 1 1 1 1 2 1 1 0 0 0 0 0 0 1 1
11| * * * * * * * * * 2 1 0 0 0 0 1 2 *
10| * * * * * * * * * * 1 0 0 0 0 1 * *
 9| * * * * * * * * * * 2 0 0 0 0 1 1 1
 8| * * * * * * * * * * 1 0 0 0 0 0 0 0
 7| * * * * * * * * * 2 1 0 0 0 0 0 0 0
 6| * * * * * 1 1 2 1 1 0 0 1 1 1 0 0 0
 5| 1 1 1 1 * 1 0 0 0 0 1 1 2 * 1 0 0 0
 4| 0 0 0 1 * 1 1 1 1 0 1 * 2 1 1 0 0 0
 3| 0 0 0 1 1 1 1 * 1 0 1 1 1 0 0 0 0 0
 2| 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 0
 1| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
    ___________________________________
    a b c d e f g h i j k l m n o p q r"""
        val test1printboard4 = """13| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
12| 1 1 1 1 1 1 1 2 1 1 0 0 0 0 0 0 1 1
11| b * * b * * b * b 2 1 0 0 0 0 1 2 b
10| * * * * * * * * * b 1 0 0 0 0 1 b *
 9| * * * * * * * * * * 2 0 0 0 0 1 1 1
 8| * * * * b * * * * b 1 0 0 0 0 0 0 0
 7| * * * * * * b * b 2 1 0 0 0 0 0 0 0
 6| * b * * * 1 1 2 1 1 0 0 1 1 1 0 0 0
 5| 1 1 1 1 * 1 0 0 0 0 1 1 2 b 1 0 0 0
 4| 0 0 0 1 b 1 1 1 1 0 1 b 2 1 1 0 0 0
 3| 0 0 0 1 1 1 1 b 1 0 1 1 1 0 0 0 0 0
 2| 0 0 0 0 0 0 1 1 1 0 0 0 0 0 0 0 0 0
 1| 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
    ___________________________________
    a b c d e f g h i j k l m n o p q r"""
        val expectedInteg1 = mutable.ArrayBuffer(
            test1printboard1,
            "\n",
            "What square do you wish to tap? Type column then row, like b 3: ",
            test1printboard2,
            "\n",
            "What square do you wish to tap? Type column then row, like b 3: ",
            test1printboard3,
            "\n",
            "What square do you wish to tap? Type column then row, like b 3: ",
            "Oops, you lost",
            "\n",
            test1printboard4,
            "\n",
        )
        println("-----------------")
        println("ALL TESTS PASS")
