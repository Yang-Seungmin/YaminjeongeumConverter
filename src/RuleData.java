public class RuleData {
    int index;
    int dataLen;

    String[] ruleStrings;
    int[] selections;
    int[] hanjas;

    public int getIndex() {
        return index;
    }

    public int getDataLen() {
        return dataLen;
    }

    public String[] getRuleStrings() {
        return ruleStrings;
    }

    public int[] getSelections() {
        return selections;
    }

    public int[] getHanjas() {
        return hanjas;
    }

    public String getData(int selection, boolean convertHanja) {
        for (int i = 0; i < selections.length; i++) {
            if(selections[i] == selection) {
                if(!convertHanja) {
                    if(hanjas[i] == 0) return ruleStrings[i];
                } else return ruleStrings[i];
            }
        }
        return getData(selection - 1, convertHanja);
    }
}
