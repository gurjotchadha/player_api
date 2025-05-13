package com.persistent.systems.player.api.model.person;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "managers")
public class Manager extends Person {
  private String someManagerData;

  @ManyToMany(mappedBy = "managers")
  private List<Player> managedPlayers;
}
