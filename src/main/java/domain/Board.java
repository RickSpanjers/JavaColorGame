package domain;

import java.util.ArrayList;

public class Board {
    private ArrayList<Color> colors;
    private ArrayList<Color> pressedColors;

    public Board() {
        pressedColors = new ArrayList<>();
        colors = new ArrayList<>();
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }

    public void addPressedColor(Color color) {
        pressedColors.add(color);
    }

    public ArrayList<Color> getPressedColors() {
        return pressedColors;
    }
}
