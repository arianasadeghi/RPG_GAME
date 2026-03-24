package clase;

import java.util.List;
import java.util.Random;

public abstract class Character extends Entity{
    private String name;
    private int experience;
    private int level;
    private int strength;
    private int charisma;
    private int dexterity;
    private Random randDamage = new Random();
    private Random randMana = new Random();
    private List<Spell> originalAbilities;

    public Character(String name, int maxHealth, int maxMana, int strength, int charisma, int dexterity, int level, int experience) {
        super(100, 50);
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        int damage = randDamage.nextInt(33) + 8;
        int mana = randMana.nextInt(9) + 5;
        this.abilities.add(new Fire(damage, mana));
        this.abilities.add(new Ice(damage, mana));
        this.abilities.add(new Earth(damage, mana));

    }

    public int getLevel(){
        return level;
    }


    public void levelUp(){
        this.level++;
        this.experience = experience + 10;
        this.maxHealth += 10;
        this.maxMana += 10;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.strength += 2;
        this.charisma += 2;
        this.dexterity += 2;
        System.out.println(name + "Leveled up to level " + level + " health " + currentHealth + " mana " + currentMana + " strength " + strength + " dexterity " + dexterity + " charisma " + charisma + " experience " + experience);
    }
    public int getExperience(){
        return experience;
    }
    public void gainExperience(int exp){
        this.experience += exp;
        System.out.println(name + "Gained experience " + exp);
        if(this.experience >= level*100){
            levelUp();
        }
    }

    @Override
    public int getDamage(){
        int damage = super.getDamage();
        int attributeBonus;
        if(this instanceof Warrior){
            attributeBonus = this.strength;
        } else if(this instanceof Mage){
            attributeBonus = this.charisma;
        } else if(this instanceof Rogue){
            attributeBonus = this.dexterity;
        } else {
            attributeBonus = 0;
        }
        int totalDamage = damage + attributeBonus;
        return totalDamage;
    }

    @Override
    public void receiveDamage(int damage){
        currentHealth -= damage;
        if(currentHealth <= 0){
            currentHealth = 0;
            System.out.println(name + " has been removed.");
        }
    }

    public void healToFull(){
        currentHealth += maxHealth;
        if(currentHealth > maxHealth){
            currentHealth = maxHealth;
        }
        currentMana += maxMana;
        if(currentMana > maxMana){
            currentMana = maxMana;
        }
    }


    @Override
    public String toString() {
        return name + "(Level: " + level + " experience: " + experience + ")";
    }
}
