package clase;

public class Fire extends Spell{
    public Fire(int damage, int mana) {
        super(damage, mana);
    }
    @Override
    public String getType(){
        return "Fire";
    }
}
