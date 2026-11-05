# CS4013-Student-Records-Project

## Project Structure

All Java source code lives in the Maven source tree (`src/main/java`). There is no
separate `Code/` directory, and all CSV data (including mock/test data) lives in
`data/` — there is no separate `CSV/` directory.

```text
.
├── pom.xml                     # Configuration file of a Maven project
├── README.md
├── CRC/                        # CRC cards (documentation deliverable)
├── data/                       # CSV data files, including mock/test data
└── src
	├── main
	│   ├── java
	│   │   ├── app/            # Application entry point
	│   │   ├── model/          # Domain models and enums
	│   │   ├── presentation/   # Command-line interface and input
	│   │   ├── repository/     # Student data repositories
	│   │   └── service/        # QCA, award, and progression logic
	│   └── resources/          # Application resources
	└── test
		└── java
			├── model/
			├── presentation/
			├── repository/
			└── service/
```

## Build and Run

Requires JDK 17 and Maven.

```bash
mvn test            # compile and run the unit tests
mvn package         # build the executable JAR (main class: app.Main)
java -jar target/student-records-1.0-SNAPSHOT.jar
```