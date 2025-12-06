fun main() {

  fun List<Long>.product() = fold(1.toLong()) { p, n -> p * n }

  fun part1(input: List<String>): Long {
    val columns = MapOfPoints(input).columns

    return columns.fold(0.toLong()) { sum, column ->
      val operator = column.last()
      val numbers = column.take(column.size - 1).map { it.value.toLong() }
      val columnValue = when (operator.value) {
        "+" -> numbers.sum()
        "*" -> numbers.product()
        else -> error("Unknown operator: ${operator.value}")
      }
      sum + columnValue
    }
  }

  val operators = setOf('+', '*').toCharArray()

  fun part2(input: List<String>): Long {

    val operatorLine = input.first { line -> line.all { it in listOf(' ', '*', '+') } }
    val operatorLineIdx = input.indexOf(operatorLine)
    val inputLines = input.filter { it != operatorLine }
    val valuePoints = mutableListOf<ValuePoint>()
    var x = 0
    operatorLine.forEachIndexed { opIdx, operatorChar ->
      if (operatorChar in operators) {
        val nextOpIdx = operatorLine.indexOfAny(operators, opIdx + 1)
        inputLines.forEachIndexed { y, line ->
          val numberStr = if (nextOpIdx > 0) line.substring(opIdx, nextOpIdx - 1) else line.substring(opIdx)
          valuePoints.add(ValuePoint(x, y, numberStr))
        }
        valuePoints.add(ValuePoint(x, operatorLineIdx, operatorChar.toString()))
        x++
      }
    }

    val columns = valuePoints.groupBy { it.x }.map { it.value }

    return columns.fold(0.toLong()) { sum, column ->
      val operator = column.last()
      val numberStrings = column.take(column.size - 1)
      val maxDigits = numberStrings.maxOf { it.value.length }
      val numbers = (0..<maxDigits).map { digit ->
        numberStrings
          .map { it.value.getOrNull(digit) ?: "" }
          .joinToString("") { it.toString() }
          .trim().toLong()
      }

      val columnValue = when (operator.value) {
        "+" -> numbers.sum()
        "*" -> numbers.product()
        else -> error("Unknown operator: ${operator.value}")
      }
      sum + columnValue
    }
  }

  compareAndCheck(part1(readInput("Day06_test")), 4277556)
  compareAndCheck(part2(readInput("Day06_test")), 3263827)

  val input = readInput("Day06")
  part1(input).println()
  part2(input).println()
}

