package converter;

public enum SelectionStrength {
    ORIGINAL(0), SLIGHT(1), MODERATE(2), EXTREME(3);

    int strength;

    SelectionStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
