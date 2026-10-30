import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LocalDateTime timestamp;
    private LogLevel level;
    private String message;

    public LogEntry(LogLevel level, String message) {
        this.timestamp = LocalDateTime.now(java.time.ZoneId.of("America/Sao_Paulo"));
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "[" + timestamp.format(formato) + "] [" + level + "] " + message;
    }
}