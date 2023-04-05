package com.example.labweek6task2;

public class Fruit {

    private String name;
    private String family;
    private String calories;
    private String sugar;
    private String carbohydrates;

    public Fruit(String name, String family, String calories, String sugar, String carbohydrates) {
        this.name = name;
        this.family = family;
        this.calories = calories;
        this. sugar = sugar;
        this.carbohydrates = carbohydrates;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public String getCalories() {
        return calories;
    }

    public String getSugar() {
        return sugar;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }
}
