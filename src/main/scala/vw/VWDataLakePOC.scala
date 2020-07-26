/*
 * VW - MAN Data Lake challenge
 * Mario de Sa Vera
 */
package vw


import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{concat, lit, col, max};

object VWDataLakePOC {

  def main(args: Array[String]) {

    // create Spark context with Spark configuration
    val spark = SparkSession.builder.appName("VW Data Lake POC").getOrCreate()

    // the CSV input file
    val inputFile = args(0)

    // loads the initial RAW Data Frame
    val initdf = spark.read.option("header","true").option("delimiter",",").option("inferSchema","true").format("csv").load(inputFile)

    // Wrangling/Cleansing should go here ... just adding a indexing new column for now ...
    val vwdf = initdf.withColumn("indexed_pk",concat(col("eq_site_limit"),lit("-"),col("policyID")))

    // Milestoning by county
    val outpath = new java.io.File("/opt/data/milestone").getCanonicalPath
    vwdf.repartition(col("county")).write.mode("append").partitionBy("county").parquet(outpath)

    // loop through partitions and create the report for each one...
    val countyList = vwdf.select(col("county")).distinct
    countyList.select("county").collect().map(_(0)).toSeq.foreach {county => printMaxAndSumFromCounty(spark,county.toString)}

    spark.stop()

  }

  def printMaxAndSumFromCounty(spark:SparkSession,countyName:String) = {

    import spark.implicits._

    val countyDF =spark.read
                  .option("header","true")
                  .option("delimiter",",")
                  .option("inferSchema","true")
                  .load(s"/opt/data/milestone/county=$countyName")

    val totalVal = countyDF.count
    val maxVal  = countyDF.agg(max("indexed_pk")).head.toString.replaceAll("\\[|\\]", "")

    println(s"$countyName-$totalVal-$maxVal")
  }
}
