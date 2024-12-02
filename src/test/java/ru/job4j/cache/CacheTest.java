package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {

    @Test
    public void whenAddFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.id());
        assertThat(find.get().name())
                .isEqualTo("Base updated");
        assertThat(find.get().version()).isEqualTo(2);
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        var base = new Base(1,   "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.id());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }

    @Test
    public void whenUpdateFirst() throws OptimisticException {
        var cache = new Cache();
        assertThat(cache.update(new Base(1, "Base", 1))).isFalse();
    }

    @Test
    public void whenAddTheSameThenFalse() {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        assertThat(cache.add(base)).isFalse();
    }

    @Test
    public void whenSuccessfullyTwoUpdates() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "First update", 1));
        assertThat(cache.update(new Base(1, "Second update", 2))).isTrue();
        assertThat(cache.findById(1).get().name()).isEqualTo("Second update");
    }
}