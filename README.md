# A rambling wander through Java Testing

_Disclaimer: I am in no way an expert, this whole project was just a playground for me while I was waiting on onboarding nonsense._

## Tools

### JUnit 5

Latest and greatest Java testing with lots of nifty features.

### Mockito

Standard mocking library for easy test doubles.

### AssertJ

More fluid assertions.

e.g.
```java
assertThat(x).isEqualTo(y);
assertThat(list).contains("a", "b", "c");
```

### Awaitlity

Making it easier to test async code.

### WireMock

Mocking for network calls (also a good way to test things like json deserialisation that lives in annotations).

### Cucumber

BDD testing, allows you to write executable spec which is readable for non-techies.

### H2 DB

In-memory DB which you can plug into your ORM to avoid needing a real one.

### TestContainers

Containers for your dependencies so you can run real services in your integration tests.

### JaCoCo

Code coverage stats and enforcing - fails the build if coverage is too low.

Reports:

- [Results](build/reports/tests/test/index.html)
- [Coverage](build/jacocoHtml/index.html)

### PITest

Mutation testing to get **_more_** confidence on your coverage.

- [Report](build/reports/pitest/index.html)
