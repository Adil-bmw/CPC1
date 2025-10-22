import java.util.*;

public class ParallelSalesAnalysis {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== СИСТЕМА ПАРАЛЛЕЛЬНОГО АНАЛИЗА ПРОДАЖ ===");
        System.out.println("Доступно процессоров: " + Runtime.getRuntime().availableProcessors());


        String[] filenames = new String[15];
        for (int i = 0; i < 15; i++) {
            filenames[i] = String.format("test_csv_files/test1/sales_diff_days_branch%d.csv", i + 1);
        }


        long parallelTime = processParallel(filenames);

        System.out.println("\n=== РЕЗУЛЬТАТЫ ===");
        System.out.println("Параллельная обработка заняла: " + parallelTime + " мс");
        System.out.println("Программа завершена успешно ✅");
    }

    public static long processParallel(String[] filenames) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        List<FileProcessor> processors = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();


        for (String file : filenames) {
            FileProcessor fp = new FileProcessor(file);
            Thread t = new Thread(fp);
            processors.add(fp);
            threads.add(t);
            t.start();
        }


        for (Thread t : threads) {
            t.join();
        }


        SalesStatistics stats = new SalesStatistics();
        for (FileProcessor fp : processors) {
            for (SalesRecord r : fp.getResults()) {
                stats.addRecord(r);
            }
        }


        stats.printReport();

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
