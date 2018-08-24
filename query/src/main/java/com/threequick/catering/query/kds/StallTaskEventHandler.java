package com.threequick.catering.query.kds;

import com.threequick.catering.api.kds.stall.StallCreatedEvent;
import com.threequick.catering.api.kds.stall.task.StallTaskCreatedEvent;
import com.threequick.catering.query.kds.repositories.StallTaskViewRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("queryModel")
public class StallTaskEventHandler {
    private final StallTaskViewRepository stallTaskViewRepository;


    public StallTaskEventHandler(StallTaskViewRepository stallTaskViewRepository) {
        this.stallTaskViewRepository = stallTaskViewRepository;
    }

    @EventHandler
    public void on(StallTaskCreatedEvent event) {
        StallTaskView stallTaskView = new StallTaskView();

        stallTaskView.setIdentifier(event.getStallId().toString());
        stallTaskView.setPoiId(event.getPoiId());
        stallTaskView.setStallTaskName(event.getStallTaskName());
        stallTaskView.setRemark(event.getRemark());
        stallTaskView.setOrderId(event.getOrderId());

        stallTaskViewRepository.save(stallTaskView);
    }
}
