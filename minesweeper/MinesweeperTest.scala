import minesweeper.Minesweeper
import minesweeper.Tile

// learn how to use testing tools later
object MinesweeperTest:
    def main(args: Array[String]): Unit = 
        println("Test indexing")
        val board0 = Vector(
            Vector(Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false))
        )
        println(s"Expected: Vector(Vector((0, false), (0, false)), Vector((0, true), (0, false)), Actual: ${Minesweeper(board0).action('a', 1)}")
        val board1 = Vector(
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
        )
        val expected = Vector(
            Vector(Tile('1', true), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
            Vector(Tile('1', false), Tile('1', false), Tile('1', false)),
        )
        println(s"Expected: $expected, Actual: ${Minesweeper(board1).action('a', 3)}")

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

        println("-----------------")
        println("ALL TESTS PASS")
