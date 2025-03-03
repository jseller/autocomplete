package com.coveo.challenge.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.coveo.challenge.search.City;
import com.coveo.challenge.search.CsvParser;

@Component
@Scope("singleton")
public class SearchFile implements Searcher {

    List<City> cities = null;

    public SearchFile() throws IOException {
        this.indexData();
    }

    private Predicate<City> buildFilter(String q, Double latitude, Double longitude) {
        Predicate<City> nameFilter = c -> c.name.contains(q);
        Predicate<City> latFilter = c -> Math.abs(c.latitude - latitude) < 2;
        Predicate<City> lonFilter = c -> Math.abs(c.longitude - longitude) < 2;
        if (latitude != null && longitude != null) {
            nameFilter = nameFilter.and(lonFilter).and(latFilter);
        } else if (latitude != null) {
            nameFilter = nameFilter.and(latFilter);
        }
        return nameFilter;
    }

    @Override
    public void indexData() throws IOException {
        CsvParser csvParser = new CsvParser();
        ClassLoader classLoader = getClass().getClassLoader();
        this.cities = new ArrayList<>(csvParser.readCities(classLoader.getResourceAsStream("data/cities_canada-usa.tsv"))
                            .values());
        System.out.println("File data initialized " + cities.size());
    }

    @Override
    public Map<String, Object> getResults(SearcherParams params) {
        Predicate<City> predicate = this.buildFilter(params.q, params.latitude, params.longitude);
        Map<String, Object> results = new HashMap<>();
        Integer limit = 50;
        Integer total = 0;
        Integer page = params.page;
        if (page > 0) {
            total = Math.toIntExact(this.cities.stream().filter(predicate).count());
        }
        results.put("page", page);
        List<City> filtered = this.cities.stream().filter(predicate).skip(page).limit(limit).collect(Collectors.toList());
        if (total == 0) {
            total = filtered.size();
        }
        results.put("total", total);
        results.put("cities", filtered);
        return results;
    }

}
