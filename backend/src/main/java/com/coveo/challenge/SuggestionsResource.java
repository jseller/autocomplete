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

import com.coveo.challenge.search.SearchFile;
import com.coveo.challenge.search.SearcherParams;
import com.coveo.challenge.search.SearchEngine.SearchBean;

@RestController
public class SuggestionsResource {

    @Autowired
    private SearchFile fileSearch;

    @Autowired
    private SearchBean indexSearch;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/suggestions", produces={"application/json; charset=UTF-8"})
    public Map<String, Object> suggestions(@RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Integer page)
            {

        System.out.println(new Date() + " --- Entering suggestions endpoint parameters are: q=" + q + ", latitude="
                + String.valueOf(latitude) + ", longitude=" + String.valueOf(longitude));
        Map<String, Object> results = new HashMap<>();
        SearcherParams params = new SearcherParams(q, latitude, longitude, page);
        results = fileSearch.getResults(params);
        System.out.println(results);
        return results;
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value="/search", produces={"application/json; charset=UTF-8"})
    public Map<String, Object> search(@RequestParam String q,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false) Integer page)
            {

        System.out.println(new Date() + " --- Entering search endpoint parameters are: q=" + q + ", latitude="
                + String.valueOf(latitude) + ", longitude=" + String.valueOf(longitude));
        Map<String, Object> results = new HashMap<>();
        SearcherParams params = new SearcherParams(q, latitude, longitude, page);
        results = indexSearch.getResults(params);
        System.out.println(results);
        return results;
    }


}
