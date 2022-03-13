@main def m() = 
    println("hi")

object FileMatcher:
    private val filesHere = List("coolfile.scala", "otherfile.scala", "scalaGreat.scala")

    def filesMatching(matcher: String => Boolean) = // function parameters
        for file <- filesHere if matcher(file, query)
        yield file

    def filesEnding(query: String) = 
        filesMatching(_.endsWith(query)) // uses closures

    def filesContaining(query: String) = 
        filesMatching(_.contains(query))

    def filesRegex(query: String) = 
        filesMatching(_.matches(query))