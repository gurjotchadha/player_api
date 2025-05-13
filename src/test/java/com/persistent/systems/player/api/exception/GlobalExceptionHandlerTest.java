package com.persistent.systems.player.api.exception;

import com.persistent.systems.player.api.controller.PlayerController;
import com.persistent.systems.player.api.service.AiService;
import com.persistent.systems.player.api.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PlayerController.class) // Use real controller
@Import(GlobalExceptionHandler.class)
public class GlobalExceptionHandlerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PlayerService playerService; // Mock the PlayerService

  @MockBean
  private AiService aiService;

  @Test
  @WithMockUser
  void whenNoSuchElementException_thenReturnsNotFound() throws Exception {
    when(playerService.getPlayerById(anyLong(), anyBoolean()))
        .thenThrow(new NoSuchElementException("Player not found with id 1"));

    mockMvc.perform(get("/v1/players/{id}", 1L))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Requested resource not found: Player not found with id 1"));
  }

  @Test
  @WithMockUser
  void whenDataAccessException_thenReturnsInternalServerError() throws Exception {
    when(playerService.getPlayerById(anyLong(), anyBoolean()))
        .thenThrow(new DataAccessResourceFailureException("Some Test Issue"));

    mockMvc.perform(get("/v1/players/{id}", 1L))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("Database error: Some Test Issue"));
  }

  @Test
  @WithMockUser
  void whenGenericException_thenReturnsInternalServerError() throws Exception {
    when(playerService.getPlayerById(anyLong(), anyBoolean()))
        .thenThrow(new RuntimeException("Oops"));

    mockMvc.perform(get("/v1/players/{id}", 1L))
        .andExpect(status().isInternalServerError())
        .andExpect(content().string("An Unexpected error occurred: Oops"));
  }
}
