# A rambling wander through Java Testing

_Disclaimer: I am in no way an expert, this whole project was just a playground for me while I was waiting on onboarding nonsense._

## Running

You can run all the _passing_ tests via `gradle clean check`.

Some tests will not run for this task:

- Tests which are intended to fail are exlcluded by JUnit Tags - you can run these using `gradle clean alltests`,
- The `pitest` tests are disabled by default, you can enable them by uncommenting the following line in `build.gradle`:
  ```
  //check.dependsOn 'pitest'
  ```

### Docker

You'll need a valid docker environment to run the `testcontainers` tests.

I'd recommend [`colima` if you're on a mac](https://github.com/abiosoft/colima).

You may also need to run this to make your colima setup accessible:
```shell
sudo ln -s $HOME/.colima/docker.sock /var/run/docker.sock
```

## Tools

### [JUnit 5](https://junit.org/junit5/)

Latest and greatest Java testing with lots of nifty features.

e.g.
- `@Nested` - divide big test classes up to give more structure,
- `@ParameterizedTest` - reduce duplicated test setup,
- `@DisplayName` - handy to make test output more readable,
- `@Tag` - nice way of classifying tests, you can plug this into your build tool to run categories of tests (or avoid running them).

Used throughout, but particularly [the `junit` package](./src/test/java/com/example/testing/junit).

### [Mockito](https://github.com/mockito/mockito)

Standard mocking library for easy test doubles.

See [the `mocking` package](./src/test/java/com/example/testing/mocking).

### [AssertJ](https://assertj.github.io/doc/)

More fluid assertions.

Used throughout, but particularly [the `assertj` package](./src/test/java/com/example/testing/assertj).

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

See [the `testcontainers` package](./src/test/java/com/example/testing/testcontainers) and also [the `db` package](./src/test/java/com/example/testing/db).

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
