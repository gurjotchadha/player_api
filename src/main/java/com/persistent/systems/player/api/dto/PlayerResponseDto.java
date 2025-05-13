package com.persistent.systems.player.api.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AdminPlayerResponseDto.class, name = "admin"),
    @JsonSubTypes.Type(value = UserPlayerResponseDto.class, name = "user")
})
public interface PlayerResponseDto {
}
