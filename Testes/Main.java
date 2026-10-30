public class Main {
    public static void main(String[] args) {
        LogManager log1 = LogManager.getInstance();
        LogManager log2 = LogManager.getInstance();

        System.out.println("--- Teste Singleton ---");
        System.out.println("log1 e log2 são a mesma instância? " + (log1 == log2));

        System.out.println("\n--- Destino: CONSOLE ---");
        log1.setDestination(LogDestination.CONSOLE);
        log1.log(LogLevel.INFO, "Aplicação iniciada");
        log1.log(LogLevel.DEBUG, "Carregando configurações");
        log1.log(LogLevel.WARNING, "Memória acima de 80%");
        log1.log(LogLevel.ERROR, "Falha ao conectar no servidor");

        System.out.println("\n--- Destino: FILE ---");
        log2.setDestination(LogDestination.FILE);
        log2.setFilePath("logs.txt");
        log2.log(LogLevel.INFO, "Usuário fez login");
        log2.log(LogLevel.ERROR, "Senha incorreta");
        System.out.println("Logs gravados no arquivo logs.txt");

        System.out.println("\n--- Destino: MEMORY ---");
        log1.setDestination(LogDestination.MEMORY);
        log1.log(LogLevel.INFO, "Pedido criado");
        log1.log(LogLevel.WARNING, "Estoque baixo");
        log1.log(LogLevel.DEBUG, "Pedido enviado para pagamento");

        for(LogEntry entry : log1.getLogs()) {
            System.out.println(entry);
        }
    }
}