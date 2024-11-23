package ru.job4j.threads;

public class Singleton {

    private static Singleton instance;

    private Singleton() {
    }

    /*
    Происходит состояние гонки, потому что создание объекта -
    операция не атомарна и состоит из 3 стадий:
        1. выделение памяти под объект,
        2. присвоение ссылки объекту
        3. Инициализация полей объекта
     При использовании метода несколькими тредами может
     наступить ситуация. Когда на этапе присвоении ссылки
     1 нитью, другая уже попробует воспользоваться объектом
     с неинциализированными полями.
     */
    public Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
