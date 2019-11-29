public class RuleData {
    int index;
    int dataLen;

    String[] ruleStrings;
    int[] selections;
    int[] hanjas;
    int[] sizes;
    int[] suggestionsH2Y;
    int[] suggestionsY2H;

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

    public int[] getSizes() {
        return sizes;
    }

    public int[] getSuggestionsH2Y() {
        return suggestionsH2Y;
    }

    public int[] getSuggestionsY2H() {
        return suggestionsY2H;
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
