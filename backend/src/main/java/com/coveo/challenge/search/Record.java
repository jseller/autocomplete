package com.coveo.challenge.search;

import java.util.Arrays;
import java.util.List;

import com.miguelfonseca.completely.data.Indexable;

public class Record implements Indexable {
        private final String name;

        public Record(String name)
        {
            this.name = name;
        }

        @Override
        public List<String> getFields()
        {
            return Arrays.asList(name);
        }

        public String getName()
        {
            return name;
        }
}

