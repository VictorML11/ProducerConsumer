package me.victor.lib.bridger;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ExecutorBridgerConsumer<L extends Collection<T>, T, R> extends AbstractBridgeConsumer<L, T, R> {

    private ExecutorService executorService;
    private int bridgers;

    public ExecutorBridgerConsumer(BlockingQueue<L> inputQueue, int bridgersCount) {
        super(inputQueue);
        this.bridgers = bridgersCount;
        executorService = Executors.newFixedThreadPool(this.bridgers);

    }

    public ExecutorBridgerConsumer(BlockingQueue<L> inputQueue, List<BlockingQueue<R>> outputQueues, int bridgersCount) {
        super(inputQueue, outputQueues);
        this.bridgers = bridgersCount;
        executorService = Executors.newFixedThreadPool(this.bridgers);
    }

    public void addOutputQueue(BlockingQueue<R> queue){
        this.outputQueues.add(queue);
    }

    public void startAllBridgers(){
        executorService.execute(() -> {
            while(true){
                this.transform();
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
