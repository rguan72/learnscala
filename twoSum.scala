import scala.collection.mutable

@main def m() = 
    // val left = Array(2, 7, 11, 15)
    println(twoSum(Array(2, 7, 11, 15), 9).mkString(" "))
    assert(twoSum(Array(2, 7, 11, 15), 9) sameElements Array(1, 0))
    println(twoSum(Array(3, 2, 4), 6).mkString(" "))
    assert(twoSum(Array(3, 2, 4), 6) sameElements Array(2, 1))
    // assert(twoSum(left == Array(0, 1)))

def twoSum(nums: Array[Int], target: Int): Array[Int] = 
    val seen = mutable.Map.empty[Int, Int]
    for (num, idx) <- nums.zipWithIndex do
        val differenceToTarget = target - num
        if seen.contains(differenceToTarget) then
            val idxOfDifferenceToTarget = seen(differenceToTarget)
            return Array(idx, idxOfDifferenceToTarget)
        seen(num) = idx
    return Array(-1, -1)

// def twoSum(nums: Array[Int], target: Int): Array[Int] =
//         val seen = mutable.Map.empty[Int, Int]
//         for numAndIdx <- nums.zipWithIndex do
//             val num = numAndIdx(0)
//             val idx = numAndIdx(1)
//             val differenceToTarget = target - num
//             if seen.contains(differenceToTarget) then
//                 val idxOfDifferenceToTarget = seen(differenceToTarget)
//                 return Array(idx, idxOfDifferenceToTarget)
//             seen(idx) = num