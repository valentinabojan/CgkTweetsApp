#Tweets Web Application

## Setup instructions for Ubuntu

1. **Install Cassandra**
    * Download DataStax Community Edition v2.2.5  as Tarball from [here] (http://www.planetcassandra.org/cassandra/) (We didn't chose the latest version, because the Spring Data API for Cassandra is not compatible with versions bigger than v2.*)
    * Unzip the archive in */opt* directory

2. **Install MongoDB**
    * Follow the instructions from [here](https://docs.mongodb.org/manual/tutorial/install-mongodb-on-ubuntu/)

3. **Other prerequisites**
    * Tomcat
    * Node.js
    * Bower

## Instructions for running the application from IntelliJ IDEA

**_Obs!_** To run the shell commands that we will present you have to be in the directory relative to the application root: *src/main/resources/scripts*

1. Start **_mongod_** process
2. Start **_cassandra_** process
    * run `./cassandra-start.sh` and let it run in a terminal
3. Clean Cassandra keyspace
    * run `./cassandra-clean.sh`
4. Populate Cassandra tables from the keyspace
    * run `./cassandra-create.sh`
5. If you want to run the application using Cassandra/Mongo for storing your data, you have to specify the Spring profile at runtime
    * `-D.spring.profiles.active=cassandra`
    * `-D.spring.profiles.active=mongo`