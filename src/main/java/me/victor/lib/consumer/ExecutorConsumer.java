package me.victor.lib.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ExecutorConsumer<T> extends AbstractConsumer<T> {


    private ExecutorService executorService;
    private int consumers;


    public ExecutorConsumer(BlockingQueue<T> queue, int consumers) {
        super(queue);
        this.consumers = consumers;
        this.executorService = Executors.newFixedThreadPool(this.consumers);
    }


    public void startAll(){
        executorService.execute(() -> {
            while(true){
                this.consume();
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
