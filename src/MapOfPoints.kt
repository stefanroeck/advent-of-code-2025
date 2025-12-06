class MapOfPoints(input: List<String>, colDelimiter: String = " ") {

  private val data: List<ValuePoint> = input.flatMapIndexed { y, line ->
    line
      .split(colDelimiter)
      .filter { it.isNotBlank() }
      .mapIndexed { x, token -> ValuePoint(x, y, token) }
  }

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

}

data class ValuePoint(val x: Int, val y: Int, var value: String)