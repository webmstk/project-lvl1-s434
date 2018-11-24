package games;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slot {
    private static final Logger log = LoggerFactory.getLogger(Slot.class);

    public static void main(String... __) {
        final int bet = 10;
        final int gain = 1_000;
        final int reelMax = 7;
        int money = 100;

        while (money >= bet) {
            log.info("У вас " + money + "$, ставка - " + bet + "$");

            int reel1 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel2 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel3 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;

            log.info("Крутим барабаны! Розыгрыш принёс следующие результаты: ");
            log.info("первый барабан - " + reel1 + ", второй - " + reel2 + ", третий - " + reel3);

            if (reel1 == reel2 && reel1 == reel3) {
                money += gain;
                log.info("Выигрыш " + gain + "$, ваш капитал теперь составляет: " + money + "$");
            } else {
                money -= bet;
                log.info("Проигрыш " + bet + "$, ваш капитал теперь составляет: " + money + "$");
            }

            log.info("");
        }

        log.info("Вы проиграли. Приходите, когда будут деньги.");
    }
}
