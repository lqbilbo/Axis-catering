package com.threequick.catering.query.kds.stall.repositories;

import com.threequick.catering.infra.config.PersistenceInfrastructureConfig;
import com.threequick.catering.query.config.HsqlDbConfiguration;
import com.threequick.catering.query.kds.stall.StallView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceInfrastructureConfig.class, HsqlDbConfiguration.class})
public class StallRepositoryIntegrationTest {

    @Autowired
    private StallViewRepository stallRepository;

    @Test
    public void storeStallInRepository() {
        StallView stallView = new StallView();
        stallView.setAbility(1);
        stallView.setRequirements("烹饪");
        stallView.setRemark("档口1");
        stallView.setPoiId(2);
        stallView.setStallName("档口1");

        stallRepository.save(stallView);
    }
}
