package model;

public enum ProjectStatus {
    ACTIVE("Active"), COMPLETE("Completed"), ON_HOLD("On Hold");

    private String name;

    private ProjectStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
