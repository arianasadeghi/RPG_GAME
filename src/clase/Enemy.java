package clase;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity{
    boolean immuneToFire;
    boolean immuneToIce;
    boolean immuneToEarth;
    private ArrayList<Spell> abilities;
    private LoginPage.GameWindow.EnemyWindow enemyWindow;

    public Enemy(int minHealth, int maxHealth, LoginPage.GameWindow.EnemyWindow enemyWindow) {
        super(minHealth, maxHealth);
        this.enemyWindow = enemyWindow;
        this.abilities = new ArrayList<>();
        populateAbilities();
        int immunityType = new Random().nextInt(3);
        immuneToFire = immunityType == 0;
        immuneToIce = immunityType == 1;
        immuneToEarth = immunityType == 2;
    }

    @Override
    public void populateAbilities() {
        Random rand = new Random();
        int numAbilities = rand.nextInt(4) + 3;

        boolean hasFire = false;
        boolean hasIce = false;
        boolean hasEarth = false;

        int attempts = 0;
        while(abilities.size() < numAbilities && attempts < 10000) {
            int spellType = rand.nextInt(3);
            int damage = rand.nextInt(18) + 7;
            int mana = rand.nextInt(11) + 10;

            Spell spell = null;
            switch(spellType){
                case 0:
                    if(!hasFire){
                        spell = new Fire(damage, mana);
                        hasFire = true;
                    }
                    break;
                case 1:
                    if(!hasIce){
                        spell = new Ice(damage, mana);
                        hasIce = true;
                    }
                    break;
                case 2:
                    if(!hasEarth){
                        spell = new Earth(damage, mana);
                        hasEarth = true;
                    }
                    break;
            }
            if(spell != null){
                abilities.add(spell);
            }
            attempts++;
        }

    }

    public void attack(Entity target) {
        Random rand = new Random();
        if(abilities.size() > 0 && currentMana>0) {
            Spell chosenSpell = abilities.get(rand.nextInt(abilities.size()));

            if(currentMana>=chosenSpell.getMana()) {
                useAbility(chosenSpell, target);
                enemyWindow.showMessage("Inamicul a folosit abilitatea " + chosenSpell.getClass().getSimpleName() + " si a dat " + chosenSpell.getDamage() + " damage.");
            } else {
                enemyWindow.showMessage("Inamicul nu are destula mana!");
                int normalAttackDamage = rand.nextInt(20) + 10;
                target.receiveDamage(normalAttackDamage);
                enemyWindow.showMessage("Inamicul a dat " + normalAttackDamage + " damage cu atacul normal.");
            }
        } else {
            int normalAttackDamage = rand.nextInt(20) + 10;
            target.receiveDamage(normalAttackDamage);
            enemyWindow.showMessage("Inamicul a dat " + normalAttackDamage + " damage cu atacul normal.");
        }
    }

    @Override
    public void receiveDamage(int damage) {
        if(new Random().nextDouble() < 0.25) {
            enemyWindow.showMessage("Inamicul a evitat atacul");
            return;
        }
        super.receiveDamage(damage);
    }
    @Override
    public int getDamage(){
        int calculatedDamage = super.getDamage();
        if(new Random().nextDouble() < 0.5) {
            calculatedDamage *= 2;
            enemyWindow.showMessage("Inamicul a dublat damage-ul.");
        }
        return calculatedDamage;
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
}
