fun main() {

  fun part1(input: List<String>): Long {
    val splitterIdx = input.indexOf("")
    val freshIngredientsSection = input.subList(0, splitterIdx)
    val allIngredients = input.subList(splitterIdx + 1, input.size).map { it.toLong() }
    val freshIngredients = freshIngredientsSection.fold(mutableListOf<LongRange>()) { acc, seq ->
      val split = seq.split("-")
      split.forEach { check(it.length <= 18) }
      acc.add(LongRange(split[0].toLong(), split[1].toLong()))
      acc
    }

    val count = allIngredients.count { ingredient ->
      freshIngredients.any { it.contains(ingredient) }
    }

    return count.toLong()
  }

  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day05_test")), 3)
  //compareAndCheck(part2(readInput("Day05_test")), 43)

  val input = readInput("Day05")
  part1(input).println()
  //part2(input).println()
}
