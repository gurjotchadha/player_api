package com.persistent.systems.player.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiService {

  private final Random random = new Random();
  private final ChatClient chatClient;


  public String generateNickName(String country) {

    ChatOptions chatOptions = ChatOptions.builder().temperature(0.7).maxTokens(20).build();
    String[] userPrompts = {
        String.format("Generate a single nickname for a BaseBall player from the country %s.", country),
        String.format("Generate a single nickname for a sports player from %s. The nickname should be short, catchy, and suitable for a sports context.", country),
        String.format("Suggest a single nickname for an athlete representing %s. Make it sound cool and energetic.", country),
        String.format("Give me a single nickname for a famous sports personality from %s.", country),
        String.format("Generate a single nickname for a legendary player from %s.", country),
        String.format("Suggest a single nickname inspired by a famous athlete from %s.", country)
    };

    String[] systemPrompts = {
        "You are a helpful assistant that generates creative nicknames.  Your responses should be very concise.",
        "You are a sports commentator known for creating catchy nicknames.  Your responses should be very concise.",
        "You are a nickname generator for athletes. Your responses should be very concise."
    };

    final int promptVariation = random.nextInt(6) +1;
    log.debug("Setting prompt variation to : "+promptVariation);

    int promptIndex = (promptVariation - 1) % userPrompts.length;
    String userPrompt = userPrompts[promptIndex];
    String systemPrompt = systemPrompts[promptIndex % systemPrompts.length]; //rotate through system prompts

    ChatResponse chatResponse = chatClient.prompt().system(systemPrompt).user(userPrompt).options(chatOptions).call().chatResponse();
    return chatResponse.getResult().getOutput().getText();
  }
}
