package clase;

import clase.*;
import interfete.Battle;

import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle {
    // lista de abilitati
    protected ArrayList<Spell> abilities;
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;

    protected boolean immuneToFire;
    protected boolean immuneToIce;
    protected boolean immuneToEarth;

    public Entity(int maxHealth, int maxMana){
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.maxMana = maxMana;
        this.abilities = new ArrayList<>();
    }
    public int getCurrentHealth(){
        return currentHealth;
    }
    public int getCurrentMana(){
        return currentMana;
    }
    public int getDamage(){
        return new Random().nextInt(10) + 10;
    }

    public void populateAbilities(){
        Random rand = new Random();
        int numAbilities = rand.nextInt(4) + 3;

        if(abilities.isEmpty()){
            abilities.add(new Fire(rand.nextInt(31) + 20, rand.nextInt(11) + 10));
            abilities.add(new Ice(rand.nextInt(31) + 20, rand.nextInt(11) + 10));
            abilities.add(new Earth(rand.nextInt(31) + 20, rand.nextInt(11) + 10));
        }
        while(abilities.size() < numAbilities){
            int spellType = rand.nextInt(3);
            int damage = rand.nextInt(31) + 20;
            int mana = rand.nextInt(11) + 10;

            Spell spell = null;
            switch(spellType){
                case 0:
                    if(spellType == 0){
                        spell = new Fire(damage, mana);
                    }
                    break;
                case 1:
                    if(spellType == 1){
                        spell = new Ice(damage, mana);
                    }
                    break;
                case 2:
                    if(spellType == 2){
                        spell = new Earth(damage, mana);
                    }
                    break;
            }
            abilities.add(spell);
        }

    }

    public void receiveDamage(int damage){
        currentHealth -= damage;
        if(currentHealth <= 0){
            currentHealth = 0;
            System.out.println("Entity has been defeated!");
        }
    }
    public void regenerateHealth(int amount){
        currentHealth += amount;
        if(currentHealth > maxHealth){
            currentHealth = maxHealth;
        }
        System.out.println(this.getClass().getName() + " regenerated health: " + currentHealth);
    }
    public void regenerateMana(int amount){
        currentMana += amount;
        if(currentMana > maxMana){
            currentMana = maxMana;
        }
        System.out.println(this.getClass().getName() + " regenerated mana: " + currentMana);
    }
    public void useAbility(Spell spell, Entity target){
        if(target instanceof Enemy){
            Enemy enemyTarget = (Enemy) target;
            if((spell instanceof Fire && enemyTarget.immuneToFire) || (spell instanceof Ice && enemyTarget.immuneToIce) || (spell instanceof Earth && enemyTarget.immuneToEarth)){
                System.out.println(enemyTarget.getClass().getName() + " is immune to " + spell.getClass().getName());
                return;
            }
        }
        if(currentMana < spell.getMana()){
            System.out.println("Nu ai suficienta mana pentru a folosi abilitatea.");
            return;
        }
        if((spell instanceof Fire && target.immuneToFire) || (spell instanceof Ice && target.immuneToIce) || (spell instanceof Earth && target.immuneToEarth)){
            System.out.println(target.getClass().getName() + " is immune to " + spell.getClass().getName());
            return;
        }
        currentMana -= spell.getMana();
        target.receiveDamage(spell.getDamage());

    }
} 