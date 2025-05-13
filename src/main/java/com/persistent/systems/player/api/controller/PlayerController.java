package com.persistent.systems.player.api.controller;

import com.persistent.systems.player.api.dto.PlayerResponseDto;
import com.persistent.systems.player.api.service.AiService;
import com.persistent.systems.player.api.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
@RequiredArgsConstructor
public class PlayerController {

  private final PlayerService playerService;
  private final AiService aiService;

  @GetMapping
  public ResponseEntity<List<? extends PlayerResponseDto>> getAllPlayers(
      @RequestParam(defaultValue = "false") boolean isAdmin) {
    List<? extends PlayerResponseDto> playerResponseDtos = playerService.getAllPlayers(isAdmin);
    return ResponseEntity.status(HttpStatus.OK).body(playerResponseDtos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<? extends PlayerResponseDto> getPlayerById(
      @PathVariable Long id, @RequestParam(defaultValue = "false") boolean isAdmin) {
    PlayerResponseDto playerDto = playerService.getPlayerById(id, isAdmin);
    return ResponseEntity.status(HttpStatus.OK).body(playerDto);
  }

  @GetMapping("/ai/nickname")
  public ResponseEntity<String> generateNickName(@RequestParam String country) {
    String response = aiService.generateNickName(country);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
