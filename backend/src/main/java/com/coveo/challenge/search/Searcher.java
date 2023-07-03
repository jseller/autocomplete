package com.coveo.challenge.search;

import java.util.Map;

public interface Searcher {
    
    void indexData();

    Map<String, Object> getResults(SearcherParams params);

}
