package pattern

import scala.collection.immutable.{SortedMap, TreeMap}

/**
 * template
 */
object GradeReporter {

  def main(args: Array[String]): Unit = {
    val sampleGrades = Vector(5.0, 4.0, 4.4, 2.2, 3.3, 3.5)

    val fullReporter = reporterTemplate(fullGradeConverter, printHistogram)
    fullReporter(sampleGrades)

    println("----")

    val plusMinusReporter = reporterTemplate(plusMinusGradeConverter, printAllGrades)
    plusMinusReporter(sampleGrades)
  }

  def reporterTemplate(numToLetter: Double => String, printGradeReport: Seq[String] => Unit): Seq[Double] => Unit = (grades: Seq[Double]) => printGradeReport(grades.map(numToLetter))

  def fullGradeConverter(grade: Double) =
    if grade <= 5.0 && grade > 4.0 then "A"
    else if grade <= 4.0 && grade > 3.0 then "B"
    else if grade <= 3.0 && grade > 2.0 then "C"
    else if grade <= 2.0 && grade > 0.0 then "D"
    else "N/A"

  def printHistogram(grades: Seq[String]) = {
    val grouped = grades.groupBy(identity)
    val counts = grouped.map((kv) => (kv._1, kv._2.size)).toSeq.sorted
    for (count <- counts) {
      val stars = "*" * count._2
      println("%s: %s".format(count._1, stars))
    }
  }

  def plusMinusGradeConverter(grade: Double) =
    if grade <= 5.0 && grade > 4.7 then "A"
    else if grade <= 4.7 && grade > 4.3 then "A-"
    else if grade <= 4.3 && grade > 4.0 then "B+"
    else if grade <= 4.0 && grade > 3.7 then "B"
    else if grade <= 3.7 && grade > 3.3 then "B-"
    else if grade <= 3.3 && grade > 3.0 then "C+"
    else if grade <= 3.0 && grade > 2.7 then "C"
    else if grade <= 2.7 && grade > 2.3 then "C-"
    else if grade <= 2.3 && grade > 0.0 then "D"
    else if grade == 0.0 then "F"
    else "N/A"

  def printAllGrades(grades: Seq[String]) =
    for (grade <- grades) println("Grade is: " + grade)
}