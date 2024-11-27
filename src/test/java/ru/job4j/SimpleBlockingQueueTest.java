package ru.job4j;

import org.junit.jupiter.api.Test;
import ru.job4j.buffer.SimpleBlockingQueue;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    private Thread produce(SimpleBlockingQueue<Integer> queue, int size) {
        return new Thread(() -> {
            try {
                for (int i = 0; i < size; i++) {
                    queue.offer(i);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private Thread consume(SimpleBlockingQueue<Integer> queue, int size) {
        return new Thread(() -> {
            try {
                for (int i = 0; i < size; i++) {
                    queue.poll();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Test
    public void whenProducerAndConsumerCorrect() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = produce(queue, 5);
        Thread consumer = consume(queue, 4);

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    public void whenBlockProducer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        Thread producer = produce(queue, 5);
        Thread consumer = consume(queue, 4);

        producer.start();
        Thread.sleep(1000);
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo(4);
    }

    @Test
    public void whenBlockConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(4);
        Thread producer = produce(queue, 4);
        Thread consumer = consume(queue, 3);

        consumer.start();
        Thread.sleep(1000);
        producer.start();
        consumer.join();
        producer.join();
        assertThat(queue.poll()).isEqualTo(3);
    }
}