import kotlin.math.max
import kotlin.math.min

class MapOfPoints(private val data: List<ValuePoint>) {

  constructor(width: Int, height: Int, value: String) : this((0..<width).flatMap { x ->
    (0..<height).map { y -> ValuePoint(x, y, value) }
  })

  constructor(input: List<String>, colDelimiter: String = " ") : this(input.flatMapIndexed { y, line ->
    line
      .split(colDelimiter)
      .filter { it.isNotBlank() }
      .mapIndexed { x, token -> ValuePoint(x, y, token) }
  }
  )


  val columns = data.groupBy { it.x }.map { it.value }
  val rows = data.groupBy { it.y }.map { it.value }

  val width = columns.size
  val height = rows.size

  fun allPoints(): List<ValuePoint> {
    return data
  }

  fun pointsWithValue(value: String) = allPoints().filter { it.value == value }

  fun countPointsWithValue(value: String) = pointsWithValue(value).size

  fun pointAt(x: Int, y: Int): ValuePoint? =
    if (x in 0..<width && y in 0..<height) {
      columns[x][y]
    } else {
      null
    }


  fun adjacentPoints(point: ValuePoint, predicate: ((ValuePoint) -> Boolean) = { true }): List<ValuePoint> {
    val result = mutableListOf<ValuePoint>()
    with(point) {
      for (dx in -1..1) {
        for (dy in -1..1) {
          if (!(dx == 0 && dy == 0))
            pointAt(x + dx, y + dy)?.let { result.add(it) }
        }
      }
    }
    return result.filter(predicate)
  }

  fun print() {
    rows.map { row ->
      println(row.joinToString("") { it.value })
    }
  }

  fun pointsBetween(first: ValuePoint, second: ValuePoint): List<ValuePoint> {
    return (min(first.x, second.x)..max(first.x, second.x)).flatMap { x ->
      (min(first.y, second.y)..max(first.y, second.y)).map { y -> pointAt(x, y)!! }
    }
  }


  fun connectedPoints(start: ValuePoint, withValue: String): List<ValuePoint> {
    val connectedPoints = mutableSetOf<ValuePoint>()
    val pointsToCheck = mutableListOf(start)
    while (pointsToCheck.isNotEmpty()) {
      val point = pointsToCheck.first()
      pointsToCheck.remove(point)
      if (point.value == withValue) {
        connectedPoints.add(point)
        pointsToCheck.addAll(adjacentPoints(point) { it.value == withValue && !connectedPoints.contains(it) })
      }
    }
    return connectedPoints.toList()
  }
}

data class ValuePoint(val x: Int, val y: Int, var value: String)