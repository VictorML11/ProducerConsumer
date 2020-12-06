package me.victor.lib.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public abstract class ScheduledProducer<T> extends AbstractProducer<T> {

    private int producers;
    private ScheduledExecutorService scheduledExecutor;

    public ScheduledProducer(BlockingQueue<T> queue, int producers) {
        super(queue);
        this.producers = producers;
        scheduledExecutor = Executors.newScheduledThreadPool(producers);
    }


    public void startAll(int startDelay, int period, TimeUnit timeUnit) {
        IntStream.range(0, producers).forEach(i -> {
            scheduledExecutor.scheduleAtFixedRate(() -> {
                this.produce();
            }, startDelay, period, timeUnit);
        });
    }

    public void stopAll() {
        this.scheduledExecutor.shutdown();
    }

}
