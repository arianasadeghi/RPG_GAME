package clase;

public abstract class Spell {
    private int damage;
    private int mana;
    public Spell(int damage, int mana) {
        this.damage = damage;
        this.mana = mana;
    }
    public int getDamage() {
        return damage;
    }
    public int getMana(){
        return mana;
    }
    public abstract String getType();
    @Override
    public String toString() {
        return this.getClass().getName() + " damage: " + damage + " mana: " + mana;
    }
}