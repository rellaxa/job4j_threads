package ru.job4j;

public final class Cache {

    private static Cache cache;

    /**
     * Операция getInstance() не атомарна, потому что
     * зависит от начального состояни объекта Cache
     * Мы проверяем состояние cache, и в зависимости от
     * результата проверки создаем или возвращаем уже
     * созданный объект.
     */
    public static Cache getInstance() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}