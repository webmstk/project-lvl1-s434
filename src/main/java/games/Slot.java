package games;

public class Slot {
    public static void main(String... __) {
        final int bet = 10;
        final int gain = 1_000;
        final int reelMax = 7;
        int money = 100;


        while (money >= bet) {
            System.out.println("У вас " + money + "$, ставка - " + bet + "$");

            int reel1 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel2 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;
            int reel3 = ((int) Math.round(Math.random() * 100)) % reelMax + 1;

            System.out.println("Крутим барабаны! Розыгрыш принёс следующие результаты: ");
            System.out.println("первый барабан - " + reel1 + ", второй - " + reel2 + ", третий - " + reel3);

            if (reel1 == reel2 && reel1 == reel3) {
                money += gain;
                System.out.println("Выигрыш " + gain + "$, ваш капитал теперь составляет: " + money + "$");
            } else {
                money -= bet;
                System.out.println("Проигрыш " + bet + "$, ваш капитал теперь составляет: " + money + "$");

                if (money < bet) {
                    System.out.println();
                    System.out.println("Вы проиграли. Приходите, когда будут деньги.");
                }
            }

            System.out.println();
        }
    }
}
