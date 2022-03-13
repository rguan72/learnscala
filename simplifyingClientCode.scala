@main def m() = 
    def containsNeg(nums: List[Int]) = nums.exists(_ < 0)
    println(containsNeg(Nil))
    println(containsNeg(List(0, -1, -2)))
    def containsOdd(nums: List[Int]) = nums.exists(_ % 2 == 1)
    def curriedSum(x: Int)(y: Int) = x + y // same as def curriedSum(x: Int) = (y: Int) => x + y
    println(curriedSum(1)(2))
    var onePlus = curriedSum(1)
    println(onePlus(2))

    def twice(op: Double => Double, x: Double) = op(op(x))
    println(twice(_ + 1, 5))

    // def withPrintWriter(file: File, op: PrintWriter => Unit) = 
    //     val writer = new PrintWriter(file)
    //     try op(writer)
    //     finally writer.close()
    // withPrintWriter(
    //     new File("date.txt"),
    //     writer => writer.println(new java.util.Date)
    // )
    val s = "Hello world!"
    println(s.charAt{ 1 })

    // def withPrintWriter(file: File)(op: PrintWriter => Unit) = 
    //     val writer = new PrintWriter(File)
    //     try op(writer)
    //     finally writer.close()
    // val file = new File("date.txt")
    // withPrintWriter(file) {
    //     writer.println(new java.util.Date)
    // }
    var assertionsEnabled = true
    def byNameAssert(predicate: => Boolean) = 
        if assertionsEnabled && !predicate then
            throw new AssertionError
    byNameAssert(5 > 3)
    assertionsEnabled = false
    byNameAssert(1 / 0 == 0)