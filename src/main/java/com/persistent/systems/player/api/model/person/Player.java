package com.persistent.systems.player.api.model.person;

import com.persistent.systems.player.api.model.Team;
import com.persistent.systems.player.api.model.statistics.BattingStatistics;
import com.persistent.systems.player.api.model.statistics.FieldingStatistics;
import com.persistent.systems.player.api.model.statistics.PitchingStatistics;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "players")
public class Player extends Person {
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "team_id")
  private Team team;

  @ManyToMany
  @JoinTable(
      name = "player_managers",
      joinColumns = @JoinColumn(name = "player_id"),
      inverseJoinColumns = @JoinColumn(name = "manager_id")
  )
  private List<Manager> managers;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "batting_statistics_id")
  private BattingStatistics battingStats;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "fielding_statistics_id")
  private FieldingStatistics fieldingStats;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "pitching_statistics_id")
  private PitchingStatistics pitchingStats;
}
