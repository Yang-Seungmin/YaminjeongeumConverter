package converter;

/**
 * 변환에 사용할 데이터 클래스입니다.
 * @author Yang-Seungmin
 * @since 1.0
 */
public class YaminjeongeumData {
    /**
     * 변환 규칙 데이터의 배열입니다.
     * @see RuleData
     */
    private RuleData[] datas;
    /**
     * 버전 정보를 가지고 있는 객체입니다.
     * @see VersionData
     */
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
