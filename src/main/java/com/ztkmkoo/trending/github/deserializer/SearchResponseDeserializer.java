package com.ztkmkoo.trending.github.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ztkmkoo.trending.github.SearchRepository;
import com.ztkmkoo.trending.github.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ztkmkoo on 2019-09-16
 */
public class SearchResponseDeserializer extends JsonDeserializer<SearchResponse> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public SearchResponseDeserializer() {
        final SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(SearchRepository.class, new SearchRepositoryDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    @Override
    public SearchResponse deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        final ObjectCodec objectCodec = p.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(p);

        final Long totalCount = jsonNode.get("total_count").asLong();
        final Boolean incompleteResults = jsonNode.get("incomplete_results").asBoolean();
        final List<SearchRepository> repositoryList = objectMapper.convertValue(jsonNode.get("items"), new TypeReference<ArrayList<SearchRepository>>(){});

        final SearchResponse searchResponse = new SearchResponse();
        searchResponse.setTotalCount(totalCount);
        searchResponse.setIncompleteResults(incompleteResults);
        searchResponse.setRepositoryList(repositoryList);

        return searchResponse;
    }
}
