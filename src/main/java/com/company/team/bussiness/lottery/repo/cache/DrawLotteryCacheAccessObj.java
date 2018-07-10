package com.company.team.bussiness.lottery.repo.cache;

import com.company.team.bussiness.lottery.domain.aggregate.DrawLottery;

import java.util.concurrent.ConcurrentHashMap;

public class DrawLotteryCacheAccessObj {

    private final static int MAX_SIZE = 1000;

    private final static ConcurrentHashMap<Integer, DrawLottery> lotteryMap = new ConcurrentHashMap<>(MAX_SIZE);

    public DrawLottery get(int lotteryId) {
        return lotteryMap.get(lotteryId);
    }

    public void add(Integer lotteryId, DrawLottery drawLottery) {
        if (lotteryId != null)  lotteryMap.put(lotteryId, drawLottery);
    }
}
