package domain;

public class Color {
    private SimonColor colorCode;
    private String toHexcode;
    private String fromHexcode;


    public Color(SimonColor colorName, String fromHexcode, String toHexcode) {
        this.colorCode = colorName;
        this.toHexcode = toHexcode;
        this.fromHexcode = fromHexcode;
    }

    public Color(SimonColor colorName){
        this.colorCode = colorName;
    }

    public SimonColor getColorCode(){
        return colorCode;
    }

    public void setColorCode(SimonColor colorCode){
        this.colorCode = colorCode;
    }

    public void setFromHexcode(String fromHexcode) {
        this.fromHexcode = fromHexcode;
    }

    public String getFromHexcode() {
        return fromHexcode;
    }

    public void setToHexcode(String toHexcode) {
        this.toHexcode = toHexcode;
    }

    public String getToHexcode() {
        return toHexcode;
    }
}
