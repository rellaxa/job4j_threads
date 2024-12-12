package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelIndexSearchTest {

    @Test
    public void whenIntegerArrayWithLengthGraterThan10() {
        Integer value = 100;
        Integer[] array = new Integer[1000];
        IntStream.range(0, 1000).forEach(el -> array[el] = el);
        int index = ParallelIndexSearch.search(array, value);
        assertThat(index).isEqualTo(100);
    }

    @Test
    public void whenStringArrayWithLength12() {
        String value = "y";
        String[] array = new String[]{"a", "b", "c", "d", "e", "g", "f", "o", "y", "p", "y", "s"};
        int index = ParallelIndexSearch.search(array, value);
        assertThat(index).isEqualTo(8);
    }

    @Test
    public void whenIntegerArrayWithLengthLessThan10() {
        Integer value = 15;
        Integer[] array = new Integer[]{3, -5, 15, 21, 7};
        int index = ParallelIndexSearch.search(array, value);
        assertThat(index).isEqualTo(2);
    }

    @Test
    public void whenNoValueInArray() {
        Integer value = 10;
        Integer[] array = new Integer[]{3, -5, 15, 21, 7};
        int index = ParallelIndexSearch.search(array, value);
        assertThat(index).isEqualTo(-1);
    }
}