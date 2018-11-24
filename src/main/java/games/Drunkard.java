package games;

import java.util.Arrays;
import static games.CardUtils.CARDS_TOTAL_COUNT;
import static games.CardUtils.getPar;
import static games.CardUtils.Par;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Drunkard {

    private static final Logger log = LoggerFactory.getLogger(Drunkard.class);

    private static final int PLAYER1 = 0;

    private static final int PLAYER2 = 1;

    private static final int DRAW = 2;

    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];

    private static int[] playersCardHeads = new int[2];

    private static int winner;

    public static void main(String... __) {
        int[] deck = CardUtils.getShuffledCards();

        dealCards(deck);

        int move = 0;

        do {
            move++;

            int card1 = pullCardFromDeck(PLAYER1);
            int card2 = pullCardFromDeck(PLAYER2);

            int compare = compareCards(card1, card2);

            if (compare > 0) {
                grabCards(PLAYER1, card1, card2);
                winner = PLAYER1;
            } else if (compare < 0) {
                grabCards(PLAYER2, card1, card2);
                winner = PLAYER2;
            } else {
                grabCard(PLAYER1, card1);
                grabCard(PLAYER2, card2);

                winner = DRAW;
            }

            printRound(move, card1, card2);
            printRoundWinner();
            printSummary();
        } while (!playerCardsIsEmpty());

        printGameWinner(winner);
    }

    private static void dealCards(int[] deck) {
        int halfDeck = CARDS_TOTAL_COUNT / 2;

        int[] deck1 = Arrays.copyOfRange(deck, 0, halfDeck);
        int[] deck2 = Arrays.copyOfRange(deck, halfDeck, deck.length);

        playersCards[PLAYER1] = Arrays.copyOf(deck1, CARDS_TOTAL_COUNT);
        playersCards[PLAYER2] = Arrays.copyOf(deck2, CARDS_TOTAL_COUNT);

        playersCardHeads[0] = halfDeck;
        playersCardHeads[1] = playersCardHeads[0];
    }

    private static int incrementIndex(int index) {
        return (index + 1) % CARDS_TOTAL_COUNT;
    }

    private static int pullCardFromDeck(int player) {
        int tail = playersCardTails[player];
        int card = playersCards[player][tail];

        playersCardTails[player] = incrementIndex(tail);

        return card;
    }

    private static void grabCards(int player, int card1, int card2) {
        grabCard(player, card1);
        grabCard(player, card2);
    }

    private static void grabCard(int player, int card) {
        int head = playersCardHeads[player];

        playersCards[player][head] = card;
        playersCardHeads[player] = incrementIndex(head);
    }

    private static int compareCards(int card1, int card2) {
        Par par1 = getPar(card1);
        Par par2 = getPar(card2);

        if (par1 == Par.SIX && par2 == Par.ACE) {
            return 1;
        }

        if (par1 == Par.ACE && par2 == Par.SIX) {
            return -1;
        }

        return getPar(card1).compareTo(getPar(card2));
    }

    private static boolean playerCardsIsEmpty() {
        // при текущей реализации колод tail и head будут равны и у победителя и у проигравшего
        // поэтому достаточно проверить одного игрока
        int tail = playersCardTails[PLAYER1];
        int head = playersCardHeads[PLAYER1];

        return tail == head;
    }

    private static int countPlayerCards(int player) {
        int head = playersCardHeads[player];
        int tail = playersCardTails[player];
        int count;

        if (head > tail) {
            count = head - tail;
        } else {
            count = head + CARDS_TOTAL_COUNT - tail;
        }

        if (count == CARDS_TOTAL_COUNT && player != winner) {
            return 0;
        }

        return count;
    }

    private static int printPlayer(int player) {
        return player + 1;
    }

    private static void printRound(int move, int card1, int card2) {
        log.info("Ход #" + move +  ". Карта игрока №" + printPlayer(PLAYER1) + ": " +
                CardUtils.toString(card1) + "; карта игрока №" + printPlayer(PLAYER2) + ": " +
                CardUtils.toString(card2) + ".");
    }

    private static void printRoundWinner() {
        if (winner == DRAW) {
            log.info("Ничья.");
        } else {
            log.info("Раунд выиграл игрок №" + printPlayer(winner) + "!");
        }
    }

    private static void printGameWinner(int winner) {
        if (winner == PLAYER1) {
            log.info("Выиграл игрок №" + printPlayer(winner) + ", пьяница - игрок №" + printPlayer(PLAYER2));
        } else {
            log.info("Выиграл игрок №" + printPlayer(winner) + ", пьяница - игрок №" + printPlayer(PLAYER1));
        }
    }

    private static void printSummary() {
        log.info("Карт у игрока №" + printPlayer(PLAYER1) + ": " + countPlayerCards(PLAYER1) +
                ", карт у игрока №" + printPlayer(PLAYER2) + ": " + countPlayerCards(PLAYER2));
        log.info("");
    }
}
