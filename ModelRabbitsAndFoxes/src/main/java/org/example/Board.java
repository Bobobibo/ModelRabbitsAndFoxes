package org.example;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Board {

    int width;
    int height;
    List<Animal> animalsList;

    public Board(int width, int height) {
        this.animalsList = new LinkedList<>();
        this.width = width;
        this.height = height;
    }

    public void initialize(List<Animal> initAnimals) {
        for (Animal animal : initAnimals) {
            animal.revive();
            animalsList.add(animal);
        }
    }

    Point[] Directions = {
            new Point(-1, -1), new Point(-1, 0), new Point(-1, 1),
            new Point(0, -1), new Point(0, 1),
            new Point(1, -1), new Point(1, 0), new Point(1, 1)
    };

    public List<Animal> getNeighbours(Animal animal) {
        List<Animal> neighbours = new LinkedList<>();
        int x = animal.x;
        int y = animal.y;

        for (Point direction : Directions) {
            int neighborX = x + direction.x;
            int neighborY = y + direction.y;

            if (neighborX >= 0 && neighborX < width && neighborY >= 0 && neighborY < height) {
                Animal neighbour = getAnimalAt(neighborX, neighborY);
                if (neighbour != null) {
                    neighbours.add(neighbour);
                }
            }
        }
        return neighbours;
    }

    private Animal getAnimalAt(int x, int y) {
        for (Animal animal : animalsList) {
            if (animal.x == x && animal.y == y) {
                return animal;
            }
        }
        return null;
    }

    private boolean isAnimalAlive(int x, int y) {
        Animal animal = getAnimalAt(x, y);
        return animal != null && animal.isAlive;
    }

    public void iterate() {
        Set<Point> animalsToCheck = new HashSet<>();

        for (Animal animal : animalsList) {
            animalsToCheck.add(new Point(animal.x, animal.y));
            for (Animal neighbour : getNeighbours(animal)) {
                animalsToCheck.add(new Point(neighbour.x, neighbour.y));
            }
        }

        List<Animal> newTurnBoard = new LinkedList<>();

        for (Point point : animalsToCheck) {
            Animal currentAnimal = getAnimalAt(point.x, point.y);
            if (currentAnimal != null) {
                List<Animal> neighbours = getNeighbours(currentAnimal);

                if (currentAnimal instanceof Rabbit) {
                    boolean newState = currentAnimal.iterate(neighbours);
                    if (newState && !isFoxAt(point.x, point.y)) {
                        newTurnBoard.add(new Rabbit(point.x, point.y, true));
                    }
                } else if (currentAnimal instanceof Fox) {
                    boolean newState = currentAnimal.iterate(neighbours);
                    if (newState) {
                        newTurnBoard.add(new Fox(point.x, point.y, true));

                        for (Animal neighbour : neighbours) {// spawning a new fox in place of the eaten rabbit
                            if (neighbour instanceof Rabbit && !neighbour.isAlive) {
                                newTurnBoard.add(new Fox(neighbour.x, neighbour.y, true));
                                break;
                            }
                        }
                    }
                }
            }
        }

        animalsList = newTurnBoard;
    }

    private boolean isFoxAt(int x, int y) {
        for (Animal animal : animalsList) {
            if (animal.x == x && animal.y == y && animal instanceof Fox) {
                return true;
            }
        }
        return false;
    }

    public void draw() {
        char[][] boardPrintation = new char[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boardPrintation[i][j] = '○';
            }
        }

        for (Animal animal : animalsList) {
            if (animal.isAlive) {
                boardPrintation[animal.x][animal.y] = animal.draw();
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(boardPrintation[j][i] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
