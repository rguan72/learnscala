@main def m() = 
    val adjectives = List("One", "Two", "Red", "Buffer")
    // val nouns = adjectives.map(adj => adj + " Fish")
    val nouns = 
        for adj <- adjectives yield
            adj + " Fish"
    println(nouns)
    // val lengths = nouns.map(noun => noun.length)
    val lengths = 
        for noun <- nouns yield
            noun.length
    println(lengths)

    val ques = Vector("Who", "What", "Where", "When", "Why")
    // val usingMap = ques.map(q => q.toLowerCase + "?")
    val usingMap = 
        for q <- ques yield
            q.toLowerCase + "?"
    println(usingMap)

    val startsW = ques.find(q => q.startsWith("W"))
    val hasLen4 = ques.find(q => q.length == 4)
    val hasLen5 = ques.find(q => q.length == 5)
    val startsH = ques.find(q => q.startsWith("H"))

    println(startsW.map(word => word.toUpperCase))
    println(for word <- startsH yield word.toUpperCase)