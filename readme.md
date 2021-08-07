# Log Processor

This application is to read log entries from a file and store found log events into file-based HSQLDB in the working folder.
Under this project, the folder is named **output**, and database name is **testdb**, which is configurable in application.yaml file.

It requires one program argument : **log file path**

## Description

Log entries :  
Every line in the file is a JSON object containing the following event data:
* id - the unique event identifier
*  state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
*  timestamp - the timestamp of the event in milliseconds
   Application Server logs also have the following additional attributes:
*  type - type of log
*  host - hostname

Log Event:
*  Event id
*  Event duration
*  Type and Host if applicable
*  Alert (true if the event took longer than 4ms, otherwise false)

## Getting Started

### Dependencies

You could check db logs , properties script under output folder. Then there will be no dependencies.

Or If you would like to see entries in database, 
* please download and install hsql first https://sourceforge.net/projects/hsqldb/files/latest/download

* Then using db properties in application.yaml file to connect to HSQL
* run command to see entries stored in database  ``` select * from log_event; ```


### Executing program

To run the application, you can eiter 
* Download source codes and import to Intellij  as a gradle project
* build the project
* Select LogProcessorApplication class -> open run configuration 
* in Program arguments field, fill **log file absolute path**.

OR
* Download the source code
* Run `./gradlew assemble` under the root project directory in a command line window
* Under same directory, run `java -jar build/libs/log-processor-0.0.1-SNAPSHOT.jar "/Users/tmp/Downloads/log-processor/logFile.txt"`



## To run test

Either
* under the root project directory in a command line window, then run ``` ./gradlew test ```

OR
* in Intellij, right click /src/test/java, then select "run all tests"