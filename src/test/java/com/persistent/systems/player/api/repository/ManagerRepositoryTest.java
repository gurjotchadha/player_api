package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.person.Manager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManagerRepositoryTest {

  @Autowired
  private ManagerRepository managerRepository;

  @Test
  void testSaveAndFindById() {
    Manager manager = Manager.builder()
        .firstName("Alice")
        .lastName("Smith")
        .age(40)
        .build();

    Manager saved = managerRepository.save(manager);
    Optional<Manager> found = managerRepository.findById(saved.getId());

    assertThat(found).isPresent();
    assertThat(found.get().getFirstName()).isEqualTo("Alice");
    assertThat(found.get().getLastName()).isEqualTo("Smith");
    assertThat(found.get().getAge()).isEqualTo(40);
  }
}
