package performance;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BailoutMain {
    public static final int TEST_TIME = 240 * 1000;

    public static final ThreadLocal<Random> threadLocalRandom =
            ThreadLocal.withInitial(() -> new Random(Thread.currentThread().getId()));

    private static char[] alphabet = {'a','b','c','d'};

    private static String[] states = {"Alabama", "California", "Hawaii", "Massachustts", "New Mexico",
            "North Carolina", "Ohio", "Pennsylvania", "Tennessee", "Washing", "Wyoming"};

    public static void main(String[] args) {
        final int numberOfThreads = Runtime.getRuntime().availableProcessors();
        final int dbSize = TaxPayerBailoutDB.NUMBER_OF_RECORDS_DESIRED;
        final int taxPayerListSize = dbSize / numberOfThreads;

        System.out.println("Number of threads to run concurrently : " + numberOfThreads);
        System.out.println("Tax payer database size : " + dbSize);

        //往数据库填充记录
        System.out.println("Creating tax payer database ...");
        TaxPayerBailoutDB db = new TaxPayerBailoutDbImpl(dbSize);
        List<String>[] taxPayerList = new ArrayList[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            taxPayerList[i] = new ArrayList<>(taxPayerListSize);
        }

        System.out.println("\tTax payer database created.");

        System.out.println("Allocating (" + numberOfThreads + ") threads ...");
        //创建executors池，执行Callable
        ExecutorService pool =
                Executors.newFixedThreadPool(numberOfThreads);
        populateDatabase(db, taxPayerList, dbSize);
        Callable<BailoutFuture>[] callables = new TaxCallable[numberOfThreads];
        for (int i = 0; i < callables.length; i++) {
            callables[i] = new TaxCallable(taxPayerList[i], db);
        }
        System.out.println("\tthreads allocated.");

        System.out.println("Starting (" + callables.length + ") threads ...");

        Set<Future<BailoutFuture>> set = new HashSet<>();

        for (int i = 0; i < callables.length; i++) {
            Callable<BailoutFuture> callable = callables[i];
            Future<BailoutFuture> future = pool.submit(callable);
            set.add(future);
        }

        System.out.println("\t(" + callables.length + ") threads started.");
        //阻塞并等待所有Callable完成
        System.out.println("Waiting for " + TEST_TIME / 1000 + " seconds for ("
         + callables.length + ") threads to complete ...");

        double iterationsPerSecond = 0;
        long recordsAdded = 0, recordsRemoved = 0;
        long nullCounter = 0; int counter = 1;
        for (Future<BailoutFuture> future : set) {
            BailoutFuture result = null;
            try {
                result = future.get();
            } catch (InterruptedException e) {
                Logger.getLogger(BailoutMain.class.getName()).log(Level.SEVERE, null, e);
            } catch (ExecutionException e) {
                Logger.getLogger(BailoutMain.class.getName()).log(Level.SEVERE, null, e);
            }
            System.out.println("Iterations per second on thread[" +
                counter ++ + "] -> " + result.getIterationsPerSecond());
            iterationsPerSecond += result.getIterationsPerSecond();
            recordsAdded += result.getRecordsAdded();
            recordsRemoved += result.getRecordsRemoved();
            nullCounter += result.getNullCounter();
        }
        //订单总数
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Total iterations per second -> " +
                df.format(iterationsPerSecond));
        NumberFormat nf = NumberFormat.getInstance();
        System.out.println("Total records added --> " + nf.format(recordsAdded));
        System.out.println("Total records removed --> " + nf.format(recordsRemoved));
        System.out.println("Total records in db --> " + nf.format(db.size()));
        System.out.println("Total null records encountered --> " + nf.format(nullCounter));

        System.exit(0);
    }

    private static void populateDatabase(TaxPayerBailoutDB db, List<String>[] taxPayerList, int dbSize) {
        for (int i = 0; i < dbSize; i++) {
            String key = getRandomTaxPayerId();
            TaxPayerRecord tpr = makeTaxPayerRecord();
            db.add(key, tpr);
            int index = i % taxPayerList.length;
            taxPayerList[index].add(key);
        }
    }

    public static String getRandomTaxPayerId() {
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            int index = threadLocalRandom.get().nextInt(alphabet.length);
            sb.append(alphabet[index]);
        }
        return sb.toString();
    }

    public static TaxPayerRecord makeTaxPayerRecord() {
        String firstName = getRandomName();
        String lastName = getRandomName();
        String ssn = getRandomSSN();
        String address = getRandomAddress();
        String city = getRandomCity();
        String state = getRandomState();
        return new TaxPayerRecord(firstName,lastName,ssn,address,city,state);
    }

    private static String getRandomState() {
        int index = threadLocalRandom.get().nextInt(states.length);
        return states[index];
    }

    private static String getRandomCity() {
        StringBuilder sb=  new StringBuilder();
        int size = threadLocalRandom.get().nextInt(5) + 6;
        loop(sb, size);
        return sb.toString();
    }

    private static void loop(StringBuilder sb, int size) {
        for (int i = 0; i < size; i++) {
            int index=  threadLocalRandom.get().nextInt(alphabet.length);
            char c =alphabet[index];
            if (i==0){
                c = Character.toUpperCase(c);
            }
            sb.append(c);
        }
    }

    private static String getRandomAddress() {
        StringBuilder sb = new StringBuilder(24);
        int size = threadLocalRandom.get().nextInt(14) + 10;
        for (int i = 0; i < size; i++) {
            if (i < 5) {
                int x =threadLocalRandom.get().nextInt(8);
                sb.append(x + 1);
            }
            int index = threadLocalRandom.get().nextInt(alphabet.length);
            char c = alphabet[index];
            if (i==5) {
                c = Character.toUpperCase(c);
            }
            sb.append(c);
        }
        return sb.toString();
    }

    private static String getRandomSSN() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            if (i==3 || i==6) {
                sb.append('-');
            }
            int x= threadLocalRandom.get().nextInt(9);
            sb.append(x);
        }
        return sb.toString();
    }

    private static String getRandomName() {
        StringBuilder sb = new StringBuilder();
        int size = threadLocalRandom.get().nextInt(8) + 5;
        loop(sb, size);
        return sb.toString();
    }
}