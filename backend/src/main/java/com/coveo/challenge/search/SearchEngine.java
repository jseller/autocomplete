package com.coveo.challenge.search;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.miguelfonseca.completely.data.Indexable;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.AutocompleteEngine;
import com.miguelfonseca.completely.IndexAdapter;
import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer;
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;

import java.io.Console;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;


@Configuration
public class SearchEngine implements Searcher {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SearchBean singletonBean() {
        return new SearchBean();
    }

    public static class SearchBean {
        public SearchBean() {
            
            AutocompleteEngine<Record> engine = new AutocompleteEngine.Builder<Record>()
                    .setIndex(new Adapter())
                    .setAnalyzers(new LowerCaseTransformer(), new WordTokenizer())
                    .build();

                for (String country : Locale.getISOCountries())
                {
                    Locale locale = new Locale("", country);
                    engine.add(new Record(locale.getDisplayCountry()));
                }
        }
        
        public String getValue() {
            return "test";
        }
    }

    @Override
    public void indexData() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'indexData'");
    }

    @Override
    public Map<String, Object> getResults(SearcherParams params) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResults'");
    }

}


