package converter;

/**
 * 데이터베이스의 버전을 나타냅니다.
 *
 * @author Yang-Seungmin
 * @since 1.0
 */
public class VersionData {
    private int dbVersion;

    public VersionData(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public int getDbVersion() {
        return dbVersion;
    }
}
