package converter;

/**
 * 변환 강도를 구현하는 Enum입니다.<br>
 * ORIGINAL, SLIGHT, MODERATE, EXTREME 4가지 데이터로 구성되어 있으며
 * 이 값은 RuleData에 정의된 값에 따라 적절한 값으로 변환됩니다.
 *
 * @see RuleData
 * @see YaminjeongeumData
 */
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
