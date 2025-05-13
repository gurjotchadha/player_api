package com.persistent.systems.player.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPlayerResponseDto implements PlayerResponseDto {
  private String firstName;
  private String lastName;
  private Integer age;
}
