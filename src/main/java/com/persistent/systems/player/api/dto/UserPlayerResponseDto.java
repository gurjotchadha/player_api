package com.persistent.systems.player.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPlayerResponseDto implements PlayerResponseDto {
  private String firstName;
  private Integer age;
}
