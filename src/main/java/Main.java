import GUI.ChessFrame;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ChessFrame frame = new ChessFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
