package com.coveo.challenge.search;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.miguelfonseca.completely.data.Indexable;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.AutocompleteEngine;
import com.miguelfonseca.completely.IndexAdapter;
import com.miguelfonseca.completely.text.analyze.tokenize.QGramTokenizer;
import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer;
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

@Configuration
public class SearchEngine {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SearchBean singletonBean() throws IOException {
        return new SearchBean();
    }

    public static class SearchBean implements Searcher {

        AutocompleteEngine<City> engine = null;

        public SearchBean() throws IOException {
            indexData();
        }

        public String getValue() {
            return "test";
        }

        @Override
        public void indexData() throws IOException {
            // load all the city data as records for indexing
            CsvParser csvParser = new CsvParser();
            ClassLoader classLoader = getClass().getClassLoader();
            List<City> cities = new ArrayList<>(
                    csvParser.readCities(classLoader.getResourceAsStream("data/cities_canada-usa.tsv"))
                            .values());

            this.engine = new AutocompleteEngine.Builder<City>()
                    .setIndex(new Adapter())
                    .setAnalyzers(new LowerCaseTransformer(), new WordTokenizer())
                    .build();

            for (City c : cities) {
                engine.add(c);
            }
        }

        @Override
        public Map<String, Object> getResults(SearcherParams params) {
            List<City> found = engine.search(params.q);
            Map<String, Object> results = new HashMap<>();
            Integer limit = 20;
            Integer total = 0;
            Integer page = params.page;
            results.put("page", page);
            List<City> filtered = new ArrayList<City>();
            for (int i = 0; i < found.size(); i++) {
                if (i >= limit) {
                    break;
                }
                City c = found.get(i);
                filtered.add(c);
            }
            if (total == 0) {
                total = filtered.size();
            }
            results.put("total", total);
            results.put("cities", filtered);
            return results;
        }

    }

}
