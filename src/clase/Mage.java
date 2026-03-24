package clase;

public class Mage extends Character{
    public Mage(String name, Integer experience, int lvl) {
        super(name, 100, 100, 5, 10, 5, experience, lvl);
        this.immuneToIce = true;
    }
    @Override
    public String toString() {
        return "Mage: " + super.toString();
    }
}
