package ru.graduation.voting.util;

import lombok.experimental.UtilityClass;
import ru.graduation.voting.model.Vote;
import ru.graduation.voting.to.VoteTo;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VoteUtil {
    public static VoteTo convertToDTO(Vote vote) {
        return new VoteTo(vote.getId(), vote.getLocalDate(), RestaurantUtil.convertToDTO(vote.getRestaurant()));
    }

    public static List<VoteTo> convertToListDTO(List<Vote> votes) {
        return votes.stream().map(VoteUtil::convertToDTO).collect(Collectors.toList());
    }
}