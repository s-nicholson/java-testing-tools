# A rambling wander through Java Testing

_Disclaimer: I am in no way an expert, this whole project was just a playground for me while I was waiting on onboarding nonsense._

## Tools

### [JUnit 5](https://junit.org/junit5/)

Latest and greatest Java testing with lots of nifty features.

### [Mockito](https://github.com/mockito/mockito)

Standard mocking library for easy test doubles.

See [the `mocking` package](./src/test/java/com/example/testing/mocking).

### [AssertJ](https://assertj.github.io/doc/)

More fluid assertions.

e.g.
```java
assertThat(x).isEqualTo(y);
assertThat(list).contains("a", "b", "c");
```

### [Awaitlity](https://github.com/awaitility/awaitility)

Making it easier to test async code.

See [the `async` package](./src/test/java/com/example/testing/async).

### [WireMock](https://wiremock.org/)

Mocking for network calls (also a good way to test things like json deserialisation that lives in annotations).

See [the `wiremock` package](./src/test/java/com/example/testing/wiremock).

### [Cucumber](https://github.com/cucumber/cucumber-jvm)

BDD testing, allows you to write executable spec which is readable for non-techies.

See [the `features` dir](./src/test/resources/features) for the feature files and [the `bdd` package](./src/test/java/com/example/testing/bdd) for the step definitions and test runner.

### [H2 DB](https://www.h2database.com/html/main.html)

In-memory DB which you can plug into your ORM to avoid needing a real one.

See [the `db` package](./src/test/java/com/example/testing/db).

### [TestContainers](https://testcontainers.com/)

Containers for your dependencies so you can run real services in your integration tests.

Also in [the `db` package](./src/test/java/com/example/testing/db) (but you can do much more with it).

### [JaCoCo](https://www.eclemma.org/jacoco/)

Code coverage stats and enforcing - fails the build if coverage is too low.

The generated reports will live here after you run `gradle clean build`:

- [Results](build/reports/tests/test/index.html)
- [Coverage](build/jacocoHtml/index.html)

See also some relevant config in `build.gradle`.

### [PITest](https://pitest.org/)

Mutation testing to get **_more_** confidence on your coverage.

The generated report will live [here](build/reports/pitest/index.html) after you run `gradle clean build`.

See [the `pitest` package](./src/test/java/com/example/testing/pitest) and some relevant config in `build.gradle`.
