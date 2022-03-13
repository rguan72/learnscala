import scala.collection.mutable

@main def m() = 
    val treasureMap = mutable.Map.empty[Int, String]
    treasureMap += (1 -> "Go To Island")
    treasureMap += (2 -> "Find big X on ground")
    treasureMap += (3 -> "Dig")
    println(treasureMap(2))

    val romanNumeral = Map(
        1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V"
    )
    println(romanNumeral(4))