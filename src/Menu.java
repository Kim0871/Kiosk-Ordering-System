import java.util.*;

interface MenuComponent {
    void display(String indent);
}

class Product implements MenuComponent {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }

    public void display(String indent) {
        System.out.println(indent + "└── " + name + " - $" + price);
    }
}

class MenuCategory implements MenuComponent {
    private String name;
    private List<MenuComponent> products = new ArrayList<>();

    public MenuCategory(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        products.add(component);
    }

    public void remove(MenuComponent component) {
        products.remove(component);
    }

    public List<MenuComponent> getChildren() {
        return products;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + name);
        for (int i = 0; i < products.size(); i++) {
            MenuComponent item = products.get(i);
            item.display(indent + "   ");
        }
    }
}