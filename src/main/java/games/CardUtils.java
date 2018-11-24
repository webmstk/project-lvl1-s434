package games;

import org.apache.commons.math3.util.MathArrays;

public class CardUtils {

    static final int PARS_TOTAL_COUNT = Par.values().length;

    static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length;

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

    static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    static int[] getShuffledCards() {
        int[] deck = createDeck();
        shuffleDeck(deck);

        return deck;
    }

    static int[] createDeck() {
        int[] deck = new int[CARDS_TOTAL_COUNT];

        for (int i = 0; i < CARDS_TOTAL_COUNT; i++) {
            deck[i] = i;
        }

        return deck;
    }

    static void shuffleDeck(int[] deck) {
        MathArrays.shuffle(deck);
    }

}
