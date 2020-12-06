package me.victor.utils;

import me.victor.Main;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    private static Random random = new Random();

    public static String randomString(){
        return UUID.randomUUID().toString();
    }

    public static int randomData(){
        return random.ints(100, 200).findFirst().getAsInt();
    }

    public static List<Integer> distributeRandomData(int amount){

        int count = Main.RATE;
        Random r = new Random();

        List<Integer> data = IntStream.range(0, count).map(value -> (r.nextInt(amount) +1)).boxed().collect(Collectors.toList());
        int total = data.stream().mapToInt(Integer::intValue).sum();

        double scale = 1d * amount / total;
        List<Integer> scaled = data.stream().map(v -> (int)(v*scale)).collect(Collectors.toList());
        total = scaled.stream().mapToInt(Integer::intValue).sum();

        while(total++ < amount){
            int i = r.nextInt(count);
            scaled.set(i, scaled.get(i) + 1);
        }

        return scaled;
    }
}
