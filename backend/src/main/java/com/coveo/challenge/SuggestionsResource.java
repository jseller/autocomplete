package com.coveo.challenge;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coveo.challenge.search.City;
import com.coveo.challenge.search.CsvParser;
import com.coveo.challenge.search.SearchFile;
import com.coveo.challenge.search.SearcherParams;
import com.coveo.challenge.search.SearchEngine.SearchBean;

@RestController
public class SuggestionsResource {
    @Autowired
    private CsvParser csvParser;

    @Autowired
    private SearchFile fileSearch;

    @Autowired
    private SearchBean indexSearch;

    private Predicate<City> buildFilter(String q, Double latitude, Double longitude) {
        Predicate<City> nameFilter = c -> c.name.contains(q);
        Predicate<City> latFilter = c -> Math.abs(c.latitude - latitude) < 10;
        Predicate<City> lonFilter = c -> Math.abs(c.longitude - longitude) < 20;
        if (latitude != null && longitude != null) {
            nameFilter = nameFilter.and(lonFilter).and(latFilter);
        } else if (latitude != null) {
            nameFilter = nameFilter.and(latFilter);
        }
        return nameFilter;
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/suggestions")
    public Map<String, Object> suggestions(@RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Integer page)
            throws Throwable {
        System.out.println(new Date() + " --- Entering suggestions endpoint parameters are: q=" + q + ", latitude="
                + String.valueOf(latitude) + ", longitude=" + String.valueOf(longitude));

        ClassLoader classLoader = getClass().getClassLoader();
        List<City> cities = new ArrayList<>(
                List.copyOf(csvParser.readCities(classLoader.getResourceAsStream("data/cities_canada-usa.tsv"))
                        .values()));

        Map<String, Object> results = new HashMap<>();
        try {
            fileSearch.indexData();
            SearcherParams params = new SearcherParams(q, latitude, longitude, page);
            Map<String, Object> results2 = fileSearch.getResults(params);
            System.out.println(results2);

        } catch (UnsupportedOperationException e) {
            System.out.println(e);
        }

        page = (page == null) ? 0 : page;
        Predicate<City> predicate = this.buildFilter(q, latitude, longitude);
        List<City> filtered = cities.stream().filter(predicate).skip(page).limit(5).toList();
        results.put("page", page);
        Integer total = Math.toIntExact(cities.stream().filter(predicate).count());
        results.put("total", total);
        results.put("cities", filtered);

        return results;

    }
}
