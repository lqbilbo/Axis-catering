package com.threequick.catering.query.kds.stall.repositories;

import com.threequick.catering.api.kds.stall.StallCreatedEvent;
import com.threequick.catering.api.kds.stall.StallId;
import com.threequick.catering.query.kds.StallEventHandler;
import com.threequick.catering.query.kds.StallView;
import com.threequick.catering.query.kds.repositories.StallViewRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StallRepositoryIntegrationTest {

    private final StallViewRepository stallRepository = Mockito.mock(StallViewRepository.class);

    private StallEventHandler testSubject;

    @Before
    public void setUp() {
        testSubject = new StallEventHandler(stallRepository);
    }

    @Test
    public void storeStallInRepository() {
        StallId expectedStallId = new StallId();
        long expectedPoiId = 2L;
        String expectedStallName = "stallName";
        String expectedRemark = "remark";
        String expectedRequirements = "requirements";
        int expectedAbility = 10;

        StallCreatedEvent testEvent = new StallCreatedEvent(expectedStallId, expectedPoiId, expectedStallName, expectedRemark, expectedRequirements, expectedAbility
        );

        testSubject.on(testEvent);

        ArgumentCaptor<StallView> stallViewCaptor = ArgumentCaptor.forClass(StallView.class);

        verify(stallRepository).save(stallViewCaptor.capture());

        StallView result = stallViewCaptor.getValue();
        assertNotNull(result);
        assertEquals(expectedStallId.toString(), result.getIdentifier());
        assertEquals(expectedPoiId, result.getPoiId());
        assertEquals(expectedStallName, result.getStallName());
        assertEquals(expectedRemark, result.getRemark());
        assertEquals(expectedRequirements, result.getRequirements());
        assertEquals(expectedAbility, result.getAbility());
    }
}
