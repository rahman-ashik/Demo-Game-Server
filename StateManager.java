public class StateManager {
    String state;

    StateManager(String state) {
        this.state = state;
    }

    StateManager() {
        this.state = "000000000000000000200000000000100000000000000";
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void appendState(String state) {
        this.state += state;
    }

    public void resetState() {
        this.state = "000000000000000000000000000000000000000000000";
    }
}