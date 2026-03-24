package clase;

public class FactoryPattern {
    public static Character createCharacter(String name, String profession, int experience, int level) {
        switch (profession) {
            case "Mage":
                return new Mage(name, experience, level);
            case "Warrior":
                return new Warrior(name, experience, level);
            case "Rogue":
                return new Rogue(name, experience, level);
            default:
                throw new IllegalArgumentException("Invalid profession" + profession);
        }
    }
}
