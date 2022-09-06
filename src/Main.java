import java.io.File;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws Exception {

        String[] products = {"Молоко", "Гречка", "Пшеница", "Масло"};
        int[] price = {50, 68, 45, 120};


        Basket basket = new Basket(products, price);

        while (true) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Список возможных товаров для покупки:");

            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ": " + products[i] + " " + price[i] + " руб/шт.");
            }

            String input = scanner.nextLine();
            if (input.equals("end")) {
                basket.saveTxt(new File("basket.txt"));
                System.out.println("Программа завершена!");
                break;
            }

            int indexProduct = Integer.parseInt(String.valueOf(input.charAt(0))) - 1;
            int amountProduct = Integer.parseInt(String.valueOf(input.charAt(2)));
            basket.addToCart(indexProduct, amountProduct);


        }
        basket.printCart();


    }


}







