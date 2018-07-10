package com.company.team.bussiness.lottery.repo.repository;

import com.company.team.bussiness.lottery.domain.aggregate.DrawLottery;
import com.company.team.bussiness.lottery.repo.cache.DrawLotteryCacheAccessObj;
import com.company.team.bussiness.lottery.repo.dao.AwardDao;
import com.company.team.bussiness.lottery.repo.dao.AwardPoolDao;

import javax.annotation.Resource;

public class DrawLotteryRepository {

    @Resource
    private AwardDao awardDao;

    @Resource
    private AwardPoolDao awardPoolDao;

    @Resource
    private DrawLotteryCacheAccessObj drawLotteryCacheAccessObj;

    public DrawLottery getDrawLotteryById(int lotteryId) {
        DrawLottery drawLottery = drawLotteryCacheAccessObj.get(lotteryId);
        if (drawLottery!=null) {
            return drawLottery;
        }
        drawLottery = getDrawLotteryFromDB(lotteryId);
        drawLotteryCacheAccessObj.add(lotteryId, drawLottery);
        return drawLottery;

    }

    private DrawLottery getDrawLotteryFromDB(int lotteryId) {
        DrawLottery drawLottery = new DrawLottery();
        drawLottery.setLotteryId(lotteryId);
        return drawLottery;
    }
}
