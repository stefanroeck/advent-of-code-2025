enum class Direction { Left, Up, Right, Down }
data class Point(val x: Int, val y: Int)

class MapOfThings(input: List<String>, private val markerChar: Char) {

  private val data = input.toMutableList()

  fun hasMarker(point: Point): Boolean {
    return data.get(point.y).get(point.x) == markerChar
  }

  fun width() = data[0].length
  fun height() = data.size

  fun allPoints(): List<Point> {
    val result = mutableListOf<Point>()
    for (x in 0..<width()) {
      for (y in 0..<height()) {
        result.add(Point(x, y))
      }
    }
    return result
  }

  fun adjacentPoints(point: Point): List<Point> {
    val result = mutableListOf<Point>()
    with(point) {
      result.add(Point(x - 1, y - 1))
      result.add(Point(x - 1, y))
      result.add(Point(x - 1, y + 1))
      result.add(Point(x, y - 1))
      result.add(Point(x, y + 1))
      result.add(Point(x + 1, y - 1))
      result.add(Point(x + 1, y))
      result.add(Point(x + 1, y + 1))
    }
    return result.filter { it.x in (0..<width()) && it.y in (0..<height()) }
  }

  fun adjacentMarkers(point: Point) = adjacentPoints(point).filter { hasMarker(it) }

  fun clearMarkerAt(point: Point) {
    check(hasMarker(point)) { "No marker at point" }
    data[point.y] = data[point.y].replaceRange(point.x..point.x, ".")
  }

  fun markerCount() = allPoints().filter(::hasMarker).size
}

fun main() {

  fun part1(input: List<String>): Long {
    val pointsToRemove = mutableListOf<Point>()
    with(MapOfThings(input, '@')) {
      allPoints().forEach { point ->
        if (hasMarker(point) && adjacentMarkers(point).size < 4) {
          pointsToRemove.add(point)
        }
      }
    }
    return pointsToRemove.size.toLong()
  }

  fun part2(input: List<String>): Long {
    var removedCount: Long = 0
    with(MapOfThings(input, '@')) {
      var remainingMarkerCount = markerCount()

      while (true) {
        allPoints().forEach { point ->
          if (hasMarker(point) && adjacentMarkers(point).size < 4) {
            clearMarkerAt(point)
            removedCount++
          }
        }

        if (markerCount() == remainingMarkerCount) break
        remainingMarkerCount = markerCount()
      }

    }
    return removedCount
  }

  compareAndCheck(part1(readInput("Day04_test")), 13)
  compareAndCheck(part2(readInput("Day04_test")), 43)

  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
