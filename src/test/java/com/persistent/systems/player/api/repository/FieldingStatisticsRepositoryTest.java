package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.statistics.FieldingStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class FieldingStatisticsRepositoryTest {


  @Autowired
  private FieldingStatisticsRepository fieldingStatisticsRepository;

  @Test
  void testSaveAndFindById() {
    FieldingStatistics stats = FieldingStatistics.builder()
        .someRandomStatOne(42)
        .someRandomStatTwo(100)
        .build();

    FieldingStatistics saved = fieldingStatisticsRepository.save(stats);

    Optional<FieldingStatistics> found = fieldingStatisticsRepository.findById(saved.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getSomeRandomStatOne()).isEqualTo(42);
    assertThat(found.get().getSomeRandomStatTwo()).isEqualTo(100);
  }
}
