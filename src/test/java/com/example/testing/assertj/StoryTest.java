package com.example.testing.assertj;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StoryTest {
    @Nested
    class WithoutAssertJ {
        @Test
        @Tag("failing")
        void one_by_one_failure() {
            var story = Story.builder()
                    .name("Test Story")
                    .description("Test Description")
                    .build();

            assertEquals("Test story", story.getName());
            assertEquals("Test description", story.getDescription());
        }
    }

    @Nested
    class WithAssertJ {
        /**
         * SoftAssertions allows you to get feedback on several assertions rather than
         * just the first one to fail.
         */
        @Test
        @Tag("failing")
        void softly_assert() {
            var story = Story.builder()
                    .name("Test Story")
                    .description("Test Description")
                    .build();

            var softly = new SoftAssertions();
            softly.assertThat(story.getName()).isEqualTo("Test story");
            softly.assertThat(story.getDescription()).isEqualTo("Test description");
            softly.assertAll();
        }

        /**
         * AssertJ asserions can be chained, making your tests more readable.
         */
        @Test
        void chained_asserts() {
            var story = Story.builder()
                    .name("Test")
                    .acceptanceCriteria(List.of(
                            "It should do the good thing",
                            "It should not do the bad thing"
                    ))
                    .build();

            assertThat(story.getAcceptanceCriteria())
                    .hasSize(2)
                    .contains("It should do the good thing",
                            "It should not do the bad thing");
        }

        @Test
        void custom_assertion() {
            var story = Story.builder()
                    .name("ABC-12345: Implement Feature")
                    .size(Story.Size.COLOSSAL)
                    .build();

            // You can mix and match custom assertions with built-ins.
            StoryAssert.assertThat(story)
                    .matches(s -> s.getSize().equals(Story.Size.COLOSSAL))
                    .hasJiraTicket();
        }

        /**
         * Custom assertions can be created to give names to common assertions,
         * making your tests more coherent.
         */
        static class StoryAssert extends AbstractAssert<StoryAssert, Story> {
            protected StoryAssert(Story actual) {
                super(actual, StoryAssert.class);
            }

            public static StoryAssert assertThat(Story actual) {
                return new StoryAssert(actual);
            }

            public StoryAssert hasJiraTicket() {
                isNotNull();
                if (!actual.getName().matches("[A-Z]+-\\d+[\s:]?\\s.+?")) {
                    failWithMessage("Expected story to contain ticket number");
                }
                return this;
            }
        }
    }
}