package me.victor.lib.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public abstract class ScheduledConsumer<T> extends AbstractConsumer<T> {

    private int consumers;
    private ScheduledExecutorService scheduledExecutor;

    public ScheduledConsumer(BlockingQueue<T> queue, int consumers) {
        super(queue);
        this.consumers = consumers;
        scheduledExecutor = Executors.newScheduledThreadPool(this.consumers);
    }

    public void startAll(int startDelay, int period, TimeUnit timeUnit){
        IntStream.range(0, consumers).forEach(i -> {
            scheduledExecutor.scheduleAtFixedRate(() -> {
                this.consume();
            }, startDelay, period, timeUnit);
        });
    }

    public void stopAll(){
        this.scheduledExecutor.shutdown();
    }
}
