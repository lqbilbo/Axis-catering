package com.threequick.catering.query.kds;

import com.threequick.catering.api.kds.servery.ServeryCreatedEvent;
import com.threequick.catering.api.kds.stall.StallCreatedEvent;
import com.threequick.catering.query.kds.repositories.ServeryViewRepository;
import com.threequick.catering.query.kds.repositories.StallViewRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("queryModel")
public class ServeryEventHandler {

    private final ServeryViewRepository serveryViewRepository;


    public ServeryEventHandler(ServeryViewRepository serveryViewRepository) {
        this.serveryViewRepository = serveryViewRepository;
    }

    @EventHandler
    public void on(ServeryCreatedEvent event) {
        ServeryView serveryView = new ServeryView();

        serveryView.setIdentifier(event.getServeryId().toString());
        serveryView.setPoiId(event.getPoiId());
        serveryView.setServeryName(event.getServeryName());
        serveryView.setRemark(event.getRemark());
        serveryView.setOnline(event.getOnline());
        serveryView.setOffline(event.getOffline());

        serveryViewRepository.save(serveryView);
    }
}