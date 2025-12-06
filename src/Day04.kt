fun main() {

  fun part1(input: List<String>): Long {
    val pointsToRemove = mutableListOf<ValuePoint>()
    with(MapOfPoints(input, "")) {
      allPoints().forEach { point ->
        if (point.value == "@" && adjacentPoints(point) { it.value == "@" }.size < 4) {
          pointsToRemove.add(point)
        }
      }
    }
    return pointsToRemove.size.toLong()
  }

  fun part2(input: List<String>): Long {
    var removedCount: Long = 0
    with(MapOfPoints(input, "")) {
      var remainingMarkerCount = countPointsWithValue("@")

      while (true) {
        allPoints().forEach { point ->
          if (point.value == "@" && adjacentPoints(point) { it.value == "@" }.size < 4) {
            point.value = "."
            removedCount++
          }
        }

        if (countPointsWithValue("@") == remainingMarkerCount) break
        remainingMarkerCount = countPointsWithValue("@")
      }

    }
    return removedCount
  }

  compareAndCheck(part1(readInput("Day04_test")), 13)
  compareAndCheck(part2(readInput("Day04_test")), 43)

  val input = readInput("Day04")
  part1(input).println() //1433
  part2(input).println() //8616
}
