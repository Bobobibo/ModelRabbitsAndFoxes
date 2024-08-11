package org.example;

import java.util.List;

public abstract class Animal {
    public boolean isAlive;
    public int x;
    public int y;

    public Animal(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    public void kill() {
        isAlive = false;
    }

    public void revive() {
        isAlive = true;
    }

    public abstract char draw();
    public abstract boolean iterate(List<Animal> neighbours);
}