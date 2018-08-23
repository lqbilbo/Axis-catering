package com.threequick.catering.query.kds.repositories;

import com.threequick.catering.api.kds.servery.ServeryId;
import com.threequick.catering.query.kds.StallView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StallViewRepository extends JpaRepository<StallView, String> {

    List<StallView> findAllStallsByServeryId(ServeryId serveryId);
}
