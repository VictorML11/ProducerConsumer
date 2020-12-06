package me.victor.custom;


import me.victor.lib.producer.ScheduledProducer;
import me.victor.utils.Utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CustomProducer extends ScheduledProducer<List<Integer>> {


    public CustomProducer(BlockingQueue<List<Integer>> queue, int producers) {
        super(queue, producers);
    }

    @Override
    public List<Integer> produceData() {
        int randomConcurrent = Utils.randomData();
        System.out.println("Generated " + randomConcurrent);
        return Utils.distributeRandomData(randomConcurrent);
    }
}
