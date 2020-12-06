package me.victor;

import me.victor.custom.CustomBridger;
import me.victor.custom.CustomConsumer;
import me.victor.custom.CustomProducer;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final int RATE = 10;

    public static void main(String[] args) {

        BlockingQueue<List<Integer>> producerQueue = new ArrayBlockingQueue<>(10);
        BlockingQueue<List<String>> consumerQueue1 = new ArrayBlockingQueue<>(120);
        BlockingQueue<List<String>> consumerQueue2 = new ArrayBlockingQueue<>(120);

        CustomProducer customProducer = new CustomProducer(producerQueue,1);

        CustomBridger customBridger = new CustomBridger(producerQueue, 1);

        CustomConsumer customConsumer1 = new CustomConsumer("Consumer1", consumerQueue1, 1);
        customBridger.addOutputQueue(consumerQueue1);

        CustomConsumer customConsumer2 = new CustomConsumer("Consumer2", consumerQueue2, 1);
        customBridger.addOutputQueue(consumerQueue2);

        customProducer.startAll(0, RATE, TimeUnit.SECONDS);
        customConsumer1.startAll(0, 1, TimeUnit.SECONDS);
        customConsumer2.startAll(0, 1, TimeUnit.SECONDS);
        customBridger.startAllBridgers();

    }



}
