package com.threequick.catering.query.kds;

import com.threequick.catering.api.kds.stall.StallCreatedEvent;
import com.threequick.catering.query.kds.repositories.StallViewRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("queryModel")
public class StallEventHandler {

    private final StallViewRepository stallViewRepository;


    public StallEventHandler(StallViewRepository stallViewRepository) {
        this.stallViewRepository = stallViewRepository;
    }

    @EventHandler
    public void on(StallCreatedEvent event) {
        StallView stallView = new StallView();

        stallView.setIdentifier(event.getStallId().toString());
        stallView.setPoiId(event.getPoiId());
        stallView.setStallName(event.getStallName());
        stallView.setRemark(event.getRemark());
        stallView.setRequirements(event.getRequirements());
        stallView.setAbility(event.getAbility());

        stallViewRepository.save(stallView);
    }
}