import kotlin.math.abs

fun main() {

  fun Pair<ValuePoint, ValuePoint>.area(): Long =
    abs(first.x - second.x + 1).toLong() * abs(first.y - second.y + 1).toLong()


  fun part1(input: List<String>): Long {
    val points = input.map {
      val (x, y) = it.split(",")
      ValuePoint(x.toInt(), y.toInt(), "")
    }

    val pairs = points.flatMapIndexed { idx, point1 ->
      IntRange(idx + 1, points.size - 1)
        .map { point2 -> Pair(point1, points[point2]) }
    }

    val maxArea = pairs.associateWith { it.area() }.maxBy { it.value }
    println("Max ${maxArea.value} between ${maxArea.key}")

    return maxArea.value
  }


  fun part2(input: List<String>): Long {
    val redTiles = input.map {
      val (x, y) = it.split(",")
      ValuePoint(x.toInt(), y.toInt(), "#")
    }
    val maxX = redTiles.maxOf { it.x } + 3
    val maxY = redTiles.maxOf { it.y } + 1
    val map = MapOfPoints(maxX, maxY, ".")

    val greenBorderTiles = (redTiles.zipWithNext() + Pair(redTiles.last(), redTiles.first()))
      .flatMap { (first, second) ->
        map.pointsBetween(first, second)
      }
    println("found ${greenBorderTiles.size} green tiles")
    redTiles.forEach { map.pointAt(it.x, it.y)?.value = "#" }
    greenBorderTiles.forEach { map.pointAt(it.x, it.y)?.value = "X" }
    map.print()

    val pointsOutside = map.connectedPoints(map.pointAt(0, 0)!!, ".").toSet()

    val rectangles = redTiles.flatMapIndexed { idx, point1 ->
      IntRange(idx + 1, redTiles.size - 1)
        .map { point2 -> Pair(point1, redTiles[point2]) }
    }

    val maxArea = rectangles
      .filter { map.pointsBetween(it.first, it.second).none { rectPoint -> pointsOutside.contains(rectPoint) } }
      .associateWith { it.area() }.maxBy { it.value }

    println("Max ${maxArea.value} between ${maxArea.key}")

    return maxArea.value
  }

  //compareAndCheck(part1(readInput("Day09_test")), 50)
  //compareAndCheck(part2(readInput("Day09_test")), 24)

  val input = readInput("Day09")
  //part1(input).println()
  part2(input).println()
}

