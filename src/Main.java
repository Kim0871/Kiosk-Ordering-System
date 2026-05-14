import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // ============== Composite Pattern ==============
        MenuCategory menu = new MenuCategory("Menu");

        MenuCategory drinks = new MenuCategory("Drinks");
        drinks.add(new Product("Kopi Ais", 5.0));
        drinks.add(new Product("Coke", 2.5));
        drinks.add(new Product("Fanta", 2.5));
        drinks.add(new Product("FuzeTea", 2.5));
        drinks.add(new Product("Water", 1.0));

        MenuCategory sandwiches = new MenuCategory("Sandwiches");
        sandwiches.add(new Product("Chicken Sandwich", 6.0));
        sandwiches.add(new Product("Veggie Sandwich", 5.5));
        
        MenuCategory snacks = new MenuCategory("Snacks");
        snacks.add(new Product("Curry Puffs", 2.5));
        snacks.add(new Product("Kuih", 1.5));
        snacks.add(new Product("Pau", 2.0));

        menu.add(drinks);
        menu.add(sandwiches);
        menu.add(snacks);

        menu.display("");

        List<Product> order = new ArrayList<>();
       
        while (true) {
            if (!order.isEmpty()) {
                System.out.println("\nYour order:");
                for (Product p : order) {
                    System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")");
                }
            }

            System.out.println("\nType product name to add (or 'done' when finished): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("done")) break;
            Product found = findProduct(menu, input);
            
            if (found != null) {

                // ============== Strategy Pattern ==============
                if (found.getName().equalsIgnoreCase("Kopi Ais")) {
                
                    DrinkCustomizer context = new DrinkCustomizer();
                
                    System.out.println("Choose size (small / medium +0.5/ large +1.0)");
                    String size = scanner.nextLine();
                    context.addStrategy(new CupSize(size));
                
                    System.out.println("Ice ? (yes / no)");
                    String ice = scanner.nextLine();

                    if (ice.equalsIgnoreCase("yes")) {
                        context.addStrategy(new Ice(true));
                    } else {
                        context.addStrategy(new Ice(false));
                    }

                    System.out.println("Sugar level? (none / 0% / 50% / 100%)");
                    String sugar = scanner.nextLine();
                    if (!sugar.equals("none")) {
                        context.addStrategy(new SugarLevel(sugar));
                    }

                    Drink result = context.applyCustomization(
                        new Drink(found.getName(), found.getPrice())
                    );

                    System.out.println("\nYour drink:");
                    System.out.println(result.getDescription());
                    System.out.println("Price: " + result.getPrice());

                    order.add(new Product(result.getDescription(), result.getPrice()));
                }

                // ============== Decorator Pattern ==============
                else if (found.getName().toLowerCase().contains("sandwich")) {
                    Sandwich s;

                    switch (found.getName().toLowerCase()) {
                        case "chicken sandwich" -> s = new ChickenSandwichBase();
                        case "veggie sandwich" -> s = new VeggieSandwichBase();
                        default -> throw new IllegalStateException("Unexpected value: " + found.getName().toLowerCase());
                    }
                    
                    List<String> extras = new ArrayList<>();
                    List<String> removed = new ArrayList<>();
                    double costSandwich = s.getCost();
                    String baseName = found.getName();
                    StringBuilder receipt = new StringBuilder();

                    while (true) {
                        System.out.println("\nType the ingredient you want to add extra (patty +1.5/ cheese +1.0/ lettuce +0.5/ tomato +0.5) or 'remove' to get rid of one (type 'done' when finished):");
                        String sandwichInput = scanner.nextLine().toLowerCase();

                        if (sandwichInput.equals("done")) break;
                        if (sandwichInput.equals("remove")) {
                            System.out.println("Type ingredient to remove:");
                            String ing = scanner.nextLine().toLowerCase();
                            removed.add(ing);
                            extras.remove(ing);
                            System.out.println(ing + " removed.");
                        } 
                        else if (!sandwichInput.equals("patty") && !sandwichInput.equals("cheese") && !sandwichInput.equals("lettuce") && !sandwichInput.equals("tomato")) {
                            System.out.println("Unknown ingredient");
                        }
                        else {
                            if (!removed.contains(sandwichInput)){
                                extras.add(sandwichInput);
                                System.out.println(sandwichInput + " added as extra.");
                            }
                        }
                    }
                    
                    receipt.append(found.getName()).append(" ($").append(costSandwich).append(") ");

                    for (String ing : extras) {
                        switch (ing) {
                            case "cheese" -> {
                                receipt.append("+ Cheese (+1.0)");
                                costSandwich += 1.0;
                            }
                            case "patty" -> {
                                receipt.append("+ Patty (+1.5)");
                                costSandwich += 1.5;
                            }
                            case "lettuce" -> {
                                receipt.append("+ Lettuce (+0.5)");
                                costSandwich += 0.5;
                            }
                            case "tomato" -> {
                                receipt.append("+ Tomato (+0.5)");
                                costSandwich += 0.5;
                            }
                        }
                    }

                   for (String ing : removed) {
                        receipt.append("- ").append(ing).append(" (removed)");
                    }
                    
                    System.out.println("\nYour sandwich:");
                    System.out.println(receipt.toString());

                    order.add(new Product(receipt.toString(), costSandwich));
                }

                else {
                    order.add(found);
                    System.out.println(input + " added.");
                }

            } else {
                System.out.println("Not found in menu.");
            }
        }

        System.out.println("\nYour order:");
        double total = 0;
        for (Product p : order) {
            System.out.println("- " + p.getName() + " ($" + p.getPrice() + ")");
            total += p.getPrice();
        }
        System.out.println("Total: $" + total);
    }

    public static Product findProduct(MenuComponent component, String name) {
        if (component instanceof Product p) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        if (component instanceof MenuCategory cat) {
            for (MenuComponent itemComponent : cat.getChildren()) {
                Product result = findProduct(itemComponent, name);
                if (result != null) return result;
            }
        }

        return null;
    }
}