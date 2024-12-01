package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenOneThreadWith50Then50() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread = new Thread(() -> IntStream.range(0, 50)
                .forEach(el -> count.increment()));
        thread.start();
        thread.join();
        assertThat(count.get()).isEqualTo(50);
    }

    @Test
    public void whenTwoThreadsWith50Then100() throws InterruptedException {
        CASCount count = new CASCount();
        Thread one = new Thread(() -> IntStream.range(0, 50)
                .forEach(el -> count.increment()));
        Thread two = new Thread(() -> IntStream.range(0, 50)
                .forEach(el -> count.increment()));
        one.start();
        two.start();
        one.join();
        two.join();
        assertThat(count.get()).isEqualTo(100);
    }
}