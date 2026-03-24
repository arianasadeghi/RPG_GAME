package clase;


import com.sun.source.tree.Tree;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Game {
    private static Game instance;
    private boolean isRunning;
    public ArrayList<Account> accounts = new ArrayList<>();
    private Account currAccount;
    public Character currCharacter;
    public Grid grid;
    public ArrayList<Credentials> credentialsList = new ArrayList<>();
    private Credentials currCredentials;
    private LoginPage.GameWindow.EnemyWindow gameWindow;

    public Game() {
        this.isRunning = true;
        loadAccounts();
    }
    public static Game getInstance() {
        if(instance == null) {
            synchronized (Game.class) {
                if(instance == null) {
                    instance = new Game();
                }
            }
        }
        return instance;
    }
    public void loadAccounts(){
        try{
            String content = new String(Files.readAllBytes(Paths.get("accounts.json")));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray accountsArray = jsonObject.getJSONArray("accounts");

            for (int i = 0; i < accountsArray.length(); i++) {
                JSONObject accountJson = accountsArray.getJSONObject(i);

                JSONObject credentialsJson = accountJson.getJSONObject("credentials");
                String email = credentialsJson.getString("email");
                String password = credentialsJson.getString("password");
                Credentials credentials = new Credentials(email, password);
                credentialsList.add(credentials);

                String name = accountJson.getString("name");
                String country = accountJson.getString("country");

                TreeSet<String> favoriteGames = new TreeSet<>();
                JSONArray favoriteGamesArray = accountJson.getJSONArray("favorite_games");
                for (int j = 0; j < favoriteGamesArray.length(); j++) {
                    favoriteGames.add(favoriteGamesArray.getString(j));
                }

                ArrayList<Character> characters = new ArrayList<>();
                JSONArray charactersArray = accountJson.getJSONArray("characters");
                for (int j = 0; j < charactersArray.length(); j++) {
                    JSONObject characterJson = charactersArray.getJSONObject(j);
                    String characterName = characterJson.getString("name");
                    String characterType = characterJson.getString("profession");
                    int level = characterJson.getInt("level");
                    int experience = characterJson.getInt("experience");

                    switch (characterType) {
                        case "Warrior":
                            characters.add(new Warrior(characterName, level, experience));
                            break;
                        case "Mage":
                            characters.add(new Mage(characterName, level, experience));
                            break;
                        case "Rogue":
                            characters.add(new Rogue(characterName, level, experience));
                            break;
                        default:
                            System.out.println("Invalid character type");
                    }
                }
                int mapsCompleted = accountJson.getInt("maps_completed");
                Account account = new Account(characters, mapsCompleted, new Account.Information(credentials, name, country, favoriteGames));
                accounts.add(account);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Eroare la incarcarea conturilor din fisier");
        }
    }
    public void authenticateUser() throws InvalidCommandException, ImpossibleMove {
        Scanner sc = new Scanner(System.in);
        System.out.print("email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        currCredentials = authenticateAccount(email, password);
        if(currCredentials != null){
            System.out.println("Autentificare reusita");
            currAccount = getAccount();
            if(currAccount != null){
                System.out.println(currAccount);
            }
            displayCharacters();
            chooseCharacter();
            if(currCharacter != null){
                System.out.println("Jucator ales cu succes!");
                createGrid();
            }
        } else {
            System.out.println("Autentificare esuata");
        }
        sc.close();
    }
    public void displayCharacters(){
        System.out.println("Caractere disponibile: ");
        ArrayList<Character> characters = currAccount.getCharacters();
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + "."+characters.get(i).toString());
        }
    }
    public void chooseCharacter() throws InvalidCommandException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Alege un caracter prin numarul sau: ");
        while (true) {
            String input = sc.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= currAccount.getCharacters().size()) {
                    currCharacter = currAccount.getCharacters().get(choice - 1);
                    System.out.println("Ai ales caracterul: " + currCharacter);
                    break;
                } else {
                    System.out.println("Ai introdus un numar gresit. Alege din nou.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input invalid: trebuie să fie un număr!");
            }
        }
    }

    public void createGrid() throws ImpossibleMove, InvalidCommandException {
        Random rand = new Random();
        int length = rand.nextInt(7) + 4;
        int width = rand.nextInt(7) + 4;
        grid = Grid.createGrid(length, width);
        Scanner scan= new Scanner(System.in);
        print(grid);
        while(true) {
            System.out.println("In ce directie vrei sa muti jucatorul? (1.Nord, 2.Sud, 3.Est, 4.Vest, 5.Iesire din joc)");
            String input = scan.nextLine();
            try{
                int choice = Integer.parseInt(input);
                if (choice == 5){
                    System.out.println("Iesire din joc...");
                    isRunning = false;
                    break;
                }
                try {
                    Cell nextCell = null;
                    switch (choice){
                        case 1:
                            nextCell = grid.getNextCell(grid.getCurrentCell().getOx()-1, grid.getCurrentCell().getOy());
                            break;
                        case 2:
                            nextCell = grid.getNextCell(grid.getCurrentCell().getOx()+1, grid.getCurrentCell().getOy());
                            break;
                        case 4:
                            nextCell = grid.getNextCell(grid.getCurrentCell().getOx(), grid.getCurrentCell().getOy()-1);
                            break;
                        case 3:
                            nextCell = grid.getNextCell(grid.getCurrentCell().getOx(), grid.getCurrentCell().getOy()+1);
                            break;
                        default:
                            System.out.println("Invalid input");
                            continue;
                    }
                    checkCell(nextCell);
                    switch (choice) {
                        case 1:
                            grid.goNorth();
                            break;
                        case 2:
                            grid.goSouth();
                            break;
                        case 4:
                            grid.goWest();
                            break;
                        case 3:
                            grid.goEast();
                            break;
                        default:
                            System.out.println("Directie invalida.");
                    }
                    print(grid);
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }

            }catch (NumberFormatException e) {
                System.out.println("Invalid command.");
            }
        }
    }
    private Credentials authenticateAccount(String email, String password){
        for(Credentials creds : credentialsList){
            if(creds.getEmail().equals(email.trim()) && creds.getPassword().equals(password.trim())){
                return creds;
            }
        }
        return null;
    }
    private Account getAccount(){
        for(Account account : accounts){
            if(account.getInformation().getCredentials().equals(currCredentials)){
                return account;
            }
        }
        return null;
    }
    public static void print(Grid grid) {
        for(int i = 0; i < grid.size(); i++){
            for(int j = 0; j < grid.get(i).size(); j++){
                System.out.print(grid.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }


    void checkCell(Cell cell) throws InvalidCommandException, ImpossibleMove {
        if(cell == null){
            System.out.println("Grid is null!");
            return;
        }
        System.out.println(cell.getType());
        switch (cell.getType()){
            case ENEMY:
                System.out.println("Ai intalnit un inamic. Incepe lupta!");
                handleEnemy();
                break;
            case SANCTUARY:
                System.out.println("Ai intalnit un sanctuar, ti se va regenera viata si mana.");
                currCharacter.regenerateHealth(50);
                currCharacter.regenerateMana(50);
                break;
            case PORTAL:
                System.out.println("Felicitari ai intalnit un portal. Se genereaza o noua harta.");
                currCharacter.gainExperience(5 * currCharacter.getLevel());
                currAccount.increaseNrGames();
                currCharacter.levelUp();
                createGrid();
                break;
            case VOID:
                System.out.println("Este o celula goala.");
                break;
            default:
                break;
        }
    }

    void handleEnemy() throws InvalidCommandException, ImpossibleMove {
        System.out.println("Intra in handleEnemy");
        Random rand = new Random();
        int maxMana = rand.nextInt(31) + 20;
        int maxHealth = rand.nextInt(51) + 50;

        Enemy enemy = new Enemy(maxHealth, maxMana, gameWindow);

        enemy.populateAbilities();
        currCharacter.populateAbilities();
        System.out.println("Inamicul are " + enemy.getCurrentHealth() + "viata si " + enemy.getCurrentMana() + " mana.");
        System.out.println("Player-ul are " + currCharacter.getCurrentHealth() + "viata si " + currCharacter.getCurrentMana() + " mana.");
        Scanner scan= new Scanner(System.in);
        while(enemy.getCurrentHealth() > 0 && currCharacter.getCurrentHealth() > 0){
            System.out.println("Ce vrei sa faci? (1.Ataca, 2.Fugi, 3.Foloseste abilitate)");
            int input = scan.nextInt();
            switch(input){
                case 1:
                    int playerDamage = currCharacter.getDamage();
                    enemy.receiveDamage(playerDamage);
                    System.out.println("Ai dat " + playerDamage + "damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());

                    if(enemy.getCurrentHealth() > 0){
                        int enemyDamage = enemy.getDamage();
                        currCharacter.receiveDamage(enemyDamage);
                        System.out.println("Inamicul ti-a dat " + enemyDamage + "damage. Viata ta si mana ta: " + currCharacter.getCurrentHealth() +" " + currCharacter.getCurrentMana());
                    }
                    break;
                case 2:
                    System.out.println("Ai ales sa fugi, inamicul te-a lasat in pace dar nu ai castigat experienta.");
                    return;
                case 3:
                    if(currCharacter.getCurrentMana() > 0 && !currCharacter.abilities.isEmpty()){
                        System.out.println("Alege o abilitate:");
                        for(int i = 0; i < currCharacter.abilities.size(); i++){
                            System.out.println((i + 1) + ". " + currCharacter.abilities.get(i).getClass().getSimpleName());
                        }
                        int abilityChoice = scan.nextInt();
                        if(abilityChoice >= 1 && abilityChoice <= currCharacter.abilities.size()){
                            Spell chosenSpell = currCharacter.abilities.get(abilityChoice - 1);
                            if(chosenSpell.getMana() > currCharacter.getCurrentMana()){
                                System.out.println("Nu ai destula mana. Se va folosi un atac normal.");
                                int damagePlayer = currCharacter.getDamage();
                                enemy.receiveDamage(damagePlayer);
                                System.out.println("Ai dat " + damagePlayer + "damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());
                                if(enemy.getCurrentHealth() > 0){
                                    enemy.attack(currCharacter);
                                    System.out.println("Mana ta ramasa: " + currCharacter.getCurrentMana() + " si viata ta: " + currCharacter.getCurrentHealth());
                                }

                            } else {
                                currCharacter.useAbility(chosenSpell, enemy);

                                System.out.println("Ai folosit abilitatea: " + chosenSpell.getClass().getSimpleName() + " si ai dat " + chosenSpell.getDamage() + " damage inamicului. El mai are: " + enemy.currentHealth + " viata.");
                                currCharacter.abilities.remove(chosenSpell);
                                if (enemy.getCurrentHealth() > 0) {
                                    enemy.attack(currCharacter);
                                    System.out.println("Mana ta ramasa: " + currCharacter.getCurrentMana() + " si viata ta: " + currCharacter.getCurrentHealth());
                                }
                            }
                        } else {
                            System.out.println("Abilitate invalida");
                        }
                    } else {
                        System.out.println("Nu ai destula mana sau abilitati disponibile.");
                        int damagePlayer = currCharacter.getDamage();
                        enemy.receiveDamage(damagePlayer);
                        System.out.println("Ai dat " + damagePlayer + "damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());
                        if(enemy.getCurrentHealth() > 0){
                            int enemyDamage = enemy.getDamage();
                            currCharacter.receiveDamage(enemyDamage);
                            System.out.println("Inamicul ti-a dat " + enemyDamage + "damage. Viata ta si mana ta: " + currCharacter.getCurrentHealth() +" " + currCharacter.getCurrentMana());
                        }
                    }
                    break;
                default:
                    System.out.println("Alegere invalida, incearca din nou!");
                    break;
            }
        }
        if(enemy.getCurrentHealth() <= 0){
            currCharacter.healToFull();
            System.out.println("Ai castigat lupta. Primesti experienta. Viata si mana ta au fost regenerate (" + currCharacter.getCurrentHealth()+", " + currCharacter.getCurrentMana() + ")");
            currCharacter.gainExperience(20);
            if(currCharacter.abilities.isEmpty()){
                currCharacter.populateAbilities();
            }
        } else if(currCharacter.getCurrentHealth() <= 0){
            System.out.println("Ai pierdut lupta! Jocul s-a terminat.");
            currCharacter.populateAbilities();
            restartGame();
        }
    }

    private void restartGame() throws InvalidCommandException, ImpossibleMove {
        System.out.println("Vrei sa alegi un cont nou sau reiei jocul din contul curent ? (1.cont nou, 2.cont curent)");
        Scanner scan= new Scanner(System.in);
        int choice = scan.nextInt();
        switch(choice){
            case 1:
                authenticateUser();
                break;
            case 2:
                currCharacter.healToFull();
                displayCharacters();
                chooseCharacter();
                createGrid();
                break;
            default:
                System.out.println("Alegere invalida, incearca din nou!");
                break;
        }
    }

    public void run() throws InvalidCommandException, ImpossibleMove {
        authenticateUser();
    }

    public static void main(String[] args) throws InvalidCommandException, ImpossibleMove {
        Game game = Game.getInstance();
        game.run();
    }
}
