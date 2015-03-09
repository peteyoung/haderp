# Haderp

## WordCount
####Source:
hadoop-mapreduce-examples-2.5.0-cdh5.3.0-sources

####Deps:

2002_sou.txt

####Run:

Put unzipped files from Deps into an hdfs folder called `wordcount_in`
```bash
hdfs dfs -fs "hdfs://namenode" -mkdir wordcount_in
stdbuf -i0 -o0 -e0 unzip -p acite75_99.zip | hdfs dfs -fs "hdfs://namenode" -put - wordcount_in/acite75_99.txt
stdbuf -i0 -o0 -e0 unzip -p apat63_99.zip | hdfs dfs -fs "hdfs://namenode" -put - wordcount_in/apat63_99.txt
```

Run WordCount
```bash
hadoop jar experimental-jobs-1.0.jar job.WordCount -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung wordcount_in wordcount_out
# or
HADOOP_CLASSPATH=~/src/haderp/build/classes/main/job hadoop WordCount -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung wordcount_in wordcount_out
```

Run WordCount2
```bash
hadoop jar experimental-jobs-1.0.jar job.WordCount2 -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung wordcount_in wordcount2_out
# or
HADOOP_CLASSPATH=~/src/haderp/build/classes/main/job hadoop WordCount2 -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung wordcount_in wordcount2_out
```

## Patent Jobs
####Source:
Hadoop in Action 2nd Ed. Listing 4.1 and Section 4.3

####Deps:

```bash
curl -O http://www.nber.org/patents/acite75_99.zip
curl -O http://www.nber.org/patents/apat63_99.zip
```

####Setup:

Put unzipped files from Deps into hdfs folders
```bash
hdfs dfs -fs "hdfs://namenode" -mkdir patent_in
stdbuf -i0 -o0 -e0 unzip -p apat63_99.zip | hdfs dfs -fs "hdfs://namenode" -put - patent_in/apat63_99.txt

hdfs dfs -fs "hdfs://namenode" -mkdir patent_cite_in
stdbuf -i0 -o0 -e0 unzip -p acite75_99.zip | hdfs dfs -fs "hdfs://namenode" -put - patent_in/acite75_99.txt
```

####Run:

PatentCiters
```bash
hadoop jar experimental-jobs-1.0.jar job.PatentCiters -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung patent_cite_in patent_cite_out
# or
HADOOP_CLASSPATH=~/src/haderp/build/classes/main/job hadoop PatentCiters -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung patent_cite_in patent_cite_out
```

PatentCitationCount
```bash
hadoop jar experimental-jobs-1.0.jar job.PatentCitationCount -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung patent_cite_in patent_count_out
# or
HADOOP_CLASSPATH=~/src/haderp/build/classes/main/job hadoop PatentCitationCount -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung patent_cite_in patent_count_out
```

## Setup dev environment
Since we're building against hadoop version 2.5.2, pull down the supported version of Java from Oracle. If you don't have an account at Oracle, you'll need to create one first.

1. Log into Oracle's site and download [this JDK installer](http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html#jdk-7u72-oth-JPR)
2. Install Java JDK from the downloaded package file
3. Add the following to your .bashrc
```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 1.7*)
```
4. ```sudo mkdir /opt/gradle```
5. ```cd /opt/gradle```
6. Download gradle from [here](http://gradle.org/downloads) to /opt/gradle. The version used here is 2.2.1
7. ```sudo unzip gradle-2.2.1-all.zip```
8. ```ln -s gradle-2.2.1/ current```
9. Add the following to your .bashrc
```bash
export GRADLE_HOME=/opt/gradle/current
PATH=$PATH:$GRADLE_HOME/bin
export PATH
```

## Setup a local hadoop instance
You'll need this to be able to run jobs against the cluster. It will give you the `hadoop` and `hdfs` commands at the command line.

1. Download the following tar files from cloudera
```bash
curl -O http://archive.cloudera.com/cdh5/cdh/5/avro-1.7.6-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/crunch-0.11.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/datafu-1.1.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/flume-ng-1.5.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.5.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/hbase-0.98.6-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/hbase-solr-1.5-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/hive-0.13.1-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/hue-3.7.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/kite-0.15.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/llama-1.0.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/mahout-0.9-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/oozie-4.0.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/parquet-1.5.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/parquet-format-2.1.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/pig-0.12.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/search-1.0.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/sentry-1.4.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/solr-4.4.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/spark-1.2.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/sqoop-1.4.5-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/sqoop2-1.99.4-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/whirr-0.9.0-cdh5.3.1.tar
curl -O http://archive.cloudera.com/cdh5/cdh/5/zookeeper-3.4.5-cdh5.3.1.tar
```
2. Extract cloudera hadoop into opt/
```bash
sudo mkdir -p /opt/cloudera/cdh5.3.1
sudo sh -c "ls *.tar | xargs -I{} tar xvf {} -C /opt/cloudera/cdh5.3.1"
```
3. Setup links
```bash
sudo -i
mkdir -p /opt/cloudera/current
ln -s /opt/cloudera/cdh5.3.1/hadoop-2.5.0-cdh5.3.1 /opt/cloudera/current/hadoop
ln -s /opt/cloudera/cdh5.3.1/hbase-0.98.6-cdh5.3.1 /opt/cloudera/current/hbase
ln -s /opt/cloudera/cdh5.3.1/hive-0.13.1-cdh5.3.1 /opt/cloudera/current/hive
ln -s /opt/cloudera/cdh5.3.1/zookeeper-3.4.5-cdh5.3.1 /opt/cloudera/current/zookeeper
ls -l /opt/cloudera/current/
```
4. Add the following to your .bash_rc
```bash
export HADOOP_HOME="/opt/cloudera/current/hadoop"
export HBASE_HOME="/opt/cloudera/current/hbase"
export HIVE_HOME="/opt/cloudera/current/hive"
export HCAT_HOME="/opt/cloudera/current/hive/hcatalog"
export PATH=${PATH}:${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin:${HBASE_HOME}/bin:${HIVE_HOME}/bin:${HCAT_HOME}/bin
```

## Building jobs jar
There is a gradle build file that will pull down hadoop dependencies and build the job classes and the jar file. Just cd into the local copy of this repo and run `gradle build`. You can clean the project with `gradle clean`

## Running jobs from the jar
The jobs in the jar file inherit from Configured and Tool which allows you to specify the NameServer with the command line switch `-fs`. You can also specify the user with a `-D` property like `-D hadoop.job.ugi=<user>`
```bash
hadoop jar experimental-jobs-1.0.jar job.PatentCiters -fs "hdfs://namenode" -D hadoop.job.ugi=peteyoung inputDir outputDir
```

## Running jobs from a class file
The class files will be located in `build/classes/main` under the project directory. Currently they are in the top level package.

1. `cd` into the `build/classes/main/job` directory
2. Run the job like so:
```bash
HADOOP_CLASSPATH=. hadoop PatentCiters -fs "hdfs://namenode:8020" -D hadoop.job.ugi=peteyoung inputDir outputDir
```

