package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.statistics.PitchingStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PitchingStatisticsRepositoryTest {

  @Autowired
  private PitchingStatisticsRepository pitchingStatisticsRepository;

  @Test
  void testSaveAndFindById() {
    PitchingStatistics stats = PitchingStatistics.builder()
        .someRandomStatOne(42)
        .someRandomStatTwo(100)
        .build();

    PitchingStatistics saved = pitchingStatisticsRepository.save(stats);

    Optional<PitchingStatistics> found = pitchingStatisticsRepository.findById(saved.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getSomeRandomStatOne()).isEqualTo(42);
    assertThat(found.get().getSomeRandomStatTwo()).isEqualTo(100);
  }
}
