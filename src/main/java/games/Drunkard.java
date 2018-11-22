package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

public class Drunkard {

    private static final int PARS_TOTAL_COUNT = Par.values().length;

    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

    private static final int PLAYER1 = 0;

    private static final int PLAYER2 = 1;

    private static final int DRAW = 2;

    private static int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];

    private static int[] playersCardTails = new int[2];

    private static int[] playersCardHeads = new int[2];

    private static int winner;

    public static void main(String... __) {
        int[] deck = createDeck();
        shuffleDeck(deck);
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

    enum Suit {
        SPADES, // пики
        HEARTS, // черви
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    private static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    private static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    private static int[] createDeck() {
        int[] deck = new int[CARDS_TOTAL_COUNT];

        for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
            deck[i] = i;
        }

        return deck;
    }

    private static void shuffleDeck(int[] deck) {
        MathArrays.shuffle(deck);
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
        System.out.println("Ход #" + move +  ". Карта игрока №" + printPlayer(PLAYER1) + ": " + toString(card1) +
                "; карта игрока №" + printPlayer(PLAYER2) + ": " + toString(card2) + ".");
    }

    private static void printRoundWinner() {
        if (winner == DRAW) {
            System.out.println("Ничья.");
        } else {
            System.out.println("Раунд выиграл игрок №" + printPlayer(winner) + "!");
        }
    }

    private static void printGameWinner(int winner) {
        if (winner == PLAYER1) {
            System.out.println("Выиграл игрок №" + printPlayer(winner) + ", пьяница - игрок №" + printPlayer(PLAYER2));
        } else {
            System.out.println("Выиграл игрок №" + printPlayer(winner) + ", пьяница - игрок №" + printPlayer(PLAYER1));
        }
    }

    private static void printSummary() {
        System.out.println("Карт у игрока №" + printPlayer(PLAYER1) + ": " + countPlayerCards(PLAYER1) +
                ", карт у игрока №" + printPlayer(PLAYER2) + ": " + countPlayerCards(PLAYER2));
        System.out.println();
    }
}
