enum class Direction { Left, Up, Right, Down }
data class Point(val x: Int, val y: Int) {
  fun translate(direction: Direction): Point {
    return when (direction) {
      Direction.Left -> Point(x - 1, y)
      Direction.Up -> Point(x, y - 1)
      Direction.Right -> Point(x + 1, y)
      Direction.Down -> Point(x, y + 1)
    }
  }
}

class MapOfThings(private val input: List<String>, private val markerChar: Char) {

  fun hasMarker(point: Point): Boolean {
    return input.get(point.y).get(point.x) == markerChar
  }

  fun width() = input.get(0).length
  fun height() = input.size

  fun allPoints(): MutableList<Point> {
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
}

fun main() {

  fun part1(input: List<String>): Long {
    val pointsToRemove = mutableListOf<Point>()
    with(MapOfThings(input, '@')) {
      allPoints().forEach { point ->
        if (hasMarker(point)) {
          val adjacentMarkers = adjacentPoints(point).count { hasMarker(it) }
          if (adjacentMarkers < 4) {
            pointsToRemove.add(point)
          }
        }
      }
    }
    return pointsToRemove.size.toLong()
  }

  fun part2(input: List<String>): Long {
    return 0
  }

  compareAndCheck(part1(readInput("Day04_test")), 13)
  //compareAndCheck(part2(readInput("Day04_test")), 1)

  val input = readInput("Day04")
  part1(input).println()
  //part2(input).println()
}
