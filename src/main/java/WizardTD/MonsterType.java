package WizardTD;

public enum MonsterType {
    GREMLIN,
    WORM,
    BEETLE;
    public static MonsterType fromString(String str) {
        for (MonsterType enumValue : MonsterType.values()) {
            if (enumValue.name().equalsIgnoreCase(str)) {
                return enumValue;
            }
        }
        throw new IllegalArgumentException("No enum constant " + MonsterType.class + " for string: " + str);
    }
}
