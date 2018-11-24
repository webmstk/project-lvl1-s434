package games;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Choice {

    private static final Logger log = LoggerFactory.getLogger(Choice.class);

    static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String... __) {
        log.info("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\", 3 - \"Очко\"");

        try {
            switch (getCharacterFromUser()) {
                case '1':
                    Slot.main();
                    break;
                case '2':
                    Drunkard.main();
                    break;
                case '3':
                    BlackJack.main();
                    break;
                default:
                    log.info("Игры с таким номером нет!");
            }
        } catch(IOException e) {
            log.info(e.getMessage());
        }
    }

    static char getCharacterFromUser() throws IOException {
        byte[] input = new byte[1 + LINE_SEPARATOR.length()];

        if (System.in.read(input) != input.length) {
            throw new RuntimeException("Пользователь ввёл недостаточное количество символов");
        }

        return (char) input[0];
    }
}
