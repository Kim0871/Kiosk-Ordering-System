interface Sandwich {
    String getDescription();
    double getCost();
}

class ChickenSandwichBase implements Sandwich {
    public String getDescription() {
        return "Chicken Sandwich";
    }

    public double getCost() {
        return 6.0;
    }
}

class VeggieSandwichBase implements Sandwich {
    public String getDescription() {
        return "Veggie Sandwich";
    }

    public double getCost() {
        return 5.5;
    }
}

abstract class SandwichDecorator implements Sandwich {
    protected Sandwich sandwich;

    public SandwichDecorator(Sandwich sandwich) {
        this.sandwich = sandwich;
    }
}


class Patty extends SandwichDecorator {
    public Patty(Sandwich sandwich) {
        super(sandwich);
    }

    public String getDescription() {
        return sandwich.getDescription() + ", extra Patty (+1.5)";
    }

    public double getCost() {
        return sandwich.getCost() + 1.5;
    }
}

class Cheese extends SandwichDecorator {
    public Cheese(Sandwich sandwich) {
        super(sandwich);
    }

    public String getDescription() {
        return sandwich.getDescription() + ", extra Cheese (+1.0)";
    }

    public double getCost() {
        return sandwich.getCost() + 1.0;
    }
}

class Lettuce extends SandwichDecorator {
    public Lettuce(Sandwich sandwich) {
        super(sandwich);
    }

    public String getDescription() {
        return sandwich.getDescription() + ", extra Lettuce (+0.5)";
    }

    public double getCost() {
        return sandwich.getCost() + 0.5;
    }
}

class Tomato extends SandwichDecorator {
    public Tomato(Sandwich sandwich) {
        super(sandwich);
    }

    public String getDescription() {
        return sandwich.getDescription() + ", extra Tomato (+0.5)";
    }

    public double getCost() {
        return sandwich.getCost() + 0.5;
    }
}