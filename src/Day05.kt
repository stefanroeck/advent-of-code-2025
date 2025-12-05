fun main() {

  fun LongRange.intersectsWith(otherRange: LongRange): Boolean {
    if (first >= otherRange.first && last <= otherRange.last) return true
    if (first < otherRange.first && last >= otherRange.first) return true
    if (first <= otherRange.last && last > otherRange.last) return true
    return false
  }

  fun LongRange.intersects(otherRange: LongRange): Boolean {
    return this.intersectsWith(otherRange) || otherRange.intersectsWith(this)
  }

  fun joinRanges(ranges: List<LongRange>): List<LongRange> {
    val result = mutableListOf<LongRange>()
    val remainingRanges = ranges.toMutableList()
    while (remainingRanges.isNotEmpty()) {
      val range = remainingRanges.first()
      val overlappingRanges = remainingRanges.filter { it.intersects(range) }
      if (overlappingRanges.size > 1) {
        remainingRanges.remove(range)
        overlappingRanges.forEach { remainingRanges.remove(it) }

        val min = (listOf(range) + overlappingRanges).minBy { it.first }
        val max = (listOf(range) + overlappingRanges).maxBy { it.last }
        remainingRanges.add(LongRange(min.first, max.last))
      } else {
        remainingRanges.remove(range)
        result.add(range)
      }
    }

    return result
  }


  fun testIntersect() {
    val range = LongRange(10, 20)
    check(range.intersects(LongRange(11, 12)))
    check(range.intersects(LongRange(9, 21)))
    check(range.intersects(LongRange(9, 11)))
    check(range.intersects(LongRange(19, 21)))
    check(range.intersects(LongRange(10, 20)))
    check(range.intersects(LongRange(10, 11)))
    check(range.intersects(LongRange(9, 10)))
    check(range.intersects(LongRange(20, 21)))
    check(range.intersects(LongRange(19, 20)))

    check(!range.intersects(LongRange(5, 6)))
    check(!range.intersects(LongRange(21, 25)))
  }

  fun part1(input: List<String>): Long {
    val splitterIdx = input.indexOf("")
    val freshIngredientsSection = input.subList(0, splitterIdx)
    val allIngredients = input.subList(splitterIdx + 1, input.size).map { it.toLong() }
    val freshIngredients = freshIngredientsSection.map { seq ->
      val split = seq.split("-")
      LongRange(split[0].toLong(), split[1].toLong())
    }

    val count = allIngredients.count { ingredient ->
      freshIngredients.any { it.contains(ingredient) }
    }

    return count.toLong()
  }

  fun part2(input: List<String>): Long {
    val splitterIdx = input.indexOf("")
    val freshIngredientsSection = input.subList(0, splitterIdx)
    val freshIngredients = freshIngredientsSection.map { seq ->
      val split = seq.split("-")
      LongRange(split[0].toLong(), split[1].toLong())
    }

    val joinedRanges = joinRanges(freshIngredients)
    return joinedRanges.fold(0.toLong()) { acc, range -> acc + (range.last - range.first + 1) }
  }

  testIntersect()

  compareAndCheck(part1(readInput("Day05_test")), 3)
  compareAndCheck(part2(readInput("Day05_test")), 14)

  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}

