package org.example;

import java.util.List;

public class Rabbit extends Animal {

    public Rabbit(int x, int y, boolean isAlive) {
        super(x, y, isAlive);
    }


    public char draw() {
        return isAlive ? 'R' : '○';
    }


    public boolean iterate(List<Animal> neighbours) {
        int rabbitNeighbours = 0;
        for (Animal neighbour : neighbours) {
            if (neighbour instanceof Rabbit && neighbour.isAlive) {
                rabbitNeighbours++;
            }
        }

        if (rabbitNeighbours == 1) {
            this.revive(); // revive if exactly 1 rabbit neighbor
        } else if (neighbours.size() == 8) {
            this.kill(); // die if exactly 8 neighbors
        }

        return isAlive;
    }
}
