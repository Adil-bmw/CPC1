import java.util.*;
import java.util.stream.Collectors;

public class SalesStatistics {
    private int totalRecords;
    private double totalRevenue;
    private Map<String, Integer> productQuantities;
    private Map<String, Double> productRevenues;

    public SalesStatistics() {
        this.totalRecords = 0;
        this.totalRevenue = 0.0;
        this.productQuantities = new HashMap<>();
        this.productRevenues = new HashMap<>();
    }

    public synchronized void addRecord(SalesRecord record) {
        totalRecords++;
        double amount = record.getTotalAmount();
        totalRevenue += amount;
        productQuantities.merge(record.getProductName(), record.getQuantity(), Integer::sum);
        productRevenues.merge(record.getProductName(), amount, Double::sum);
    }

    public synchronized void merge(SalesStatistics other) {
        this.totalRecords += other.totalRecords;
        this.totalRevenue += other.totalRevenue;
        other.productQuantities.forEach((p, q) -> this.productQuantities.merge(p, q, Integer::sum));
        other.productRevenues.forEach((p, r) -> this.productRevenues.merge(p, r, Double::sum));
    }

    public void printReport() {
        System.out.println("\n=== ОТЧЁТ ПО ПРОДАЖАМ ===");
        System.out.println("Всего записей обработано: " + totalRecords);
        System.out.printf("Общая выручка: %.2f тг\n", totalRevenue);

        System.out.println("\n--- Топ 5 товаров по количеству ---");
        productQuantities.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " шт."));

        System.out.println("\n--- Топ 5 товаров по выручке ---");
        productRevenues.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.printf("%s: %.2f тг%n", e.getKey(), e.getValue()));
    }
}
