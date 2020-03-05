package GUI;

import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChessFrame extends JFrame {

    public ChessFrame() throws IOException {
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JSONObject obj = new JSONObject(new String(Files.readAllBytes(Paths.get("src/main/resources/levels/Standard.json"))));
        this.setName(obj.getString("name"));
        this.add(new LevelPanel(obj.getJSONArray("board")));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
