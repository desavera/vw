/*
 * VW - MAN Data Lake challenge
 * Mario de Sa Vera
 */


def printMaxAndSumFromCounty(countyName:String) = {

  import org.apache.spark.sql.functions.{avg, broadcast, col, max};
  val countyDF =spark.read
                .option("header","true")
                .option("delimiter",",")
                .option("inferSchema","true")
                .load(s"/opt/data/milestonew/county=$countyName")

  val totalVal = countyDF.count
  val maxVal  = countyDF.agg(max("indexed_pk")).head.toString.replaceAll("\\[|\\]", "")

  println(s"$countyName-$totalVal-$maxVal")
}
