package beight.eksamenkea.model;

import java.util.List;

abstract public class ProjectTask {

    private final String title;

    public ProjectTask(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    abstract public List<ProjectTask> getSubjects();

    public float getEstimatedHours() {
        float sum = 0;
        for (ProjectTask pj : getSubjects()) {
            sum += pj.getEstimatedHours();
        }
        return sum;
    }

    public float getSpentHours(){
        float sum = 0;
        for (ProjectTask pj : getSubjects()) {
            sum += pj.getSpentHours();
        }
        return sum;
    }

    public float getKgCO2e(){
        float sum = 0;
        for (ProjectTask pj : getSubjects()) {
            sum += pj.getKgCO2e();
        }
        return sum;
    }

    public int getPercentageDone() {
        float estimatedHours = getEstimatedHours();
        if (estimatedHours == 0) return 0;
        return Math.round((getSpentHours() / estimatedHours) * 100f);
    }

    public String formatEstimatedHours() {
        return format(getEstimatedHours());
    }

    public String formatSpentHours() {
        return format(getSpentHours());
    }

    public String formatKgCO2e() {
        return format(getKgCO2e());
    }

    private static String format(float number) {
        String s = String.valueOf(number);
        int index = s.indexOf(".");
        if (s.endsWith(".0")) return s.substring(0, index); // Whole numbers
        if (s.length() < s.substring(0, index).length() + 3) return s; // One decimal
        return s.substring(0, index + 3); // Maximum of two decimals
    }
}
