package io.zgeeks.combinators;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.zgeeks.combinators.Predicates.equalTo;
import static io.zgeeks.combinators.Predicates.or;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class PredicatesTest {

    @Test
    public void testAnd() {

        List<Data> list = Stream.of(new Data("toto1", 1, false),
                new Data("toto2", 2, true),
                new Data("toto3", 3, true))
                .filter(or(equalTo(Data::getKey, 2),
                        equalTo(Data::getKey, 3)))
                .collect(Collectors.toList());

        assertEquals(asList(new Data("toto2", 2, true),
                new Data("toto3", 3, true)), list);

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return key == data.key &&
                    dump == data.dump &&
                    Objects.equals(toto, data.toto);
        }

        @Override
        public int hashCode() {

            return Objects.hash(toto, key, dump);
        }
    }
}
