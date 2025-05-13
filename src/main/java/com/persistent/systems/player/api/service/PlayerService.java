package com.persistent.systems.player.api.service;

import com.persistent.systems.player.api.dto.AdminPlayerResponseDto;
import com.persistent.systems.player.api.dto.PlayerResponseDto;
import com.persistent.systems.player.api.dto.UserPlayerResponseDto;
import com.persistent.systems.player.api.model.Team;
import com.persistent.systems.player.api.model.person.Manager;
import com.persistent.systems.player.api.model.person.Player;
import com.persistent.systems.player.api.model.statistics.BattingStatistics;
import com.persistent.systems.player.api.model.statistics.FieldingStatistics;
import com.persistent.systems.player.api.model.statistics.PitchingStatistics;
import com.persistent.systems.player.api.repository.ManagerRepository;
import com.persistent.systems.player.api.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {

  private final PlayerRepository playerRepository;
  private final ManagerRepository managerRepository;

  @Transactional
  @PostConstruct
  public void initializeData() {
    //Loading sample data in case the DB is empty
    if (playerRepository.count() == 0) {
      Manager managerOne = managerRepository.save(Manager.builder().firstName("First").lastName("Manager").age(30).build());
      Manager managerTwo = managerRepository.save(Manager.builder().firstName("Second").lastName("Manager").age(40).build());
      Manager managerThree = managerRepository.save(Manager.builder().firstName("Third").lastName("Manager").age(50).build());

      Team teamOne = Team.builder().city("toronto").name("Toronto Blue Jays").build();
      Team teamTwo = Team.builder().city("test").name("Testers").build();

      BattingStatistics playerOneBatting = BattingStatistics.builder().someRandomStatOne(10).someRandomStatTwo(20).build();
      FieldingStatistics playerOneFielding = FieldingStatistics.builder().someRandomStatOne(100).someRandomStatTwo(200).build();
      PitchingStatistics playerOnePitching = PitchingStatistics.builder().someRandomStatOne(1000).someRandomStatTwo(2000).build();

      BattingStatistics playerTwoBatting = BattingStatistics.builder().someRandomStatOne(11).someRandomStatTwo(21).build();
      FieldingStatistics playerTwoFielding = FieldingStatistics.builder().someRandomStatOne(101).someRandomStatTwo(201).build();


      Player playerOne = playerRepository.save(Player.builder().firstName("P1").lastName("One").age(25).managers(List.of(managerOne, managerTwo)).team(teamOne).battingStats(playerOneBatting).fieldingStats(playerOneFielding).pitchingStats(playerOnePitching).build());
      Player playerTwo = playerRepository.save(Player.builder().firstName("P2").lastName("Two").age(24).managers(List.of(managerThree)).team(teamTwo).battingStats(playerTwoBatting).fieldingStats(playerTwoFielding).pitchingStats(null).build());
      log.info("Player One ID : " + playerOne.getId() + " and Player Two ID : " + playerTwo.getId());
    }
  }

  public List<? extends PlayerResponseDto> getAllPlayers(boolean isAdmin) {
    List<Player> players = playerRepository.findAll();
    return players.stream()
        .map(player -> mapToPlayerResponseDto(player, isAdmin))
        .toList();
  }

  public PlayerResponseDto getPlayerById(Long id, boolean isAdmin) {
    Optional<Player> playerOptional = playerRepository.findById(id);
    Player player = playerOptional.orElseThrow(() -> new NoSuchElementException("No data found with player id : " + id));

    return mapToPlayerResponseDto(player, isAdmin);
  }

  private PlayerResponseDto mapToPlayerResponseDto(Player player, boolean isAdmin) {
    if (isAdmin) {
      return AdminPlayerResponseDto.builder()
          .firstName(player.getFirstName())
          .lastName(player.getLastName())
          .age(player.getAge())
          .build();
    } else {
      return UserPlayerResponseDto.builder()
          .firstName(player.getFirstName())
          .age(player.getAge())
          .build();
    }
  }
}
