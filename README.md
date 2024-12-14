# Maastricht Public Transport Routing

This project aims to optimize public transport routing within Maastricht using the General Transit Feed Specification (GTFS) dataset. The focus is on creating an efficient routing algorithm that utilizes a well-structured relational database to enhance the user experience in navigating the city’s bus network.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Database Setup](#setup-database)
  - [Installation](#installation)
- [Usage](#usage)
- [Running the Tests](#running-the-tests)
  - [Unit Tests](#unit-tests)
  - [Code Coverage](#code-coverage)
- [Built With](#built-with)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

Java JDK 11 or later
Maven 3.6 or later
Docker
A suitable IDE like IntelliJ IDEA or Eclipse

### Setup Database
1. Run docker compose from project root directory:
```bash 
$ docker compose up -d
```

2. Get container name:
```bash
$ docker ps
```

3. Copy files to the container:
```bash
$ docker cp ./resources/gtfs <container-name>:/
$ docker cp ./resources/CompleteZipCodes.txt <container-name>:/
$ docker cp ./create_table.sql <container-name>:/
```

4. Access the container:
```bash
$ docker exec -it <container-name> /bin/bash
```

5. Access mysql as root (root password can be found in docker compose file):
```bash
$ mysql -u root -p
```

6. Set the local infile variable in mysql command line:
```bash
mysql> SET GLOBAL local_infile=1;
```

7. Exit mysql server:
```bash
mysql> exit
```

8. Connect to the server with local-infile system variable:
```bash
$ mysql --local-infile=1 -u root -p
```

9. Run script to create and fill tables:
```bash
mysql> source create_table.sql
```


### Installation

A step-by-step series of examples that tell you how to get a development environment running:

1. Clone the repository:
```bash
   git clone https://gitlab.maastrichtuniversity.nl/bcs_group14_2024/group14.git .
```
2. Install Maven dependencies:
```bash
    mvn install
```
3. Compile the project:
```bash
    mvn compile
```

## Database Configuration Setup

To configure the database settings for the application, please update the database properties file located at:

```bash
src/main/resources/org/group14/database.properties
```

For guidance on the format and available configuration options, refer to the example file provided at:

```bash
src/main/resources/org/group14/example.database.properties
```

## Usage
```bash
mvn exec:java -D"exec.mainClass"="org.group14.App"
```

## Running the Tests

### Unit Tests
```bash
mvn test
```
### Code Coverage
```bash
mvn jacoco:report
```
This will generate a report in `target/site/jacoco/index.html`
