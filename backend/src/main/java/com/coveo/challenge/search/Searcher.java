package com.coveo.challenge.search;

import java.io.IOException;
import java.util.Map;

public interface Searcher {
    
    void indexData() throws IOException;

    Map<String, Object> getResults(SearcherParams params);

}
