package domain;

public class PlayerState {

    private boolean lost;
    private boolean ready;

    public PlayerState(){
        this.lost = false;
        this.ready = false;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean getReady() {
        return ready;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

    public boolean getLost() {
        return lost;
    }
}
