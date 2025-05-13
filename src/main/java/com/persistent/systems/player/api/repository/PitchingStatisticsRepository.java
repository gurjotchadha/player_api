package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.statistics.PitchingStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchingStatisticsRepository extends JpaRepository<PitchingStatistics, Long> {
}
