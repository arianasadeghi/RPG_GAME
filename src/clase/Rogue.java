package clase;

public class Rogue extends Character{
    public Rogue(String name, Integer experience, int lvl){
        super(name, 120, 75, 7, 5, 10, experience, lvl);
        this.immuneToEarth = true;
    }
    @Override
    public String toString() {
        return "Rogue: " + super.toString();
    }
}
