package by.bobrovich.market;

import by.bobrovich.market.api.Receipt;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            receipt.print(new OutputStreamWriter(Files.newOutputStream(talkAboutFilePath())));
        }
    }

    /**
     * Returns path to existed and writable file
     * @return path to file
     */
    private Path talkAboutFilePath() {
        String s = """
                Введите путь к файлу:;
                """;
        String userAnswer;
        Path pathToFile;
        boolean isExistAndWritable;

        do {
            System.out.println(s);
            isExistAndWritable = true;
            userAnswer = console.nextLine();
            pathToFile = Paths.get(userAnswer);

            if (Files.notExists(pathToFile)) {
                System.out.println("Файл не существует");
                isExistAndWritable = false;
            } else if (!Files.isWritable(pathToFile)) {
                System.out.println("В файл нельзя записать");
                isExistAndWritable = false;
            }

        }while (!isExistAndWritable);

        return pathToFile;
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