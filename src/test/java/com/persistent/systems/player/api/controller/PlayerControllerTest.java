package com.persistent.systems.player.api.controller;

import com.persistent.systems.player.api.dto.AdminPlayerResponseDto;
import com.persistent.systems.player.api.dto.PlayerResponseDto;
import com.persistent.systems.player.api.dto.UserPlayerResponseDto;
import com.persistent.systems.player.api.service.AiService;
import com.persistent.systems.player.api.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PlayerService playerService;
  @MockBean
  private AiService aiService;


  @Test
  @WithMockUser
  void getAllPlayers_isAdmin() throws Exception {
    List<AdminPlayerResponseDto> adminPlayerResponseDtos =
        Collections.singletonList(
            AdminPlayerResponseDto.builder()
                .firstName("Admin")
                .lastName("User")
                .age(30)
                .build());

    Mockito
        .<List<? extends PlayerResponseDto>>when(playerService.getAllPlayers(true))
        .thenReturn(adminPlayerResponseDtos);

    mockMvc.perform(get("/v1/players?isAdmin=true")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Admin"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("User"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(30));
  }

  @Test
  @WithMockUser
  void getAllPlayers_isAdmin_false() throws Exception {
    List<UserPlayerResponseDto> userPlayerResponseDtos =
        Collections.singletonList(
            UserPlayerResponseDto.builder()
                .firstName("Admin")
                .age(30)
                .build());

    Mockito
        .<List<? extends PlayerResponseDto>>when(playerService.getAllPlayers(false))
        .thenReturn(userPlayerResponseDtos);

    mockMvc.perform(get("/v1/players?isAdmin=false")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Admin"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(30));
  }

  @Test
  @WithMockUser
  void getPlayerById_isAdmin() throws Exception {
    long id = 1L;
    AdminPlayerResponseDto adminPlayerResponseDto =
        AdminPlayerResponseDto.builder().firstName("Admin").lastName("User").age(30).build();

    Mockito
        .when(playerService.getPlayerById(id,true))
        .thenReturn(adminPlayerResponseDto);

    mockMvc.perform(get("/v1/players/{id}?isAdmin=true", id)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser
  void getPlayersById_isAdmin_false() throws Exception {
    long id = 1L;
    UserPlayerResponseDto userPlayerResponseDto =
        UserPlayerResponseDto.builder().firstName("User").age(30).build();

    Mockito
        .when(playerService.getPlayerById(id,false))
        .thenReturn(userPlayerResponseDto);

    mockMvc.perform(get("/v1/players/{id}?isAdmin=false", id)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("User"));
  }
}
