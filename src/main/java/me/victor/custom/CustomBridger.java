package me.victor.custom;

import me.victor.lib.bridger.ExecutorBridgerConsumer;
import me.victor.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CustomBridger extends ExecutorBridgerConsumer<List<Integer>, Integer, List<String>> {

    public CustomBridger(BlockingQueue<List<Integer>> inputQueue, List<BlockingQueue<List<String>>> outputQueues, int bridgersCount) {
        super(inputQueue, outputQueues, bridgersCount);
    }

    public CustomBridger(BlockingQueue<List<Integer>> inputQueue, int bridgersCount) {
        super(inputQueue, bridgersCount);
    }

    @Override
    public List<String> transformData(Integer amount) {
        return IntStream.range(0, amount).mapToObj(i -> Utils.randomString()).collect(Collectors.toCollection(() -> new ArrayList<>(amount)));
    }


}


