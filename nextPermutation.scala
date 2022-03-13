// wow great job good stuff!

@main def m() = 
    println(s"Expected: Vector(1), Actual: ${nextPermutation(Vector(1))}")
    println(s"Expected: Vector(1, 2, 3), Actual: ${nextPermutation(Vector(3, 2, 1))}")
    println(s"Expected: Vector(2, 1), Actual: ${nextPermutation(Vector(1, 2))}")
    println(s"Expected: Vector(1, 2, 4, 3), Actual: ${nextPermutation(Vector(1, 2, 3, 4))}")
    println(s"Expected: Vector(3, 2, 1, 1, 2), Actual: ${nextPermutation(Vector(3, 1, 2, 2, 1))}")
    println(s"Expected: Vector(3, 1, 4, 2, 5), Actual: ${nextPermutation(Vector(3, 1, 2, 5, 4))}")
    println(s"Expected: Vector(4, 1, 2, 3, 5), Actual: ${nextPermutation(Vector(3, 5, 4, 2, 1))}")

def nextPermutation(nums: Vector[Int]): Vector[Int] = 
    if nums.size == 1 then
        nums
    else
        nextPermutationMultiElement(nums)
        
def nextPermutationMultiElement(nums: Vector[Int]): Vector[Int] = 
    val righmostIndexNotWeaklyDescending = nums.sliding(2).toVector.lastIndexWhere(vec => vec(0) < vec(1))
    if isNumsWeaklyDescending(righmostIndexNotWeaklyDescending) then
        nums.reverse
    else
        swapElementAndReverseRightSide(nums, righmostIndexNotWeaklyDescending)

def swapElementAndReverseRightSide(nums: Vector[Int], righmostIndexNotWeaklyDescending: Int): Vector[Int] = 
    val rightmostNumNotWeaklyDescending = nums(righmostIndexNotWeaklyDescending)
    val (smallestWithConditions, smallestIndexWithConditions) =  nums.zipWithIndex.
        filter(
            valIndex => valIndex._1 > rightmostNumNotWeaklyDescending && valIndex._2 > righmostIndexNotWeaklyDescending
        ).minBy(
            valIndex => (valIndex._1, -valIndex._2)
        )
    val swapped = nums.
        updated(smallestIndexWithConditions, nums(righmostIndexNotWeaklyDescending)).
        updated(righmostIndexNotWeaklyDescending, nums(smallestIndexWithConditions))
    swapped.take(righmostIndexNotWeaklyDescending) ++: (smallestWithConditions +: swapped.drop(righmostIndexNotWeaklyDescending + 1).reverse)

def isNumsWeaklyDescending(righmostIndexNotWeaklyDescending: Int): Boolean = 
    righmostIndexNotWeaklyDescending == -1