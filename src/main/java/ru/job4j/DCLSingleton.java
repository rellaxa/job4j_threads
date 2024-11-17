package ru.job4j;

public class DCLSingleton {

    private static volatile DCLSingleton instance;

    /**
     * Без volatile: возможна ситуация частичной инициализации объекта
     * В момент создания ссылки переменной на объект в 1 потоке.
     * А в другом потоке, уже попытка использования этого объекта
     * с неинциализированными полями приведет к некорректной работе с объектом.
     */
    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }

    private DCLSingleton() {
    }
}
