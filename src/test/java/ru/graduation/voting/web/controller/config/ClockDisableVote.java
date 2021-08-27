package ru.graduation.voting.web.controller.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class ClockDisableVote {
    @Bean
    @Primary
    Clock clock() {
        System.out.println("PUK");
        return Clock.fixed(Instant.parse("2021-08-27T11:00:30.00Z"), ZoneId.of("UTC"));
    }
}