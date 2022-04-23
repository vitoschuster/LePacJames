package client;

public enum Constants {
    W(1120), H(700);
    
    private int numVal;

    Constants(int numVal) {
        this.numVal = numVal;
    }

    public int toInt() {
        return this.numVal;
    }
}
