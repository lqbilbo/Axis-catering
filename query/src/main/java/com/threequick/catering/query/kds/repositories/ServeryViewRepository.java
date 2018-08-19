package com.threequick.catering.query.kds.repositories;

import com.threequick.catering.query.kds.ServeryView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServeryViewRepository extends JpaRepository<ServeryView, String> {

    ServeryView findByCookingInfo(String skuId, String cookingId);
}
