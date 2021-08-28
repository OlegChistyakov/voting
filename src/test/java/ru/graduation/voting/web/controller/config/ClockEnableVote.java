package ru.graduation.voting.web.controller.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class ClockEnableVote {
    @Bean
    @Primary
    Clock clock() {
        return Clock.fixed(Instant.parse("2021-08-27T10:15:30.00Z"), ZoneId.of("UTC"));
    }
}