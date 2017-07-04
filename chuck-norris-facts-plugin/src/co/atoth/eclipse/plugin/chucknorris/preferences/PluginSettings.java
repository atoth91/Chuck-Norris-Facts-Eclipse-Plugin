package co.atoth.eclipse.plugin.chucknorris.preferences;


public class PluginSettings {

    public static final String DEFAULT_FIRST_NAME = "Chuck";
    public static final String DEFAULT_LAST_NAME = "Norris";
    public static final int DEFAULT_FACT_NO = 3;

    public int factLoadCount = DEFAULT_FACT_NO;
    public String factFirstName = DEFAULT_FIRST_NAME;
    public String factLastName = DEFAULT_LAST_NAME;

    public int getFactLoadCount() {
        return factLoadCount;
    }

    public void setFactLoadCount(int factLoadCount) {
        this.factLoadCount = factLoadCount;
    }

    public String getFactFirstName() {
        return factFirstName;
    }

    public void setFactFirstName(String factFirstName) {
        this.factFirstName = factFirstName;
    }

    public String getFactLastName() {
        return factLastName;
    }

    public void setFactLastName(String factLastName) {
        this.factLastName = factLastName;
    }

}