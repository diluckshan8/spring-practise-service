package com.spring.practise.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonObjectMapper {

  private final ObjectMapper mapper = new ObjectMapper();
  public static <T> T mapStringToClass(Class<T> clazz, String value)
      throws JsonProcessingException {
    return mapper.readValue(value,clazz);
  }

  public static <T> JsonNode mapToJSONNode(String value)
      throws JsonProcessingException {
    return mapper.readTree(value);
  }

  public static List<String> mapStringToTypeReference(TypeReference<List<String>> typeReference, String string)
      throws JsonProcessingException {
    return mapper.readValue(string,typeReference);
  }
}
