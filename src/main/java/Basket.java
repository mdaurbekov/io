import java.io.*;

public class Basket {
    private String[] products;
    private int[] price;
    private int[] basket;


    public Basket(String[] products, int[] price) {
        Basket basket1 = loadFromTxtFile(new File("basket.txt"));
        if (basket1 != null) {
            this.products = basket1.getProducts();
            this.price = basket1.getPrice();
            this.basket = basket1.getBasket();
        } else {
            this.products = products;
            this.price = price;
            this.basket = new int[products.length];
        }
    }

    public Basket(String[] products, int[] price, int[] basket) {
        this.products = products;
        this.price = price;
        this.basket = basket;
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrice() {
        return price;
    }

    public int[] getBasket() {
        return basket;
    }

    public void addToCart(int productNum, int amount) {
        basket[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Ваша корзина:");
        int sumProducts = 0;
        for (int i = 0; i < basket.length; i++) {
            if (basket[i] != 0) {
                System.out.println(products[i] + " " + basket[i] + " шт " + price[i] + " руб/шт " + (basket[i] * price[i]) + " рублей в сумме");
                sumProducts += basket[i] * price[i];
            }

        }
        System.out.println("Итого: " + sumProducts);

    }

    public void saveTxt(File textFile) {

        try (BufferedWriter out = new BufferedWriter(new FileWriter(textFile))) {
            for (int i = 0; i < products.length; i++) {
                out.write(products[i] + " " + basket[i] + " " + price[i] + "\r\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Basket loadFromTxtFile(File textFile) {
        Basket rezult = null;
        if (textFile.exists() & textFile.length() != 0) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));) {
                int count = 0;
                int size = 4;
                String[] products = new String[size];
                int[] basket = new int[size];
                int[] price = new int[size];
                String read;
                while ((read = bufferedReader.readLine()) != null) {
                    String[] strings = read.split(" ");
                    products[count] = strings[0];
                    basket[count] = Integer.parseInt(strings[1]);
                    price[count] = Integer.parseInt(strings[2]);
                    count++;
                }
                rezult = new Basket(products, price, basket);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return rezult;
    }
}
