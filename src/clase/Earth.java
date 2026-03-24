package clase;

public class Earth extends Spell{
    public Earth(int damage, int mana) {
        super(damage, mana);
    }
    @Override
    public String getType(){
        return "Earth";
    }
}
