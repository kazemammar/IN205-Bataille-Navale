package ensta;

import ensta.model.Board;

public class TestBoard {
    public static void main(String[] args) {
        Board board1 = new Board("board 1", 10);
        board1.setName("Test123");
        board1.print();
        System.out.println(board1.getName());

    }
}
