package model;

public enum StaffStatus {
    ACTIVE("Active"), INACTIVE("Inactive"), ON_LEAVE("On Leave"),
    RESIGNED("Resigned"), TERMINATED("Terminated"), SUSPENDED("Suspended"),
    ON_PROBATION("On Probation"), PART_TIME("Part-Time"), FULL_TIME("Full-Time"),
    FREELANCE("Freelance");

    private String name;

    private StaffStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
