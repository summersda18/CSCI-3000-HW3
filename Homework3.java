import java.util.concurrent.*;

public class Homework3 {
    public static void main(String[] args) {
        int min = 0;
        int max = 0;
        int avg = 0;
        int[] input = new int[args.length];
        for(int i=0;i<args.length;i++) {
            input[i] = Integer.parseInt(args[i]);
        }

        ExecutorService pool = Executors.newFixedThreadPool(3);
        Future<Integer> resultMin = pool.submit(new Minimum(input));
        Future<Integer> resultMax = pool.submit(new Maximum(input));
        Future<Integer> resultAvg = pool.submit(new Average(input));
        try {
            min = resultMin.get();
            max = resultMax.get();
            avg = resultAvg.get();
        } catch(InterruptedException | ExecutionException ie){ System.out.println("Enter numbers in as args");}
        pool.shutdown();

        System.out.print("Minimum: " +min+ "\nMaximum: " +max+ "\nAverage: " +avg);
    }
}

class Minimum implements Callable<Integer> {
    private int[] input;
    public Minimum(int[] input) {
        this.input = input; 
    }
    public Integer call() {
        int min = input[0];
        for(int i=1;i<input.length;i++) {
            if(input[i] < min) {
                min = input[i];
            }
        }
        return min;
    }
}

class Maximum implements Callable<Integer> {
    private int[] input;
    public Maximum(int[] input) {
        this.input = input;
    }
    public Integer call() {
        int max = input[0];
        for(int i=1;i<input.length;i++) {
            if(input[i] > max) {
                max = input[i];
            }
        }
        return max;
    }
}

class Average implements Callable<Integer> {
    private int[] input;
    public Average(int[] input) {
        this.input = input;
    }
    public Integer call() {
        int avg = 0;
        for(int i=1;i<input.length;i++) {
            avg += input[i];
        }
        return avg/input.length;
    }
}