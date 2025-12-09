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

    val maxDistance = pairs.associateWith { it.area() }.maxBy { it.value }
    println("Max ${maxDistance.value} between ${maxDistance.key}")

    return maxDistance.value
  }


  fun part2(input: List<String>): Long {
    return 0
  }

  //compareAndCheck(part1(readInput("Day09_test")), 50)
  compareAndCheck(part2(readInput("Day09_test")), 25272)

  val input = readInput("Day09")
  part1(input).println()
  //part2(input).println()
}

