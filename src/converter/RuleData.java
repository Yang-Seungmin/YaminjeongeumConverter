package converter;

/**
 * 이 클래스는 야민정음 변환기의 변환 규칙을 가지고 있는 데이터입니다.<br>
 * 예를 들어 [대 to 머]로 문자열을 수정하는 것입니다.<br><br>
 *
 * 이 클래스는 Gson을 사용하여 데이터를 파싱해 데이터를 생성합니다. 사용자가 직접 데이터를 생성할 수 없습니다.
 * @see com.google.gson.Gson
 *
 * @author Yang-Seungmin
 * @since 1.0
 */
public class RuleData {
    /**
     * 변환 규칙 데이터의 고유 식별자로 사용되는 값입니다.
     */
    private int index;
    /**
     * 변환 규칙 데이터의 개수입니다.<br>
     * 예를 들어 '대머리'를 변환하고자 하면 '머머리', '머대리'등 다양한 규칙이 나옵니다.<br>
     * 이 데이터의 수로 사용되는 값입니다.
     */
    private int dataLen;

    /**
     * 변환 규칙 문자열을 가지고 있는 배열 데이터입니다.
     */
    private String[] ruleStrings;
    /**
     * 각 문자열에 대해 변환의 우선순위를 제공합니다.
     * @see SelectionStrength
     */
    private int[] selections;
    /**
     * 변환 규칙 문자열에 한자가 있을 경우 1을 가집니다.
     */
    private int[] hanjas;

    /**
     * 이 객체의 index 값을 반환합니다.
     * @return index 값
     */
    public int getIndex() {
        return index;
    }

    /**
     * 이 객체의 데이터 개수를 반환합니다.
     * @return 데이터의 개수
     */
    public int getDataLen() {
        return dataLen;
    }

    /**
     * 이 객체의 규칙 문자열 배열을 반환합니다.
     * @return 규칙 문자열 배열
     */
    public String[] getRuleStrings() {
        return ruleStrings;
    }

    /**
     * 이 객체의 변환 우선순위를 반환합니다.
     * @return 변환 우선순위
     */
    public int[] getSelections() {
        return selections;
    }

    /**
     * 한자 포함 여부 배열을 반환합니다.
     * @return hanja
     */
    public int[] getHanjas() {
        return hanjas;
    }

    /**
     * selection을 기반으로 한 최적의 데이터를 제공합니다.<br><br>
     *
     * selection 배열의 값에 가장 가까운 데이터 문자열을 반환합니다. 만약 convertHanja가 true이고 그 데이터 문자열에 한자가 포함되어 있으면 건너뜁니다.<br>
     * 예를 들어 selection 배열의 [0,2,3]이고 selection 값이 3이면 문자열 배열에서 3번째 데이터를 리턴합니다.
     *
     * @see #selections
     *
     * @param selection 변환 강도 int(0, 1, 2, 3)
     * @see SelectionStrength
     * @param convertHanja 한자 변환 여부
     * @return selection 배열의 값에 가장 가까운 데이터 문자열을 반환
     */
    public String getData(int selection, boolean convertHanja) {
        for (int i = 0; i < dataLen; i++) {
            if(selections[i] == selection) {
                if(!convertHanja) {
                    if(hanjas[i] == 0) return ruleStrings[i];
                } else return ruleStrings[i];
            }
        }
        return getData(selection - 1, convertHanja);
    }
}
