package com.coveo.challenge.search;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SearchFile implements Searcher {

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
