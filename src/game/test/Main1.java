package game.test;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main1 {
    public static void main(String[] args) {
        Product p1 = new Product("candy");
        Product p2 = new Product("Bar", 4);

        System.out.println(p2.equals(p1));
        System.out.println(p1.id);
        System.out.println(p1.getName());
        p1 = p2;
        System.out.println(p1.getName() + p1.getPrice());
        System.out.println(p1.id);

    }

    private static class Product {
        private int maxId = 0;
        private String name;
        private int price;
        private int id;

        { id = ++maxId; }

        public Product(final String name) {
            this.name = name;
        }

        public Product(String name, int price) {
            this(name);
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return price == product.price && name.equals(product.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, price);
        }
    }
}


