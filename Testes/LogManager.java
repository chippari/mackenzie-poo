import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static LogManager instance;
    private LogDestination destination;
    private List<LogEntry> logs;
    private String filePath;

    private LogManager() {
        this.destination = LogDestination.CONSOLE;
        this.logs = new ArrayList<>();
        this.filePath = "logs.txt";
    }

    public static synchronized LogManager getInstance() {
        if(instance == null) {
            instance = new LogManager();
        }
        return instance;
    }

    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void log(LogLevel level, String message) {
        LogEntry entry = new LogEntry(level, message);

        switch(destination) {
            case CONSOLE:
                writeToConsole(entry);
                break;
            case FILE:
                writeToFile(entry);
                break;
            case MEMORY:
                writeToMemory(entry);
                break;
        }
    }

    public List<LogEntry> getLogs() {
        return logs;
    }

    private void writeToConsole(LogEntry entry) {
        System.out.println(entry);
    }

    private void writeToFile(LogEntry entry) {
        try(BufferedWriter arquivo = new BufferedWriter(new FileWriter(filePath, true))) {
            arquivo.write(entry.toString());
            arquivo.newLine();
        } catch(IOException e) {
            System.out.println("Não foi possível escrever no arquivo.\n" + e);
        }
    }

    private void writeToMemory(LogEntry entry) {
        logs.add(entry);
    }
}