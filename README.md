# Handicap

A command-line tool for calculating golf handicaps, written in plain Java against a MySQL course-ratings database.

It supports two workflows:

1. **Calculate Handicap** — enter your last 5 rounds (course, tees, gender, score) and get back a USGA-style handicap index, computed from each course's rating and slope.
2. **Calculate Course Handicap** — enter an existing handicap index plus a course/tees, and get back your playing handicap adjusted for that course's slope and par.

If a course name doesn't match anything in the database, the tool falls back to a Levenshtein-distance search and suggests the closest matches.

## Requirements

- Java 21+
- A MySQL server
- The bundled MySQL Connector/J driver (`lib/mysql-connector-j-9.3.0.jar`)

## Database setup

The app expects a `course_info` database with a `courseinfo` table shaped like this:

```sql
CREATE DATABASE IF NOT EXISTS course_info;

CREATE TABLE course_info.courseinfo (
    course_name    VARCHAR(255) NOT NULL,
    Tees           VARCHAR(255) NOT NULL,
    Mens           BOOLEAN NOT NULL,
    course_rating  DOUBLE NOT NULL,
    slope_rating   INT NOT NULL,
    par            INT NOT NULL
);
```

Populate it with the courses/tees you want to look up.

Connection details are read from environment variables — there are no credentials in the source:

| Variable              | Required | Default                                        |
|-----------------------|----------|-------------------------------------------------|
| `HANDICAP_DB_URL`      | No       | `jdbc:mysql://localhost:3306/course_info`       |
| `HANDICAP_DB_USER`     | Yes      | —                                                |
| `HANDICAP_DB_PASSWORD` | Yes      | —                                                |

## Build and run

```bash
javac -cp lib/mysql-connector-j-9.3.0.jar -d out src/*.java

# Windows
set HANDICAP_DB_USER=youruser
set HANDICAP_DB_PASSWORD=yourpassword
java -cp "out;lib/mysql-connector-j-9.3.0.jar" Main

# macOS / Linux
export HANDICAP_DB_USER=youruser
export HANDICAP_DB_PASSWORD=yourpassword
java -cp "out:lib/mysql-connector-j-9.3.0.jar" Main
```

## Project structure

| File                                                  | Purpose                                                    |
|--------------------------------------------------------|-------------------------------------------------------------|
| [Main.java](src/Main.java)                             | CLI menu and entry point                                     |
| [UserInfo.java](src/UserInfo.java)                      | Collects and validates round input from the player           |
| [Database.java](src/Database.java)                      | Looks up course rating, slope, par and tee data over JDBC     |
| [Handicap.java](src/Handicap.java)                      | Handicap index and playing handicap math                     |
| [LevDistance.java](src/LevDistance.java)                | Fuzzy course-name matching for typo recovery                  |
| [CourseInfo.java](src/CourseInfo.java) / [ICourseInfo.java](src/ICourseInfo.java) | Value object for a course's rating/slope/par |
| [CourseLDistancePair.java](src/CourseLDistancePair.java) | Pairs a course name with its edit distance to the query      |

## Known limitations

- No automated tests yet.
- No build tool (Maven/Gradle) — dependency is a checked-in jar.
