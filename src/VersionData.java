public class VersionData {
    private int dbVersion;
    private int minAppVersion;
    private int maxAppVersion;

    public VersionData(int dbVersion, int minAppVersion, int maxAppVersion) {
        this.dbVersion = dbVersion;
        this.minAppVersion = minAppVersion;
        this.maxAppVersion = maxAppVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public void setMinAppVersion(int minAppVersion) {
        this.minAppVersion = minAppVersion;
    }

    public void setMaxAppVersion(int maxAppVersion) {
        this.maxAppVersion = maxAppVersion;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public int getMinAppVersion() {
        return minAppVersion;
    }

    public int getMaxAppVersion() {
        return maxAppVersion;
    }
}
