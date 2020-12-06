package me.victor.lib.bridger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;


public abstract class AbstractBridgeConsumer <L extends Collection<T>, T, R> implements Function<T,R> {

    private BlockingQueue<L> inputQueue;
    protected List<BlockingQueue<R>> outputQueues;


    public AbstractBridgeConsumer(BlockingQueue<L> inputQueue) {
        this.inputQueue = inputQueue;
        this.outputQueues = new ArrayList<>();
    }

    public AbstractBridgeConsumer(BlockingQueue<L> inputQueue, List<BlockingQueue<R>> outputQueues) {
        this.inputQueue = inputQueue;
        this.outputQueues = outputQueues;
    }

    public void transform(){
        try {
            if(!outputQueues.isEmpty()){
                inputQueue.take().forEach(input -> {
                    outputQueues.forEach( queue -> {
                        try {
                            queue.put(this.apply(input));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                });
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract R transformData(T t);

    @Override
    public R apply(T t) {
        return this.transformData(t);
    }

}
