fun main() {
  fun processBeamDownPart1(
    map: MapOfPoints,
    position: ValuePoint,
    splitCount: Long,
    beamStartingPoints: MutableSet<ValuePoint>
  ): Long {
    var downBeam: ValuePoint? = position
    do {
      check(downBeam != null)
      downBeam = map.pointAt(downBeam.x, downBeam.y + 1)
    } while (downBeam != null && downBeam.value == ".")

    if (downBeam == null) {
      return splitCount
    }
    check(downBeam.value == "^")

    val leftBeam = map.pointAt(downBeam.x - 1, downBeam.y)
    val rightBeam = map.pointAt(downBeam.x + 1, downBeam.y)

    var additionalSplits = 0
    val splitCountLeft = leftBeam?.let {
      if (!beamStartingPoints.contains(it)) {
        additionalSplits = 1
        beamStartingPoints.add(it)
        processBeamDownPart1(map, it, splitCount, beamStartingPoints)
      } else 0
    } ?: 0
    val splitCountRight = rightBeam?.let {
      if (!beamStartingPoints.contains(it)) {
        additionalSplits = 1
        beamStartingPoints.add(it)
        processBeamDownPart1(map, it, splitCount, beamStartingPoints)
      } else 0
    } ?: 0

    return splitCountLeft + splitCountRight + additionalSplits
  }


  fun processBeamDownPart2(
    map: MapOfPoints,
    position: ValuePoint,
    reachedBottomCount: Long,
    splitterPointTimelineCount: MutableMap<ValuePoint, Long>,
  ): Long {
    var downBeam: ValuePoint? = position
    do {
      check(downBeam != null)
      downBeam = map.pointAt(downBeam.x, downBeam.y + 1)
    } while (downBeam != null && downBeam.value == ".")

    if (downBeam == null) {
      return 1
    }
    check(downBeam.value == "^")

    splitterPointTimelineCount[downBeam]?.let {
      return it
    }

    val leftBeam = map.pointAt(downBeam.x - 1, downBeam.y)
    val rightBeam = map.pointAt(downBeam.x + 1, downBeam.y)

    val timelineCountLeft = leftBeam?.let {
      processBeamDownPart2(map, it, reachedBottomCount, splitterPointTimelineCount)
    } ?: error("splitter at left border")
    val timelineCountRight = rightBeam?.let {
      processBeamDownPart2(map, it, reachedBottomCount, splitterPointTimelineCount)
    } ?: error("splitter at right border")

    (timelineCountLeft + timelineCountRight).let { timelineCount ->
      splitterPointTimelineCount[downBeam] = timelineCount
      return timelineCount
    }
  }

  fun part1(input: List<String>): Long {
    val map = MapOfPoints(input, "")
    val start = map.pointsWithValue("S").single()
    val beamStartingPoints = mutableSetOf<ValuePoint>()

    return processBeamDownPart1(map, start, 0, beamStartingPoints)
  }


  fun part2(input: List<String>): Long {
    val map = MapOfPoints(input, "")
    val start = map.pointsWithValue("S").single()
    val splitterPointCosts = mutableMapOf<ValuePoint, Long>()

    return processBeamDownPart2(map, start, 0, splitterPointCosts)
  }

  compareAndCheck(part1(readInput("Day07_test")), 21)
  compareAndCheck(part2(readInput("Day07_test")), 40)

  val input = readInput("Day07")
  part1(input).println()
  part2(input).println()
}

