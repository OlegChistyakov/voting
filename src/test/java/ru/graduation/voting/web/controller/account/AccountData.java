package ru.graduation.voting.web.controller.account;

import ru.graduation.voting.MatcherFactory;
import ru.graduation.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static ru.graduation.voting.web.controller.open.OpenTestData.rest1;
import static ru.graduation.voting.web.controller.open.OpenTestData.rest2;

public class AccountData {
    public static final MatcherFactory.Matcher<Vote> MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant.menu", "user.registered");

    public static final Vote vote1 = new Vote(100_015, LocalDate.now().minusDays(1), null, rest1);
    public static final Vote vote2 = new Vote(100_016, LocalDate.now().minusDays(10), null, rest2);
    public static final Vote vote3 = new Vote(100_017, LocalDate.now(), null, rest1);

    public static final List<Vote> votesUser1 =List.of(vote1, vote2);

    public static Vote getReVoting() {
        return new Vote(100_017, LocalDate.now(), null, rest2);
    }
    public static Vote getFirstVote() {
        return new Vote(100_019, LocalDate.now(), null, rest1);
    }

}
