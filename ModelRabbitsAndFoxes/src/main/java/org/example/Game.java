package org.example;

import java.util.LinkedList;
import java.util.List;

public class Game {
    public Board board;

    public Game(Board board) {
        this.board = board;
    }

    public void Play() {
        board.draw();
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            board.iterate();
            board.draw();
        }
    }

    public static void main(String[] args) {
        int width = 20;
        int height = 20;
        Board board = new Board(width, height);
        List<Animal> initialCells = new LinkedList<>();

        initialCells.add(new Fox(4, 3, true));
        initialCells.add(new Rabbit(5, 3, true));
        initialCells.add(new Rabbit(6, 4, true));
        initialCells.add(new Rabbit(6, 3, true));

        board.initialize(initialCells);
        Game game = new Game(board);
        game.Play();
    }
}
