#Tweets Web Application

## Setup instructions for Ubuntu

1. **Install Cassandra**
    * Download DataStax Community Edition v2.2.5  as Tarball from [here] (http://www.planetcassandra.org/cassandra/) (We didn't chose the latest version, because the Spring Data API for Cassandra is not compatible with versions bigger than v2.*)
    * Unzip the archive in */opt* directory

2. **Install MongoDB**
    * Download the archive suitable for your OS distribution from [here](https://www.mongodb.org/downloads#production)
    * Unzip the archive in */opt* directory
    * Rename the directory into */mongodb-linux*

3. **Other prerequisites**
    * Tomcat
    * Node.js
    * Bower

## Instructions for running the application from IntelliJ IDEA

**_Obs!_** To run the shell commands that we will present you have to be in the directory relative to the application root: *src/main/resources/scripts*

1. Start **_mongod_** process
    * run `./mongo-start.sh` and let it run in a terminal
2. **_Clean_** MongoDB collections
    * run `./mongo-clean.sh`
3. **_Populate_** MongoDB collections
    * run `./cassandra-create.sh`
4. Start **_cassandra_** process
    * run `./cassandra-start.sh` and let it run in a terminal
5. **_Clean_** Cassandra keyspace
    * run `./cassandra-clean.sh`
6. **_Populate_** Cassandra tables from the keyspace
    * run `./cassandra-create.sh`
7. If you want to run the application using Cassandra/Mongo for storing your data, you have to specify the **_Spring profile_** at runtime
    * `-D.spring.profiles.active=cassandra`
    * `-D.spring.profiles.active=mongo`