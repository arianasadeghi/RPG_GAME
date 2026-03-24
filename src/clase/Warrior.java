package clase;

public class Warrior extends Character{
    public Warrior(String name, Integer experience, int lvl) {
        super(name, 150, 50, 10, 5, 7, experience, lvl);
        this.immuneToFire = true;
    }
    @Override
    public String toString() {
        return "Warrior: " + super.toString();
    }
}
