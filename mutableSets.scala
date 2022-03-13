import scala.collection.mutable

@main def m() = 
    val movieSet = mutable.Set("Good Will Hunting", "Spiderman", "Marvel")
    movieSet.+=("Parasite")
    movieSet.foreach(println)