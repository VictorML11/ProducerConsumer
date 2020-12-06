package me.victor.lib.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ExecutorProducer<T> extends AbstractProducer<T> {

    private ExecutorService executorService;
    private int producers;

    public ExecutorProducer(BlockingQueue<T> queue, int producers) {
        super(queue);
        this.producers = producers;
        this.executorService = Executors.newFixedThreadPool(producers);
    }


    public void startAll(){
        executorService.execute(() -> {
            while(true){
                this.produce();
            }
        });
    }


    public void stopAll(){
        // Disable new tasks from being submitted
        executorService.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            while (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
                    System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            executorService.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }


}
