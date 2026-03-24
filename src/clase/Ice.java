package clase;

public class Ice extends Spell{
    public Ice(int damage, int mana){
        super(damage, mana);
    }
    @Override
    public String getType(){
        return "Ice";
    }
}
