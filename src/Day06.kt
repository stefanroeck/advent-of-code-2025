fun main() {
  fun part1(input: List<String>): Long {
    val columns = MapOfPoints(input).columns()

    return columns.fold(0.toLong()) { sum, column ->
      val operator = column.last()
      val numbers = column.take(column.size - 1).map { it.value.toLong() }
      val columnValue = when (operator.value) {
        "+" -> numbers.sum()
        "*" -> numbers.fold(1.toLong()) { p, n -> p * n }
        else -> error("Unknown operator: ${operator.value}")
      }
      println("$operator $columnValue")
      sum + columnValue
    }
  }

  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day06_test")), 4277556)
  //compareAndCheck(part2(readInput("Day06_test")), 14)

  val input = readInput("Day06")
  part1(input).println()
  //part2(input).println()
}

