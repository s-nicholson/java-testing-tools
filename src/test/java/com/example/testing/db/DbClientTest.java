package com.example.testing.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class DbClientTest {
    /**
     * H@ in-memory DB for speedy tests against a basic SQL DB.
     * Doesn't support everything your real DB does, so you may not be able to
     * test everything.
     */
    @Nested
    class WithH2 {
        @Test
        void can_fetch_users() {
            var initScriptPsth = getClass().getResource("/data.sql").getPath();
            var dbClient = new DbClient(
                    "jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM '%s'".formatted(initScriptPsth),
                    "sa",
                    "password");

            var users = dbClient.getUsers();

            assertThat(users).isEqualTo(List.of(
                    new DbClient.User(1, "a"),
                    new DbClient.User(2, "b"),
                    new DbClient.User(3, "c")
            ));
        }
    }

    /**
     * Using a real database in docker to test our DB code.
     * Slower and has some issues with certain CI setups, but useful if you need to
     * test against the real thing.
     */
    @Nested
    class WithTestContainers {
        private static JdbcDatabaseContainer postgreSQLContainer;
        @BeforeAll
        static void setup() {
            postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.8")
                    .withDatabaseName("testsdb")
                    .withUsername("sa")
                    .withPassword("password")
                    .withInitScript("data.sql");
            postgreSQLContainer.start();
        }

        @Test
        void can_fetch_users() {
            var dbClient = new DbClient(
                    postgreSQLContainer.getJdbcUrl(),
                    postgreSQLContainer.getUsername(),
                    postgreSQLContainer.getPassword());

            var users = dbClient.getUsers();

            assertThat(users).isEqualTo(List.of(
                    new DbClient.User(1, "a"),
                    new DbClient.User(2, "b"),
                    new DbClient.User(3, "c")
            ));
        }
    }
}
