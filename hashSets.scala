import scala.collection.immutable.HashSet

@main def m() = 
    val hashSet = HashSet("Tomatoes", "Chilies")
    val ingredients = hashSet + "Coriander"
    println(ingredients)
