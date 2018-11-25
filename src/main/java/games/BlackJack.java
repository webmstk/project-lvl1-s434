package games;

import java.io.IOException;
import static games.CardUtils.getShuffledCards;
import static games.CardUtils.getPar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlackJack {

    private static final Logger log = LoggerFactory.getLogger(BlackJack.class);

    private static final int MAX_VALUE = 21;

    private static final int MAX_CARDS_COUNT = 8;

    private static final int PLAYERS_COUNT = 2;

    private static final int PLAYER = 0;

    private static final int AI = 1;

    private static final int AI_STOP_POINTS = 16;

    private static final int BET = 10;

    private static final int INITIAL_ROUND_CARDS_COUNT = 2;

    private static int[] cards; // Основная колода

    private static int cursor = 0; // Счётчик карт основной колоды

    private static int[][] playersCards = new int[PLAYERS_COUNT][MAX_CARDS_COUNT];

    private static int[] playersCursors = new int[PLAYERS_COUNT];

    private static int[] playersMoney = {100, 100};

    public static void main(String... __) throws IOException {
        while (playersMoney[PLAYER] >= BET && playersMoney[AI] >= BET) {
            int card;
            int cardsCount;

            initRound();
            printInitRound();

            // player

            cardsCount = 0;

            while (cardsCount++ < INITIAL_ROUND_CARDS_COUNT || confirm("Берём ещё?")) {
                card = dealCard(PLAYER);
                printDeal(PLAYER, card);
            }

            // ai

            cardsCount = 0;

            while (cardsCount++ < INITIAL_ROUND_CARDS_COUNT || sum(AI) <= AI_STOP_POINTS) {
                card = dealCard(AI);
                printDeal(AI, card);
            }

            // result

            int playerSum = getFinalSum(PLAYER);
            int aiSum = getFinalSum(AI);

            printRoundResult(playerSum, aiSum);

            if (playerSum > aiSum) {
                playersMoney[PLAYER] += BET;
                playersMoney[AI] -= BET;
            } else if (playerSum < aiSum) {
                playersMoney[AI] += BET;
                playersMoney[PLAYER] -= BET;
            }

            printRoundSummary(playerSum, aiSum);
        }

        printGameResult();
    }

    private static int value(int card) {
        switch (getPar(card)) {
            case JACK: return 2;
            case QUEEN: return 3;
            case KING: return 4;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case ACE:
            default: return 11;
        }
    }

    private static void initRound() {
        cards = getShuffledCards();
        playersCards = new int[PLAYERS_COUNT][MAX_CARDS_COUNT];
        playersCursors = new int[]{0, 0};
        cursor = 0;
    }

    private static int addCard2Player(int player) {
        int card = cards[cursor++];
        int playerCursor = playersCursors[player];

        playersCards[player][playerCursor] = card;
        playersCursors[player]++;

        return card;
    }

    private static int sum(int player) {
        int playerCursor = playersCursors[player];
        int points = 0;

        for (int i = 0; i < playerCursor; i++) {
            points += value(playersCards[player][i]);
        }

        return points;
    }

    private static int getFinalSum(int player) {
        int points = sum(player);

        return points > MAX_VALUE ? 0 : points;
    }

    private static int dealCard(int player) {
        return addCard2Player(player);
    }

    private static void printInitRound() {
        log.info("\nУ вас " + playersMoney[PLAYER] + "$, у компьютера - " + playersMoney[AI] +
                "$. Начинаем новый раунд!");
    }

    private static void printDeal(int player, int card) {
        if (player == PLAYER) {
            log.info("Вам выпала карта " + CardUtils.toString(card));
        } else {
            log.info("Компьютеру выпала карта " + CardUtils.toString(card));
        }
    }

    private static void printRoundResult(int playerSum, int aiSum) {
        log.info("Сумма ваших очков - " + playerSum + ", компьютера - " + aiSum);
    }

    private static void printRoundSummary(int playerSum, int aiSum) {
        log.info("");

        if (playerSum > aiSum) {
            log.info("Вы выиграли раунд! Получаете " + BET + "$");
        } else if (playerSum < aiSum) {
            log.info("Вы проиграли раунд! Теряете " + BET + "$");
        } else {
            log.info("Ничья.");
        }
    }

    private static void printGameResult() {
        log.info("");

        if (playersMoney[PLAYER] > 0) {
            log.info("Вы выиграли! Поздравляем!");
        } else {
            log.info("Вы проиграли. Соболезнуем...");
        }
    }

    private static boolean confirm(String message) throws IOException {
        log.info(message + " \"Y\" - Да, {любой другой символ} - нет (Чтобы выйти из игры, " +
                "нажмите Ctrl + C)");

        switch (Choice.getCharacterFromUser()) {
            case 'Y':
            case 'y': return true;
            default: return false;
        }
    }

}
