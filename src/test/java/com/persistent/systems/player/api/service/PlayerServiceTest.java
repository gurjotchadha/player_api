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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {

  @Mock
  private PlayerRepository playerRepository;

  @Mock
  private ManagerRepository managerRepository;

  @InjectMocks
  private PlayerService playerService;

  private Player testPlayer;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    Team team = Team.builder().name("teamName").city("cityName").build();
    Manager manager = Manager.builder().firstName("managerFirstName").lastName("managerLastName").age(40).managedPlayers(Collections.emptyList()).build();
    BattingStatistics batting = BattingStatistics.builder().someRandomStatOne(1).someRandomStatTwo(2).build();
    FieldingStatistics fielding = FieldingStatistics.builder().someRandomStatOne(3).someRandomStatTwo(4).build();
    PitchingStatistics pitching = PitchingStatistics.builder().someRandomStatOne(5).someRandomStatTwo(6).build();

    testPlayer =
        Player.builder()
            .id(1L)
            .firstName("firstName")
            .lastName("lastName")
            .age(20)
            .managers(List.of(manager))
            .team(team)
            .battingStats(batting)
            .fieldingStats(fielding)
            .pitchingStats(pitching)
            .build();

  }

  @Test
  void testGetAllPlayers_isAdmin() {
    when(playerRepository.findAll()).thenReturn(List.of(testPlayer));

    List<? extends PlayerResponseDto> result = playerService.getAllPlayers(true);

    assertEquals(1, result.size());
    assertTrue(result.get(0) instanceof AdminPlayerResponseDto);
    AdminPlayerResponseDto adminDto = (AdminPlayerResponseDto) result.get(0);
    assertEquals("firstName", adminDto.getFirstName());
    assertEquals("lastName", adminDto.getLastName());
  }

  @Test
  void testGetAllPlayers_isAdmin_false() {
    when(playerRepository.findAll()).thenReturn(List.of(testPlayer));

    List<? extends PlayerResponseDto> result = playerService.getAllPlayers(false);

    assertEquals(1, result.size());
    assertTrue(result.get(0) instanceof UserPlayerResponseDto);
    UserPlayerResponseDto userDto = (UserPlayerResponseDto) result.get(0);
    assertEquals("firstName", userDto.getFirstName());
    assertEquals(20, userDto.getAge());
  }

  @Test
  void testGetPlayerById_isAdmin() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));

    PlayerResponseDto response = playerService.getPlayerById(1L, true);

    assertTrue(response instanceof AdminPlayerResponseDto);
    AdminPlayerResponseDto dto = (AdminPlayerResponseDto) response;
    assertEquals("firstName", dto.getFirstName());
    assertEquals("lastName", dto.getLastName());
    assertEquals(20, dto.getAge());
  }

  @Test
  void testGetPlayerById_isAdmin_false() {
    when(playerRepository.findById(1L)).thenReturn(Optional.of(testPlayer));

    PlayerResponseDto response = playerService.getPlayerById(1L, false);

    assertTrue(response instanceof UserPlayerResponseDto);
    UserPlayerResponseDto dto = (UserPlayerResponseDto) response;
    assertEquals("firstName", dto.getFirstName());
    assertEquals(20, dto.getAge());
  }

  @Test
  void testGetPlayerById_notFound() {
    when(playerRepository.findById(99L)).thenReturn(Optional.empty());

    NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
        playerService.getPlayerById(99L, true)
    );

    assertEquals("No data found with player id : 99", exception.getMessage());
  }
}
