# VW Data Lake POC

VW digital solutions challenge - Mario de Sa Vera

## Reasoning

REF: https://aws.amazon.com/pt/blogs/big-data/build-and-automate-a-serverless-data-lake-using-an-aws-glue-trigger-for-the-data-catalog-and-etl-jobs/

The challenge basically tries to promote a fast data scenario where moving windows aggregations would be automatically updated and available for consumption through AWS services.

For a development environment with NO COSTs from AWS it was suggested to use a local install of Spark so that the data transformations could be ran properly with no implication and easy to be extended as part of the technical interview.

As a summary we can say this POC is a demo on how to avoid this kind of SQL joins :

https://stackoverflow.com/questions/46031893/aggregate-multiple-columns-in-spark-dataframe

## AWS ETL

Storage

https://docs.aws.amazon.com/apigateway/latest/developerguide/integrating-api-with-aws-services-s3.html

Indexing

REF : https://aws.amazon.com/pt/blogs/big-data/building-and-maintaining-an-amazon-s3-metadata-index-without-servers/

Seems like metadata query is somehow limited : https://aws.amazon.com/blogs/big-data/building-and-maintaining-an-amazon-s3-metadata-index-without-servers/
dramatically reduce the search space and improve performance for Metadata search
Because each listing can return at most 1000 keys, it may require many requests before finding the object

NOTE : DynamoDB seems just like HBase


Aggregation

REF : https://github.com/aws-samples/aws-glue-samples/blob/master/examples/JoinAndRelationalize.scala

Lambdas

Deploy

REF : https://aws.amazon.com/cloudformation/resources/templates/?nc1=h_ls

NOTE : the deployment seems to be out of the box with Cloud Craft but needs to be tested...

Diagrams

REF : https://app.cloudcraft.co/blueprint/0c4a5015-882d-4492-866e-47c0baa87f77

## LOCAL SPARK ETL

Spark install

docker pull bitnami/spark:3-debian-10
docker run --name spark -v `pwd`/data:/opt/data bitnami/spark:3-debian-10
docker exec -it spark /bin/bash

NOTE : the image was modified so that Spark logging level is WARN and the output can be seen properly.

Source code 

https://github.com/desavera/vw

Building the Scala App 

sbt package


Running Spark App

docker run --name spark -v `pwd`/data:/opt/data spark3-info
docker exec -it spark spark-submit --class vw.VWDataLakePOC --master local[4] /opt/data/vw-data-lake-poc_2.12-1.0.jar [VW CSV INPUTFILE PATH MAPPED TO LOCAL DIR/data]

## Results

The processing was quite reasonable and the LOCAL SPARK ETL was successfully ran. We can see the output of :

COUNTYID - TOTAL NUM OF POLICIES - MAX EQ SITE LIMIT VALUE - POLICYID OF MAX

as we trigger 
