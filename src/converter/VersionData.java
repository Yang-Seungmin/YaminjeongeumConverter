package converter;

public class VersionData {
    private int dbVersion;

    public VersionData(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public int getDbVersion() {
        return dbVersion;
    }
}
