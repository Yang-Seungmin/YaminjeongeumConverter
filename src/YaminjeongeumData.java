public class YaminjeongeumData {
    private RuleData[] datas;
    private VersionData versionInfo;

    public YaminjeongeumData(RuleData[] datas, VersionData versionInfo) {
        this.datas = datas;
        this.versionInfo = versionInfo;
    }

    public RuleData[] getDatas() {
        return datas;
    }

    public VersionData getVersionInfo() {
        return versionInfo;
    }
}
