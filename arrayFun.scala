@main def m() = 
    val greetStrings = new Array[String](3)
    greetStrings.update(0, "Hello")
    greetStrings.update(1, ", ")
    greetStrings.update(2, "world!\n")

    for i <- 0 to 2 do
        print(greetStrings.apply(i))