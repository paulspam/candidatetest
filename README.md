## Introduction

The competition has passed and there are the start and finish logs 
with the participant ID and his start and finish time, respectively.

## Test task purpose

1. Using Parsing Logic, get information from tag_read_start. When repeating tags, use the first occurrence. (The start time has a “UTC” timezone!!!)
2. Using Parsing Logic, get information from tag_read_finish. When repeating tags, use the last occurrence.  (The finish time has a “Europe/Kiev” timezone!!!)
3. Define 10 participants who spent the least time in this competition.

## Requirements


1. Develop a spring boot application with Rest API using which we can do an HTTP request to get the competition result.
2. Log files must be in the project (“resources” folder) and you must read logs from those files.
3. Implementation  should be done with maximum use of Stream API
4. Implementation should be as concise as possible
5. Ready solution upload to Github

## Solution

Method __getTimesFromFile__ reads string lines from file, then parses it 
to participant ID and his time. If file does not exit it 
throws __NoSuchInputFileException__ with appropriate message. <br>
In case of duplicating ID, conflicts are resolved depending on the
value of the __firstOccurrence__ parameter

Method __getResults__ merges result times from start and finish times.
When repeating tags, for start times method uses the first occurrence,
for finish times - last occurrence.

Time zone difference is also taken into calculating.

Result is calculated as the difference between finish time and start time.
If there aren't matching finish results, finish time sets as start time.
Thus, incorrect data is filtered out by the condition *resultTime > 0*.

Names of data source files are taken from private variables
__startFile__ and __finishFile__

Time zones for result times are taken from private variables
__startFileTimeZone__  and __finishFileTimeZone__ <br>
Values for these private variables can be defined in
`application.properties` file.

Method __getTop10Results__ takes result times using __getResults__ method
and returns only Top 10 results.

Application has two endpoints: <br>
- `/top10results` - using this, you can do an `GET` HTTP request to get the top 10 competition results.
- `/results` - using this, you can do an `GET` HTTP request to get the all correct results.

You can test endpoints using __Swagger UI__ by this
URL: http://localhost:8080/swagger-ui.html
