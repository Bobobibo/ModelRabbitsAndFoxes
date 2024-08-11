package org.example;

import java.util.List;

public class Fox extends Animal {

    public int turnsWithoutFood = 0;

    public Fox(int x, int y, boolean isAlive) {
        super(x, y, isAlive);
    }


    public char draw() {
        return isAlive ? 'F' : '○';
    }


    public boolean iterate(List<Animal> neighbours) {
        boolean ateRabbit = false;

        for (Animal neighbour : neighbours) {
            if (neighbour instanceof Rabbit && neighbour.isAlive) {
                // eat the rabbit
                neighbour.kill();
                ateRabbit = true;
                turnsWithoutFood = 0; // reset hunger
                break;
            }
        }

        if (!ateRabbit) {
            turnsWithoutFood++;
        }

        if (turnsWithoutFood >= 3) {
            this.kill(); // death after 3 turns
        }

        return isAlive;
    }
}
