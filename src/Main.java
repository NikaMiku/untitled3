import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException{
        int numThreads = 1000;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Thread thread = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = 0;
                for (int j = 0; j < route.length(); j++) {
                    if (route.charAt(j) == 'R') {
                        countR++;
                    }
                }
                synchronized (sizeToFreq) {
                    int freq = sizeToFreq.getOrDefault(countR, 0);
                    sizeToFreq.put(countR, freq + 1);
                }
                System.out.println("Thread " + Thread.currentThread().getId() + ": " + countR);
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        int maxFreq = 0;
        int maxFreqSize = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            int size = entry.getKey();
            int freq = entry.getValue();
            if (freq > maxFreq) {
                maxFreq = freq;
                maxFreqSize = size;
            }
        }
        System.out.println("Самое частое кол-во повторений " + maxFreqSize + " (встретилось " + maxFreq + " раз)");
        System.out.println("Другие размеры: ");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            int size = entry.getKey();
            int freq = entry.getValue();
            if (size != maxFreqSize) {
                System.out.println("- " + size + " (" + freq + " раз)");
            }
        }
    }



    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}