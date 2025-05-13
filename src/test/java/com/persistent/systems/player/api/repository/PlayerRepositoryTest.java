package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.person.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PlayerRepositoryTest {

  @Autowired
  private PlayerRepository playerRepository;

  @Test
  void testSaveAndFindById() {
    Player player = Player.builder()
        .firstName("Test")
        .lastName("Player")
        .age(28)
        .build();

    Player saved = playerRepository.save(player);
    Optional<Player> found = playerRepository.findById(saved.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getFirstName()).isEqualTo("Test");
    assertThat(found.get().getLastName()).isEqualTo("Player");
    assertThat(found.get().getAge()).isEqualTo(28);
  }

  @Test
  void testDeleteById() {
    Player player = Player.builder()
        .firstName("Delete")
        .lastName("Me")
        .age(30)
        .build();

    Player saved = playerRepository.save(player);
    playerRepository.deleteById(saved.getId());

    Optional<Player> deleted = playerRepository.findById(saved.getId());
    assertThat(deleted).isNotPresent();
  }

  @Test
  void testFindAll() {
    playerRepository.save(Player.builder().firstName("P1").lastName("One").age(24).build());
    playerRepository.save(Player.builder().firstName("P2").lastName("Two").age(26).build());

    var players = playerRepository.findAll();

    assertThat(players).hasSize(2);
  }
}
