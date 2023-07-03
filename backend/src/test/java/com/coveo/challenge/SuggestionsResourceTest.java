/**
 * Copyright (c) 2011 - 2019, Coveo Solutions Inc.
 */
package com.coveo.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.coveo.challenge.search.City;
import com.coveo.challenge.search.CsvParser;
import com.coveo.challenge.search.SearchEngine.SearchBean;
import com.coveo.challenge.search.SearchFile;

public class SuggestionsResourceTest
{
    SuggestionsResource suggestionsResource = new SuggestionsResource();

    @BeforeEach                                         
    void setUp() {
        ReflectionTestUtils.setField(suggestionsResource, "csvParser", new CsvParser());
        ReflectionTestUtils.setField(suggestionsResource, "indexSearch", new SearchBean());
        ReflectionTestUtils.setField(suggestionsResource, "fileSearch", new SearchFile());
    }
    
    @Test
    public void testSuggestionEndpoint() throws Throwable
    {
        Map<String, Object> results = new HashMap<>();
        results.put("cities", new ArrayList<>());
        results.put("total", 0);
        results.put("page", 0);
        Assertions.assertEquals(results, suggestionsResource.suggestions("test", null, null, null));
    }

    @Test
    public void testSuggestionEndpoint2() throws Throwable
    {
        Map<String, Object> res = suggestionsResource.suggestions("Qué", null, null, null);
        List<City> cities = (List<City>) res.get("cities");
        City c = cities.get(0);
        assertEquals(c.name, "Québec");
        assertEquals(c.id, 6325494);
    }


    @Test
    public void testSuggestionEndpoint4() throws Throwable
    {
        Map<String, Object> a = suggestionsResource.suggestions("Qué", 43.0, -23.2, null);
        Map<String, Object> b = suggestionsResource.suggestions("Qué", 43.0, -23.2, null);

        Assertions.assertEquals(b, a);
    }
}
