import scala.collection.mutable.Stack

@main def m() = 
    println(s"Expected: true, Actual: ${validParantheses("()")}")
    println(s"Expected: true, Actual: ${validParantheses("[]")}")
    println(s"Expected: true, Actual: ${validParantheses("{}")}")
    println(s"Expected: false, Actual: ${validParantheses("((")}")
    println(s"Expected: false, Actual: ${validParantheses("(]")}")
    println(s"Expected: true, Actual: ${validParantheses("({}[])")}")
    println(s"Expected: false, Actual: ${validParantheses("((())")}")
    println(s"Expected: true, Actual: ${validParantheses("((()[]){}{})()")}")

// currentParanthese = '(', index = 1

def validParantheses(parantheses: String): Boolean = 
    val openToClosedParantheses = Map(('(' -> ')'), ('[' -> ']'), ('{' -> '}'))
    val paranthesesStack = Stack(parantheses(0))
    var index = 1
    while !paranthesesStack.isEmpty && index < parantheses.size do
        if openToClosedParantheses(paranthesesStack.top) == parantheses(index) then
            paranthesesStack.pop()
        else if openToClosedParantheses.contains(parantheses(index)) then
            paranthesesStack.push(parantheses(index))
        else
            return false
        index += 1
    paranthesesStack.isEmpty


// currentParanthese always open

// (())