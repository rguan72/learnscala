import minesweeper.Minesweeper
import minesweeper.Tile
import scala.util.Random

// learn how to use testing tools later

// replace all tests with equivalent tests that only work on driver code? Board state => visual output?
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

        println("-----------------")
        println("ALL TESTS PASS")
