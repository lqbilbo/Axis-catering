package com.threequick.catering.query.kds.repositories;

import com.threequick.catering.api.kds.servery.ServeryId;
import com.threequick.catering.query.kds.StallTaskView;
import com.threequick.catering.query.kds.StallView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StallTaskViewRepository extends JpaRepository<StallTaskView, String> {

}
