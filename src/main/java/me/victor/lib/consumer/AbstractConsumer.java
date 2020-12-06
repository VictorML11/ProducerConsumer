package me.victor.lib.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public abstract class AbstractConsumer<T> implements Consumer<T> {

    private BlockingQueue<T> queue;

    public AbstractConsumer(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public void consume(){
        try {
            this.accept(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void consumeData(T t);

    @Override
    public void accept(T t) {
        this.consumeData(t);
    }
}
