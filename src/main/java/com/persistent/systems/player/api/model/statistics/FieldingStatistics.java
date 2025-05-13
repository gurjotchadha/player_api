package com.persistent.systems.player.api.model.statistics;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "fielding_statistics")
public class FieldingStatistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private int someRandomStatOne;
  private double someRandomStatTwo;
}
