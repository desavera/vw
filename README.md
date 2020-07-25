# vw

Challenge for VW Digital Solutoins - Mario de Sa Vera

VW digital solutions challenge - Mario de Sa Vera

Reasoning

REF: https://aws.amazon.com/pt/blogs/big-data/build-and-automate-a-serverless-data-lake-using-an-aws-glue-trigger-for-the-data-catalog-and-etl-jobs/

The challenge basically tries to promote a fast data scenario where moving windows aggregations would be automatically updated and available for consumption through AWS services.

For a development environment with NO COSTs from AWS it was suggested to use a local install of Spark so that the data transformations could be ran properly with no implication and easy to be extended as part of the technical interview.

AWS ETL

Storage

Batch
https://docs.aws.amazon.com/apigateway/latest/developerguide/integrating-api-with-aws-services-s3.html

Streaming

Indexing

REF : https://aws.amazon.com/pt/blogs/big-data/building-and-maintaining-an-amazon-s3-metadata-index-without-servers/

Seems like metadata query is somehow limited : https://aws.amazon.com/blogs/big-data/building-and-maintaining-an-amazon-s3-metadata-index-without-servers/
dramatically reduce the search space and improve performance for Metadata search
Because each listing can return at most 1000 keys, it may require many requests before finding the object
DynamoDB seems just like HBase


Aggregation

REF : https://github.com/aws-samples/aws-glue-samples/blob/master/examples/JoinAndRelationalize.scala

Lambdas

Deploy

REF : https://aws.amazon.com/cloudformation/resources/templates/?nc1=h_ls

Diagram

REF : https://app.cloudcraft.co/blueprint/0c4a5015-882d-4492-866e-47c0baa87f77

LOCAL SPARK ETL

Spark install

# docker pull bitnami/spark:3-debian-10
# docker run --name spark -v `pwd`/data:/opt/data bitnami/spark:3-debian-10

Load csv

val vwdf = spark.read.option("header","true").option("delimiter",",").option("inferSchema","true").format("csv").load("/opt/data/vwdataset-wrangled2.csv”)

Wrangling

val vwDFW = vwdf.withColumn("indexed_pk",concat(col("eq_site_limit"),lit("-"),col("policyID")))

Milestoning

val outpath = new java.io.File("/opt/data/milestone").getCanonicalPath

vwdf.repartition(col("county")).write.partitionBy("county").parquet(outpath)

val countyList = vwdf.select(col("county")).distinct

countyList.select("county").collect().map(_(0)).toSeq.foreach {county => printMaxAndSumFromCounty(county.toString)}


Avoiding this JOIN ...

https://stackoverflow.com/questions/46031893/aggregate-multiple-columns-in-spark-dataframe

