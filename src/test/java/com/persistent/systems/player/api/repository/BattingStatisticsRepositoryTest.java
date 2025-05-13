package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.statistics.BattingStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class BattingStatisticsRepositoryTest {

  @Autowired
  private BattingStatisticsRepository battingStatisticsRepository;

  @Test
  void testSaveAndFindById() {
    BattingStatistics stats = BattingStatistics.builder()
        .someRandomStatOne(42)
        .someRandomStatTwo(100)
        .build();

    BattingStatistics saved = battingStatisticsRepository.save(stats);

    Optional<BattingStatistics> found = battingStatisticsRepository.findById(saved.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getSomeRandomStatOne()).isEqualTo(42);
    assertThat(found.get().getSomeRandomStatTwo()).isEqualTo(100);
  }

}
