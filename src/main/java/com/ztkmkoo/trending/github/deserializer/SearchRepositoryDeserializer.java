package com.ztkmkoo.trending.github.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.ztkmkoo.trending.github.SearchRepository;

import java.io.IOException;

/**
 * Created by Ztkmkoo on 2019-09-16
 */
public class SearchRepositoryDeserializer extends JsonDeserializer<SearchRepository> {

    @Override
    public SearchRepository deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        final ObjectCodec objectCodec = p.getCodec();
        final JsonNode jsonNode = objectCodec.readTree(p);

        final Long id = jsonNode.get("id").asLong();
        final String name = jsonNode.get("name").asText();
        final String description = jsonNode.get("description").asText();

        if (id == null) {
            throw new IOException("id value is null");
        }

        final SearchRepository searchRepository = new SearchRepository();
        searchRepository.setId(id);
        searchRepository.setName(name);
        searchRepository.setDescription(description);

        return searchRepository;
    }
}
