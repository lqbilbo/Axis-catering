package com.company.team.bussiness.lottery.facade;

import com.company.team.bussiness.lottery.DrawLotteryContext;
import com.company.team.bussiness.lottery.domain.entity.MtCityInfo;
import com.company.team.bussiness.lottery.model.LbsReq;
import com.company.team.bussiness.lottery.model.LbsResponse;
import com.company.team.bussiness.lottery.service.LbsService;

import javax.annotation.Resource;

public class UserCityInfoFacade {
    @Resource
    private LbsService lbsService;//外部用户城市信息RPC服务

    public MtCityInfo getMtCityInfo(DrawLotteryContext context) {
        LbsReq lbsReq = new LbsReq();
        lbsReq.setLat(context.getLat());
        lbsReq.setLng(context.getLng());

        LbsResponse resp = lbsService.getLbsCityInfo(lbsReq);
        return buildMtCityInfo(resp);
    }

    private MtCityInfo buildMtCityInfo(LbsResponse resp) {
        return new MtCityInfo();
    }
}
