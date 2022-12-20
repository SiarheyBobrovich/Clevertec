package by.bobrovich.market;

import by.bobrovich.market.api.Receipt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Scanner;

public class UserTalker {

    private final Scanner console;

    public UserTalker(Scanner scanner) {
        this.console = scanner;
    }

    public void talkAboutReceipt(Receipt receipt) throws IOException {
        int userAnswer = talkAboutWhereDoWePrint();

        if (userAnswer == 1) {
            receipt.print(System.out);
        }else if (userAnswer == 2){
            Path tempFile = createTempFile();
            receipt.print(new OutputStreamWriter(new FileOutputStream(tempFile.toFile())));
            System.out.println("путь к файлу: " + tempFile);
        }
    }

    /**
     * Returns path to temp file
     * @return path to file
     */
    private Path createTempFile() throws IOException {
        return Files.createTempFile(LocalDateTime.now().toString(), "-receipt.txt");
    }

    /**
     * Returns answer from user
     * @return 1 if console and 2 if file
     */
    public int talkAboutWhereDoWePrint() {
        String userPrintMessage =
                """
                Куда печатаем чек?
                    1) Консоль
                    2) Файл
                """;

        int userAnswer;
        do {
            System.out.print(userPrintMessage);
            userAnswer = console.nextInt();

        }while(userAnswer < 1 || userAnswer > 2);

        return userAnswer;
    }
}