package com.github.zgeeks.combinators;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.github.zgeeks.combinators.Predicates.equalTo;
import static com.github.zgeeks.combinators.Predicates.or;
import static java.util.stream.Collectors.toList;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PredicatesTest {

    @Test
    public void testOr() {
        List<Data> list = Stream
            .of(
                new Data("toto1", 1, false),
                new Data("toto2", 2, true),
                new Data("toto3", 3, true)
            )
            .filter(
                or(
                    equalTo(Data::getKey, 2),
                    equalTo(Data::getKey, 3)
                )
            )
            .collect(toList());

        assertEquals(
            asList(
                new Data("toto2", 2, true),
                new Data("toto3", 3, true)
            ),
            list
        );
    }

    private final class Data {
        final String toto;
        final int key;
        final boolean dump;

        private Data(String toto, int key, boolean dump) {
            this.toto = toto;
            this.key = key;
            this.dump = dump;
        }

        public String getToto() {
            return toto;
        }

        public int getKey() {
            return key;
        }

        public boolean isDump() {
            return dump;
        }
    }
}
