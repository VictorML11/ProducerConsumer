package me.victor.custom;

import me.victor.lib.consumer.ScheduledConsumer;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class CustomConsumer extends ScheduledConsumer<List<String>> {

    private String name;

    public CustomConsumer(String name, BlockingQueue<List<String>> queue, int consumers) {
        super(queue, consumers);
        this.name = name;
    }

    @Override
    public void consumeData(List<String> data) {
        data.forEach(d -> {
//            System.out.println(name + " " + d);
        });

        System.out.println(name + " Consumed: " + data.size());
    }


}
