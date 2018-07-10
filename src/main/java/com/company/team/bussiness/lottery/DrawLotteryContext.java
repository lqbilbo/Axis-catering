package com.company.team.bussiness.lottery;

import com.company.team.bussiness.lottery.domain.entity.MtCityInfo;

public class DrawLotteryContext {

    private MtCityInfo mtCityInfo;

    private int gameScore;

    private int lat;

    private int lng;

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public MtCityInfo getMtCityInfo() {
        return mtCityInfo;
    }

    public void setMtCityInfo(MtCityInfo mtCityInfo) {
        this.mtCityInfo = mtCityInfo;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }
}
