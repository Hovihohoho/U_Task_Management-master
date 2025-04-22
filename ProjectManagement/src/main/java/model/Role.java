package model;

public enum Role {
    STAFF("Staff"), MANAGER("Manager");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
