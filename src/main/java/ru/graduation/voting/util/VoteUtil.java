package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {
    public static VoteTo convert(Vote vote) {
        return new VoteTo(vote.getId(), vote.getLocalDate(), RestaurantUtil.convert(vote.getRestaurant()));
    }

    public static List<VoteTo> convertList(List<Vote> votes) {
        return votes.stream().map(VoteUtil::convert).collect(Collectors.toList());
    }
}