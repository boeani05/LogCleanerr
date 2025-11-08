import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LogCleaner {
    public static void main(String[] args) throws IOException {
        Path input = Path.of("start/src/input/app.log");
        Path output = Path.of("start/src/output/cleaned.log");

        if (output.getParent() != null) {
            Files.createDirectories(output.getParent());
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(output, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
             BufferedReader bufferedReader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            int lineCounter = 0;
            int warns = 0;
            int error = 0;
            int totalLines = 0;
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                if (line.contains("WARN")) {
                    warns++;
                    totalLines++;
                    printWriter.printf("""
                                    %d. %s
                                    """,
                            ++lineCounter,
                            line
                    );
                    continue;
                }

                if (line.contains("ERROR")) {
                    error++;
                    totalLines++;
                    printWriter.printf("""
                                    %d. %s
                                    """,
                            ++lineCounter,
                            line
                    );
                    continue;
                }
                totalLines++;
                System.out.println(++lineCounter + ". " + line);
            }
            printWriter.println("\n=== Statistik ===");
            printWriter.printf("Gesamtzeilen: %d%nWARNs: %d%nERRORs: %d%n", totalLines, warns, error);
        }
    }
}