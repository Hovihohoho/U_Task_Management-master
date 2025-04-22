package model;

public enum Priority {
    LOW("Low"), MEDIUM("Medium"), HIGH("High");

    private String name;

    private Priority(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
