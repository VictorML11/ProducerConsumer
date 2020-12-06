package me.victor.lib.producer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

public abstract class AbstractProducer<T> implements Supplier<T> {

    private BlockingQueue<T> queue;

    public AbstractProducer(BlockingQueue<T> queue) {
        this.queue = queue;
    }

    public void produce(){
        T data = this.get();
        try {
            this.queue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract T produceData();

    @Override
    public T get() {
        return this.produceData();
    }
}
