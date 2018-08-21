package com.threequick.catering.query.kds.repositories;

import com.threequick.catering.api.kds.servery.ServeryId;
import com.threequick.catering.query.kds.ServeryView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServeryViewRepository extends JpaRepository<ServeryView, String> {

    ServeryView findByCookingInfo(String skuId, String cookingId);

    List<ServeryView> findAllStallsByServeryId(ServeryId serveryId);
}
