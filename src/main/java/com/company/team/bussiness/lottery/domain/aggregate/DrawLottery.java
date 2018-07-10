package com.company.team.bussiness.lottery.domain.aggregate;

import com.company.team.bussiness.lottery.DrawLotteryContext;
import com.company.team.bussiness.lottery.domain.entity.MtCityInfo;
import com.company.team.bussiness.lottery.domain.valobj.AwardPool;

import java.util.List;

public class DrawLottery {
    private  int lotteryId; //抽奖id
    private List<AwardPool> awardPools; //奖池列表

    public void setLotteryId(int lotteryId) {
        if (lotteryId<=0) {
            throw new IllegalArgumentException("非法的抽奖id");
        }
        this.lotteryId = lotteryId;
    }

    //根据抽奖入参context选择奖池
    public AwardPool chooseAwardPool(DrawLotteryContext context) {
        if (context.getMtCityInfo()!=null) {
            return chooseAwardPoolByCityInfo(awardPools, context.getMtCityInfo());
        } else {
            return chooseAwardPoolByScore(awardPools, context.getGameScore());
        }
    }

    private AwardPool chooseAwardPoolByScore(List<AwardPool> awardPools, int gameScore) {
        for (AwardPool awardPool : awardPools) {
            if (awardPool.matchedCity(gameScore)) {
                return awardPool;
            }
        }
        return null;
    }

    private AwardPool chooseAwardPoolByCityInfo(List<AwardPool> awardPools, MtCityInfo mtCityInfo) {
        for (AwardPool awardPool : awardPools) {
            if (awardPool.matchedCity(mtCityInfo.getCityId())) {
                return awardPool;
            }
        }
        return null;
    }

}
