import java.util.*;

interface DrinkStrategy {
    Drink apply(Drink drink);
}

public class Drink {
    private String name;
    private double basePrice;

    public Drink(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    public String getDescription() {
        return name;
    }

    public double getPrice() {
        return basePrice;
    }
}


class CupSize implements DrinkStrategy {

    private String size;

    public CupSize(String size) {
        this.size = size;
    }

    public Drink apply(Drink drink) {

        double price = drink.getPrice();

        switch (size.toLowerCase()) {
            case "small" -> price += 0;
            case "medium" -> price += 0.5;
            case "large" -> price += 1.0;
        }

        String desc = drink.getDescription()
        + " (" + size + 
            (size.equalsIgnoreCase("medium") ? " +0.5" : "") +
            (size.equalsIgnoreCase("large") ? " +1.0" : "") 
        +")";

        return new Drink(desc, price);
    }
}

class Ice implements DrinkStrategy {
    private boolean haveIce;

    public Ice(boolean haveIce) {
        this.haveIce = haveIce;
    }

    public Drink apply(Drink drink) {
        String desc = drink.getDescription() + (haveIce ? " + Ice" : " no Ice");
        return new Drink(desc, drink.getPrice());
    }
}

class SugarLevel implements DrinkStrategy {
    private String level;

    public SugarLevel(String level) {
        this.level = level;
    }

    public Drink apply(Drink drink) {
        return new Drink(drink.getDescription() + " + Sugar: " + level, drink.getPrice());
    }
}

class DrinkCustomizer {
    private List<DrinkStrategy> strategies = new ArrayList<>();

    public void addStrategy(DrinkStrategy strategy) {
        strategies.add(strategy);
    }

    public Drink applyCustomization(Drink drink) {
        Drink result = drink;
        for (DrinkStrategy strategy : strategies) {
            result = strategy.apply(result);
        }
        return result;
    }

    public void clear() {
        strategies.clear();
    }
}