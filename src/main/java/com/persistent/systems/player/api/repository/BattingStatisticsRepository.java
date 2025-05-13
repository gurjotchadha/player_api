package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.statistics.BattingStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattingStatisticsRepository extends JpaRepository<BattingStatistics, Long> {
}
