package com.company.team.bussiness.lottery.domain.valobj;

import com.company.team.bussiness.lottery.domain.entity.Award;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AwardPool {

    private String cityIds;
    private String scores;
    private int userGroupType;
    private List<Award> awards;

    public boolean matchedCity(int cityId) {
        if (cityId == 11 || cityId == 17) {
            return true;
        }
        return false;
    }

    public boolean matchedScore(int score) {
        if (score < 60) {
            return false;
        }
        return true;
    }

    //根据概率选择奖池
    public Award randomGetAward() {
        int sumOfProbablity = 0;
        for (Award award : awards) {
            sumOfProbablity += award.getAwardProbablity();
        }
        int randomNumber = ThreadLocalRandom.current().nextInt(sumOfProbablity);
        int range = 0;
        for (Award award : awards) {
            range += award.getAwardProbablity();
            if (randomNumber<range) {
                return award;
            }
        }
        return null;
    }

}
