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
            log.info("У вас {}$, ставка - {}$", money, bet);

            int reel1 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel2 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel3 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;

            log.info("Крутим барабаны! Розыгрыш принёс следующие результаты: ");
            log.info("первый барабан - {}, второй - {}, третий - {}", reel1, reel2, reel3);

            if (reel1 == reel2 && reel1 == reel3) {
                money += gain;
                log.info("Выигрыш {}$, ваш капитал теперь составляет: {}$", gain, money);
            } else {
                money -= bet;
                log.info("Проигрыш {}$, ваш капитал теперь составляет: {}$", bet, money);
            }

            log.info("");
        }

        log.info("Вы проиграли. Приходите, когда будут деньги.");
    }
}
