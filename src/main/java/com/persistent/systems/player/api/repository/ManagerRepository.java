package com.persistent.systems.player.api.repository;

import com.persistent.systems.player.api.model.person.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
