import java.io.*;
import java.util.*;

public class FileProcessor implements Runnable {
    private String filename;
    private List<SalesRecord> results;
    private volatile boolean completed;
    private volatile String errorMessage;

    public FileProcessor(String filename) {
        this.filename = filename;
        this.results = new ArrayList<>();
        this.completed = false;
        this.errorMessage = null;
    }

    @Override
    public void run() {
        System.out.println("[" + Thread.currentThread().getName() + "] Начинаю обработку файла: " + filename);
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int quantity = Integer.parseInt(parts[2].trim());
                    double price = Double.parseDouble(parts[3].trim());
                    String date = parts[4].trim();

                    SalesRecord record = new SalesRecord(id, name, quantity, price, date);
                    results.add(record);
                }
            }
        } catch (IOException e) {
            errorMessage = "Ошибка чтения файла: " + e.getMessage();
        } catch (NumberFormatException e) {
            errorMessage = "Ошибка формата данных: " + e.getMessage();
        }

        completed = true;
        long endTime = System.currentTimeMillis();
        System.out.println("[" + Thread.currentThread().getName() + "] Обработано записей: "
                + results.size() + " за " + (endTime - startTime) + " мс");
    }

    public List<SalesRecord> getResults() { return results; }
    public boolean isCompleted() { return completed; }
    public String getErrorMessage() { return errorMessage; }
    public String getFilename() { return filename; }
}
