import GUI.ChessFrame;

import java.io.IOException;

public class Main {
    // TODO: Bug with trying to mate(?) where sometimes, beaten piece can revive?
    // TODO: Bug where king cannot beat piece checking him
    public static void main(String[] args) {
        try {
            ChessFrame frame = new ChessFrame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
