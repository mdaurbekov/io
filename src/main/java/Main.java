import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class Main {

    public static void saveBasketJson(Basket basket, Config config) {
        Gson gson = new GsonBuilder().create();

        try (FileWriter file = new FileWriter(config.getFileNameSave())) {
            file.write(gson.toJson(basket));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Basket loadBasketJson(String[] products, int[] price, Config config) {
        Basket basket = null;

        Gson gson = new GsonBuilder().create();
        File file = new File(config.getFileNameLoad());
        String productsJson;
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                productsJson = reader.readLine();
                basket = gson.fromJson(productsJson, Basket.class);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            String basketArray = Arrays.toString(new int[products.length]);
            productsJson = "{"
                    + "\"products\":" + Arrays.toString(products) + ","
                    + "\"price\":" + Arrays.toString(price) + ","
                    + "\"basket\":" + basketArray
                    + "}";

            basket = gson.fromJson(productsJson, Basket.class);

        }

        return basket;
    }

    public static Config loadConfig() {
        return new Config();
    }

    public static void main(String[] args) throws Exception {
        Config config = loadConfig();
        String[] products = {"Молоко", "Гречка", "Пшеница", "Масло"};
        int[] price = {50, 68, 45, 120};
        ClientLog clientLog = new ClientLog();
        Basket basket = null;
        if (config.isLoad() && "json".equals(config.getFormatLoad())) {
            basket = loadBasketJson(products, price, config);
        } else {
            basket = new Basket(products, price, config);
        }

        while (true) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Список возможных товаров для покупки:");

            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ": " + products[i] + " " + price[i] + " руб/шт.");
            }

            String input = scanner.nextLine();
            if (input.equals("end")) {

                if (config.isLog()) {
                    clientLog.exportAsCSV(new File("/" + config.getFileNameLog()));
                }
                if (config.isSave() && "txt".equals(config.getFormatSave())) {
                    basket.saveTxt(new File(config.getFileNameSave()));
                }
                if (config.isSave() && "json".equals(config.getFormatSave())) {
                    saveBasketJson(basket, config);
                }

                System.out.println("Программа завершена!");
                break;
            }

            int indexProduct = Integer.parseInt(String.valueOf(input.charAt(0))) - 1;
            int amountProduct = Integer.parseInt(String.valueOf(input.charAt(2)));
            basket.addToCart(indexProduct, amountProduct);
            clientLog.log(indexProduct, amountProduct);


        }
        basket.printCart();


    }


}







