package ru.graduation.voting.util;

import ru.graduation.voting.model.Vote;
import ru.graduation.voting.to.RestaurantTo;
import ru.graduation.voting.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    private VoteUtil() {
    }

    public static VoteTo convert(Vote vote) {
        return new VoteTo(vote.getId(), vote.getLocalDate(), RestaurantTo.convert(vote.getRestaurant()));
    }

    public static List<VoteTo> convertList(List<Vote> votes) {
        return votes.stream().map(VoteUtil::convert).collect(Collectors.toList());
    }
}