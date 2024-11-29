package beight.eksamenkea.model;

public class Subproject {
    private String subprojectName;
    private String subprojectDescription;
    private float subprojectEstimatedTime;

    public Subproject(String subprojectName, String subprojectDescription, float subprojectEstimatedTime) {
        this.subprojectName = subprojectName;
        this.subprojectDescription = subprojectDescription;
        this.subprojectEstimatedTime = subprojectEstimatedTime;
    }

    public String getSubprojectName() {
        return subprojectName;
    }
    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }
    public String getSubprojectDescription() {
        return subprojectDescription;
    }
    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }
    public float getSubprojectEstimatedTime() {
        return subprojectEstimatedTime;
    }
}
