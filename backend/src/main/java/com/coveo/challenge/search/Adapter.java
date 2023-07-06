package com.coveo.challenge.search;

import java.util.Collection;

import com.miguelfonseca.completely.IndexAdapter;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;

public class Adapter implements IndexAdapter<City>
    {
        private FuzzyIndex<City> index = new PatriciaTrie<>();

        @Override
        public Collection<ScoredObject<City>> get(String token)
        {
            // Set threshold according to the token length
            double threshold = Math.log(Math.max(token.length() - 1, 3));
            return index.getAny(new EditDistanceAutomaton(token, threshold));
        }

        @Override
        public boolean put(String token, City value)
        {
            return index.put(token, value);
        }

        @Override
        public boolean remove(City value)
        {
            return index.remove(value);
        }
    }

