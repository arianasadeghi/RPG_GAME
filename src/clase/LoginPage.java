package clase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class LoginPage {
    public void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(51, 51, 51));

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailLabel.setForeground(Color.LIGHT_GRAY);
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));

        JTextField emailText = new JTextField();
        emailText.setPreferredSize(new Dimension(200, 40));
        emailText.setMaximumSize(new Dimension(200, 40));
        emailText.setForeground(Color.GRAY);
        emailText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        emailText.setBackground(Color.LIGHT_GRAY);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));

        JPasswordField passwordText = new JPasswordField();
        passwordText.setPreferredSize(new Dimension(200, 40));
        passwordText.setMaximumSize(new Dimension(200, 40));
        passwordText.setForeground(new Color(51, 51, 51));
        passwordText.setFont(new Font("Tahoma", Font.PLAIN, 18));
        passwordText.setBackground(Color.LIGHT_GRAY);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.LIGHT_GRAY);
        loginButton.setForeground(new Color(51, 51, 51));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setForeground(new Color(51, 51, 51));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.setBackground(Color.GRAY);
        inputPanel.add(emailLabel);
        inputPanel.add(emailText);
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordText);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(51, 51, 51));
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(inputPanel);
        centerPanel.add(Box.createVerticalGlue());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.LIGHT_GRAY);
        buttonPanel.setForeground(new Color(51, 51, 51));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        frame.add(centerPanel);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                authenticateUser(frame, emailText.getText(), new String(passwordText.getPassword()));
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }
    private void authenticateUser(JFrame frame, String email, String password) {
        try{
            if(email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Completati toate campurile !");
                return;
            }
            ArrayList<Account> accounts = JsonInput.deserializeAccounts();
            if(accounts.isEmpty() || accounts == null) {
                JOptionPane.showMessageDialog(frame, "Nu ai conturi");
                return;
            }
            Account account = null;
            for(Account a : accounts) {
                if(a.getInformation().getCredentials().getEmail().equals(email) && a.getInformation().getCredentials().getPassword().equals(password)) {
                    account = a;
                    break;
                }
            }
            if(account != null) {
                JOptionPane.showMessageDialog(frame, "Autentificare reusita!");
                frame.dispose();
                new PlayerDetailsWindow(account);
            } else {
                JOptionPane.showMessageDialog(frame, "Email sau parola incorecte!");
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }
    class PlayerDetailsWindow{
        private Account account;
        private JFrame frame;
        private JPanel panel;
        private Character selectedCharacter;
        private int selectedCharacterIndex = -1;

        public PlayerDetailsWindow(Account account) {
            this.account = account;
            frame = new JFrame("Player Details");
            frame.getContentPane().setBackground(Color.CYAN);
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setBackground(Color.GRAY);

            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(51, 51, 51));
            panel.setAlignmentX(Component.CENTER_ALIGNMENT);

            ArrayList<Character> characters = account.getCharacters();
            for(int i = 0; i < characters.size(); i++) {
                Character character = characters.get(i);
                JPanel characterPanel = createCharacterPanel(character, i);
                characterPanel.setBackground(new Color(51, 51, 51));
                panel.add(characterPanel);
            }
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
            frame.setVisible(true);
        }
        private JPanel createCharacterPanel(Character character, int i) {
            JPanel panel = new JPanel();
            panel.setLayout(new FlowLayout());

            String[] availableImages = {"C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\carac1.jpg", "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\caract2.jpg", "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\caract3.jpg"};
            
            JLabel imageLabel = null;
            if(i >= 0 && i < availableImages.length) {
                String imagePath = availableImages[i];
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image scaledImage = imageIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
                imageLabel = new JLabel(scaledImageIcon);
            }
            
            JLabel nameLabel = new JLabel(character.toString());
            nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
            nameLabel.setForeground(Color.LIGHT_GRAY);

            JButton selectCharacterButton = new JButton("Selecteaza acest caracter");
            selectCharacterButton.setBackground(new Color(51, 51, 51));
            selectCharacterButton.setForeground(Color.LIGHT_GRAY);
            selectCharacterButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedCharacterIndex = i;
                    selectedCharacter = character;
                    selectCharacter(character);
                }
            });

            panel.add(imageLabel);
            panel.add(nameLabel);
            panel.add(selectCharacterButton);
            return panel;
        }
        private void selectCharacter(Character character) {
            JOptionPane.showMessageDialog(frame, "Ai selectat caracterul: " + character.toString());
            selectedCharacter = character;
            frame.dispose();
            new GameWindow(account, selectedCharacter, selectedCharacterIndex);
        }
    }

    class GameWindow {
        private final Player player;
        private final Grid gameGrid;
        private JPanel boardPanel;
        private Character currCharacter;
        private Account currentAccount;
        private JFrame frame;
        private int selectedCharacterIndex;
        private JLabel healthLabel;
        private JLabel manaLabel;
        private JLabel experienceLabel;

        public GameWindow(Account account, Character selectedCharacter, int selectedCharacterIndex) {
            this.currCharacter = selectedCharacter;
            this.selectedCharacterIndex = selectedCharacterIndex;
            System.out.println("caracterul: " + selectedCharacter.toString());
            this.currentAccount = account;
            player = new Player(account);
            frame = new JFrame("Joc");

            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(new Color(51, 51, 51));

            JPanel infoPanel = new JPanel(new GridLayout(1,4, 10, 10));
            infoPanel.setPreferredSize(new Dimension(200, 50));
            infoPanel.setBackground(new Color(51, 51, 51));

            JLabel levelLabel = new JLabel("Level: " + currCharacter.getLevel());
            levelLabel.setBackground(new Color(51, 51, 51));
            levelLabel.setForeground(Color.LIGHT_GRAY);

            experienceLabel = new JLabel("Experience: " + currCharacter.getExperience());
            experienceLabel.setBackground(new Color(51, 51, 51));
            experienceLabel.setForeground(Color.LIGHT_GRAY);

            healthLabel = new JLabel("Health: " + currCharacter.getCurrentHealth());
            healthLabel.setBackground(new Color(51, 51, 51));
            healthLabel.setForeground(Color.LIGHT_GRAY);

            manaLabel = new JLabel("Mana: " + currCharacter.getCurrentMana());
            manaLabel.setBackground(new Color(51, 51, 51));
            manaLabel.setForeground(Color.LIGHT_GRAY);

            infoPanel.add(levelLabel);
            infoPanel.add(experienceLabel);
            infoPanel.add(healthLabel);
            infoPanel.add(manaLabel);

            mainPanel.add(infoPanel, BorderLayout.NORTH);

            JPanel movementPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            movementPanel.setBackground(new Color(51, 51, 51));

            JButton northButton = new JButton("North");
            northButton.setBackground(new Color(51, 51, 51));
            northButton.setForeground(Color.LIGHT_GRAY);
            northButton.setPreferredSize(new Dimension(50, 50));
            northButton.setMaximumSize(new Dimension(50, 50));

            EnemyWindow enemyWindow = new EnemyWindow(this, account, selectedCharacter);
            SanctuaryWindow sanctuaryWindow = new SanctuaryWindow(this);
            PortalWindow portalWindow = new PortalWindow();
            northButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        int nextOx = gameGrid.getCurrentCell().getOx() - 1;
                        int nextOy = gameGrid.getCurrentCell().getOy();
                        if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.SANCTUARY) {
                            System.out.println("Sanctuar detectat");
                            sanctuaryWindow.showSanctuary();
                            gameGrid.goNorth();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        } else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.PORTAL) {
                            portalWindow.showPortal();
                            gameGrid.goNorth();
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.ENEMY){
                            enemyWindow.showEnemy();
                            gameGrid.goNorth();
                            currCharacter.getCurrentHealth();
                            currCharacter.getCurrentMana();
                            currCharacter.getExperience();
                            updateCharacterStatus(currCharacter);
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        } else {
                            gameGrid.goNorth();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }
                    } catch (ImpossibleMove ex){
                        showErrorMessage(ex.getMessage());
                    }
                }
            });

            JButton southButton = new JButton("South");
            southButton.setBackground(new Color(51, 51, 51));
            southButton.setForeground(Color.LIGHT_GRAY);
            southButton.setPreferredSize(new Dimension(50, 50));
            southButton.setMaximumSize(new Dimension(50, 50));

            southButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int nextOx = gameGrid.getCurrentCell().getOx() + 1;
                        int nextOy = gameGrid.getCurrentCell().getOy();
                        if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.SANCTUARY) {
                            System.out.println("Sanctuar detectat");
                            sanctuaryWindow.showSanctuary();
                            gameGrid.goSouth();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.PORTAL) {
                            portalWindow.showPortal();
                            gameGrid.goSouth();
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.ENEMY){
                            enemyWindow.showEnemy();
                            gameGrid.goSouth();
                            currCharacter.getCurrentHealth();
                            currCharacter.getCurrentMana();
                            currCharacter.getExperience();
                            updateCharacterStatus(currCharacter);
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        } else {
                            gameGrid.goSouth();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }
                    } catch (ImpossibleMove ex){
                        showErrorMessage(ex.getMessage());
                    }
                }
            });

            JButton westButton = new JButton("West");
            westButton.setBackground(new Color(51, 51, 51));
            westButton.setForeground(Color.LIGHT_GRAY);
            westButton.setPreferredSize(new Dimension(50, 50));
            westButton.setMaximumSize(new Dimension(50, 50));

            westButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        int nextOx = gameGrid.getCurrentCell().getOx();
                        int nextOy = gameGrid.getCurrentCell().getOy() - 1;
                        if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.SANCTUARY) {
                            System.out.println("Sanctuar detectat");
                            sanctuaryWindow.showSanctuary();
                            gameGrid.goWest();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.PORTAL) {
                            portalWindow.showPortal();
                            gameGrid.goWest();
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.ENEMY){
                            enemyWindow.showEnemy();
                            gameGrid.goWest();
                            currCharacter.getCurrentHealth();
                            currCharacter.getCurrentMana();
                            currCharacter.getExperience();
                            updateCharacterStatus(currCharacter);
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                        } else {
                            gameGrid.goWest();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }
                    } catch (ImpossibleMove ex){
                        showErrorMessage(ex.getMessage());
                    }
                }
            });

            JButton eastButton = new JButton("East");
            eastButton.setBackground(new Color(51, 51, 51));
            eastButton.setForeground(Color.LIGHT_GRAY);
            eastButton.setPreferredSize(new Dimension(50, 50));
            eastButton.setMaximumSize(new Dimension(50, 50));

            eastButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int nextOx = gameGrid.getCurrentCell().getOx();
                        int nextOy = gameGrid.getCurrentCell().getOy() + 1;
                        if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.SANCTUARY) {
                            System.out.println("Sanctuar detectat");
                            sanctuaryWindow.showSanctuary();
                            gameGrid.goEast();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.PORTAL) {
                            portalWindow.showPortal();
                            gameGrid.goEast();
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }else if(gameGrid.getNextCell(nextOx, nextOy).getType() == CellEntityType.ENEMY){
                            enemyWindow.showEnemy();
                            gameGrid.goEast();
                            currCharacter.getCurrentHealth();
                            currCharacter.getCurrentMana();
                            currCharacter.getExperience();
                            updateCharacterStatus(currCharacter);
                            frame.dispose();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        } else {
                            gameGrid.goEast();
                            boardPanel.removeAll();
                            JPanel gameGrid1 = gameGrid.getGridPanel();
                            gameGrid1.setBackground(new Color(51, 51, 51));
                            gameGrid1.setPreferredSize(new Dimension(500, 500));
                            boardPanel.add(gameGrid1);
                            boardPanel.revalidate();
                            boardPanel.repaint();
                        }
                    } catch (ImpossibleMove ex){
                        showErrorMessage(ex.getMessage());
                    }
                }
            });

            movementPanel.add(northButton);
            movementPanel.add(southButton);
            movementPanel.add(westButton);
            movementPanel.add(eastButton);

            mainPanel.add(movementPanel, BorderLayout.SOUTH);

            boardPanel = new JPanel();
            boardPanel.setLayout(new GridBagLayout());
            boardPanel.setBackground(new Color(51, 51, 51));

            Random rand = new Random();
            int length = rand.nextInt(7) + 4;
            int width = rand.nextInt(7) + 4;
            gameGrid = Grid.createGrid(length, width);
            JPanel gridPanel = gameGrid.getGridPanel();
            gridPanel.setBackground(new Color(51, 51, 51));
            gridPanel.setPreferredSize(new Dimension(500, 500));
            boardPanel.add(gridPanel);

            mainPanel.add(boardPanel, BorderLayout.CENTER);

            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        public void updateCharacterStatus(Character character){
            healthLabel.setText("Health: " + character.getCurrentHealth());
            manaLabel.setText("Mana: " + character.getCurrentMana());
            experienceLabel.setText("Experience: " + character.getExperience());

            healthLabel.getParent().revalidate();
            healthLabel.getParent().repaint();
            manaLabel.getParent().revalidate();
            manaLabel.getParent().repaint();
            experienceLabel.getParent().revalidate();
            experienceLabel.getParent().repaint();
        }

        public int getSelectedCharacterIndex(){
            return selectedCharacterIndex;
        }

        private void showErrorMessage(String message){
            JOptionPane.showMessageDialog(null, "Mutare invalida!");
        }

        public void close(){
            frame.dispose();
        }

        public void setVisible(boolean b){
            frame.setVisible(b);
        }

        class EnemyWindow{
            private GameWindow gameWindow;
            private JFrame enemyFrame;
            private JLabel enemyHealth;
            private JLabel charHealth;
            private Character currCharacter;
            private Enemy enemy;
            private Account currentAccount;

            public EnemyWindow(GameWindow gameWindow, Account currentAccount, Character currentCharacter){
                this.gameWindow = gameWindow;

                this.currentAccount = currentAccount;
                this.currCharacter = currentCharacter;
                Random rand = new Random();
                int maxMana = rand.nextInt(31) + 20;
                int maxHealth = rand.nextInt(51) + 50;
                this.enemy = new Enemy(maxHealth, maxMana, this);

                enemy.populateAbilities();
                currCharacter.populateAbilities();
            }
            public void showEnemy(){
                enemyFrame = new JFrame();
                enemyFrame.setSize(1000,1000);
                enemyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                enemyFrame.setBackground(new Color(51, 51, 51));
                enemyFrame.setLayout(new BorderLayout());

                JPanel infoPanel = new JPanel(new GridLayout(2,1, 0, 10));
                infoPanel.setBackground(new Color(51, 51, 51));
                infoPanel.setForeground(Color.LIGHT_GRAY);
                infoPanel.setFont(new Font("Arial", Font.BOLD, 20));
                infoPanel.setPreferredSize(new Dimension(30, 100));

                enemyHealth = new JLabel(getEnemyStats(enemy));
                enemyHealth.setPreferredSize(new Dimension(100, 10));
                enemyHealth.setForeground(Color.LIGHT_GRAY);
                charHealth = new JLabel(getCharacterStats(currCharacter));
                charHealth.setForeground(Color.LIGHT_GRAY);
                charHealth.setPreferredSize(new Dimension(100, 10));

                infoPanel.add(enemyHealth);
                infoPanel.add(charHealth);

                JPanel imagePanel = new JPanel(new GridLayout(1, 2));
                imagePanel.setBackground(new Color(51, 51, 51));

                String characterImagePath = "";
                switch (gameWindow.getSelectedCharacterIndex()){
                    case 0:
                        characterImagePath = "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\carac1.jpg";
                        break;
                    case 1:
                        characterImagePath = "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\caract2.jpg";
                        break;
                    case 2:
                        characterImagePath = "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\caract3.jpg";
                        break;
                }

                ImageIcon characterImage = new ImageIcon(characterImagePath);
                Image scaledCharacter = characterImage.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                JLabel characterImageLabel = new JLabel(new ImageIcon(scaledCharacter));

                String[] enemyImages = {"C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\enemy1.jpg", "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\enemy2.jpg", "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\enemy3.jpg", "C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\enemy4.jpg"};
                Random rand = new Random();
                String enemyImagePath = enemyImages[rand.nextInt(enemyImages.length)];
                ImageIcon enemyImageIcon = new ImageIcon(enemyImagePath);
                Image enemyImage = enemyImageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                JLabel enemyImageLabel = new JLabel(new ImageIcon(enemyImage));

                imagePanel.add(enemyImageLabel, BorderLayout.WEST);
                imagePanel.add(characterImageLabel, BorderLayout.EAST);

                JPanel buttonPanel = new JPanel(new FlowLayout());
                buttonPanel.setBackground(new Color(51, 51, 51));
                buttonPanel.setForeground(Color.LIGHT_GRAY);
                JButton attackButton = new JButton("ATTACK");
                attackButton.setBackground(new Color(51, 51, 51));
                attackButton.setFont(new Font("Tahoma", Font.BOLD, 21));
                attackButton.setForeground(Color.LIGHT_GRAY);
                JButton abilityButton = new JButton("USE ABILITY");
                abilityButton.setBackground(new Color(51, 51, 51));
                abilityButton.setFont(new Font("Tahoma", Font.BOLD, 21));
                abilityButton.setForeground(Color.LIGHT_GRAY);

                buttonPanel.add(attackButton);
                buttonPanel.add(abilityButton);

                attackButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleAttack();
                    }
                });

                abilityButton.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handleUseAbility();
                    }
                });

                JPanel southPanel = new JPanel(new BorderLayout());
                southPanel.setBackground(new Color(51, 51, 51));
                southPanel.setForeground(Color.LIGHT_GRAY);
                southPanel.add(infoPanel, BorderLayout.NORTH);
                southPanel.add(buttonPanel, BorderLayout.SOUTH);

                enemyFrame.add(southPanel, BorderLayout.SOUTH);
                enemyFrame.add(imagePanel);

                enemyFrame.setLocationRelativeTo(null);
                enemyFrame.setVisible(true);
            }

            private void handleAttack(){
                int playerDamage = currCharacter.getDamage();
                enemy.receiveDamage(playerDamage);
                JOptionPane.showMessageDialog(enemyFrame, "Ai dat " + playerDamage + " damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());
                updateStats();
                if(enemy.getCurrentHealth() > 0){
                    int enemyDamage = enemy.getDamage();
                    currCharacter.receiveDamage(enemyDamage);
                    JOptionPane.showMessageDialog(enemyFrame, "Inamicul ti-a dat " + enemyDamage + " damage. Viata ta: " + currCharacter.getCurrentHealth());
                }
                updateStats();
                checkBattleOutcome();
            }

            private void checkBattleOutcome(){
                if(enemy.getCurrentHealth() <= 0){
                    JOptionPane.showMessageDialog(enemyFrame, "Ai castigat lupta! Primesti experienta.");
                    currCharacter.gainExperience(10);
                    enemy.healToFull();
                    updateStats();
                    enemyFrame.dispose();
                    gameWindow.setVisible(true);
                } else if(currCharacter.getCurrentHealth() <= 0){
                    JOptionPane.showMessageDialog(enemyFrame, "Ai pierdut lupta! Jocul s-a terminat.");
                    enemyFrame.dispose();
                    enemy.populateAbilities();
                    showRestartMessage();
                }
            }

            private void showRestartMessage(){
                JFrame restartFrame = new JFrame("Restart Game");
                restartFrame.setSize(300, 150);
                restartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                restartFrame.setLocationRelativeTo(null);
                restartFrame.setLayout(new BorderLayout());

                JLabel messageLabel = new JLabel("Alegeți opțiunea:", SwingConstants.CENTER);
                messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                restartFrame.add(messageLabel, BorderLayout.NORTH);

                JPanel buttonPanel = new JPanel(new FlowLayout());
                JButton newAccountButton = new JButton("Cont nou");
                JButton currentAccountButton = new JButton("Cont curent");
                newAccountButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartFrame.dispose();
                        gameWindow.close();
                        new LoginPage().main(new String[]{});
                    }
                });

                currentAccountButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        restartFrame.dispose();
                        currCharacter.healToFull();
                        gameWindow.close();
                        new PlayerDetailsWindow(currentAccount);
                    }
                });

                buttonPanel.add(newAccountButton);
                buttonPanel.add(currentAccountButton);

                restartFrame.add(buttonPanel, BorderLayout.CENTER);
                restartFrame.setVisible(true);
            }

            private void handleUseAbility(){
                if(currCharacter.getCurrentMana() > 0 && !currCharacter.abilities.isEmpty()){
                    String[] abilityOptions = new String[currCharacter.abilities.size()];
                    for(int i = 0; i < currCharacter.abilities.size(); i++){
                        abilityOptions[i] = currCharacter.abilities.get(i).getClass().getSimpleName();
                    }

                    String chosenAbility = (String) JOptionPane.showInputDialog(enemyFrame, "Alege o abilitate: ", "Foloseste abilitate", JOptionPane.PLAIN_MESSAGE, null, abilityOptions, abilityOptions[0]);
                    if(chosenAbility != null){
                        Spell chosenSpell = null;
                        for(Spell spell : currCharacter.abilities){
                            if(spell.getClass().getSimpleName().equals(chosenAbility)){
                                chosenSpell = spell;
                                break;
                            }
                        }
                        if(chosenSpell != null && chosenSpell.getMana() <= currCharacter.getCurrentMana()){
                            currCharacter.useAbility(chosenSpell, enemy);
                            if(chosenSpell instanceof Fire && enemy.immuneToFire){
                                JOptionPane.showMessageDialog(enemyFrame,"Inamicul este imun la foc! Abilitatea " + chosenSpell.getClass().getSimpleName() + " nu va cauza daune.");
                            } else if(chosenSpell instanceof Ice && enemy.immuneToIce) {
                                JOptionPane.showMessageDialog(enemyFrame, "Inamicul este imun la gheata! Abilitatea " + chosenSpell.getClass().getSimpleName() + " nu va cauza daune.");
                            } else if(chosenSpell instanceof Earth && enemy.immuneToEarth) {
                                JOptionPane.showMessageDialog(enemyFrame, "Inamicul este imun la pamant! Abilitatea " + chosenSpell.getClass().getSimpleName() + " nu va cauza daune.");
                            } else {
                                JOptionPane.showMessageDialog(enemyFrame, "Ai folosit abilitate " + chosenAbility + " si ai dat " + chosenSpell.getDamage() + " damage inamicului.");
                            }
                            currCharacter.abilities.remove(chosenSpell);
                            updateStats();
                            if(enemy.getCurrentHealth() > 0){
                                enemy.attack(currCharacter);
                                updateStats();
                            }
                        } else {
                            JOptionPane.showMessageDialog(enemyFrame,"Nu ai destula mana pentru a folosi aceasta abilitate. Se va folosi un atac nornal");
                            int damage = currCharacter.getDamage();
                            currCharacter.receiveDamage(damage);
                            JOptionPane.showMessageDialog(enemyFrame, "Ai dat " + damage + " damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());
                            updateStats();
                            if(enemy.getCurrentHealth() > 0) {
                                enemy.attack(currCharacter);
                                updateStats();
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(enemyFrame, "Nu ai selectat nicio abilitate.");
                    }
                } else {
                    JOptionPane.showMessageDialog(enemyFrame,"Nu ai destula mana pentru a folosi aceasta abilitate. Se va folosi un atac nornal");
                    int damage = currCharacter.getDamage();
                    currCharacter.receiveDamage(damage);
                    JOptionPane.showMessageDialog(enemyFrame, "Ai dat " + damage + " damage inamicului. Viata inamicului: " + enemy.getCurrentHealth());
                    updateStats();

                    if(enemy.getCurrentHealth() > 0) {
                        enemy.attack(currCharacter);
                        updateStats();
                    }
                }
                updateStats();
                checkBattleOutcome();
            }
            private void updateStats(){
                charHealth.setText(getCharacterStats(currCharacter));
                enemyHealth.setText(getEnemyStats(enemy));
            }
            private String getCharacterStats(Character character){
                return "Player - Viata: " + currCharacter.getCurrentHealth() + " si mana: " + currCharacter.getCurrentMana();
            }
            private String getEnemyStats(Enemy enemy){
                return "Inamic - Viata: " + enemy.getCurrentHealth() + " si mana " + enemy.getCurrentMana();
            }
            public void showMessage(String message){
                JOptionPane.showMessageDialog(frame,message);
            }
        }


        class SanctuaryWindow {
            private GameWindow gameWindow;
            private JFrame sanctuaryFrame;

            public SanctuaryWindow(GameWindow gameWindow){
                this.gameWindow = gameWindow;
            }
            public void showSanctuary(){
                sanctuaryFrame = new JFrame("Sanctuar");
                sanctuaryFrame.setSize(700, 700);
                sanctuaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                sanctuaryFrame.setLocationRelativeTo(null);

                JLabel messageLabel = new JLabel("Ai ajuns pe un sanctuar!", SwingConstants.CENTER);
                messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
                messageLabel.setForeground(Color.LIGHT_GRAY);
                messageLabel.setBackground(new Color(51, 51, 51));

                JPanel messagePanel = new JPanel(new GridBagLayout());
                messagePanel.setBackground(new Color(51, 51, 51));
                messagePanel.setPreferredSize(new Dimension(100, 100));
                messagePanel.setMaximumSize(new Dimension(100, 100));
                messagePanel.add(messageLabel);

                ImageIcon gifIcon = new ImageIcon("C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\sanctuar.gif");

                JLabel gifLabel = new JLabel(gifIcon);
                gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                JPanel sanctuaryPanel = new JPanel(new GridBagLayout());
                sanctuaryPanel.setBackground(new Color(51, 51, 51));
                sanctuaryPanel.add(gifLabel);

                JLabel regenLabel = new JLabel("Viata si mana ta au fost regenerate!");
                regenLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
                regenLabel.setForeground(Color.LIGHT_GRAY);
                regenLabel.setBackground(new Color(51, 51, 51));
                regenLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JButton continueButton = new JButton("Continua jocul");
                continueButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
                continueButton.setForeground(Color.LIGHT_GRAY);
                continueButton.setBackground(new Color(51, 51, 51));

                continueButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currCharacter.regenerateHealth(50);
                        currCharacter.regenerateMana(50);
                        sanctuaryFrame.dispose();
                        gameWindow.setVisible(true);
                    }
                });

                JPanel regenPanel = new JPanel(new BorderLayout());
                regenPanel.setBackground(new Color(51, 51, 51));
                regenPanel.add(regenLabel, BorderLayout.WEST);
                regenPanel.add(continueButton, BorderLayout.EAST);

                sanctuaryFrame.setLayout(new BorderLayout());
                sanctuaryFrame.add(messagePanel, BorderLayout.NORTH);
                sanctuaryFrame.add(sanctuaryPanel, BorderLayout.CENTER);
                sanctuaryFrame.add(regenPanel, BorderLayout.SOUTH);
                sanctuaryFrame.setVisible(true);
            }
        }
        
        class PortalWindow{
            private JFrame portalFrame;
            
            public void showPortal(){
                portalFrame = new JFrame("Portal");
                portalFrame.setSize(700, 700);
                portalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                portalFrame.setLocationRelativeTo(null);

                JLabel messageLabel = new JLabel("Felicitari ai ajuns la portal, treci la nivelul urmator!", SwingConstants.CENTER);
                messageLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
                messageLabel.setForeground(Color.LIGHT_GRAY);
                messageLabel.setBackground(new Color(51, 51, 51));

                JPanel messagePanel = new JPanel(new GridBagLayout());
                messagePanel.setBackground(new Color(51, 51, 51));
                messagePanel.setPreferredSize(new Dimension(100, 100));
                messagePanel.setMaximumSize(new Dimension(100, 100));
                messagePanel.add(messageLabel);

                ImageIcon gifIcon = new ImageIcon("C:\\Users\\Aryana\\Desktop\\teme_POO\\Tema1\\portal.gif");

                JLabel gifLabel = new JLabel(gifIcon);
                gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                JPanel sanctuaryPanel = new JPanel(new GridBagLayout());
                sanctuaryPanel.setBackground(new Color(51, 51, 51));
                sanctuaryPanel.add(gifLabel);

                JLabel regenLabel = new JLabel("Viata si mana ta au fost regenerate la capacitate maxima!");
                regenLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
                regenLabel.setForeground(Color.LIGHT_GRAY);
                regenLabel.setBackground(new Color(51, 51, 51));
                regenLabel.setHorizontalAlignment(SwingConstants.CENTER);

                JButton continueButton = new JButton("Continua jocul");
                continueButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
                continueButton.setForeground(Color.LIGHT_GRAY);
                continueButton.setBackground(new Color(51, 51, 51));

                continueButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        currCharacter.levelUp();
                        currCharacter.healToFull();
                        portalFrame.dispose();
                        new GameWindow(currentAccount, currCharacter, selectedCharacterIndex);
                    }
                });

                JPanel regenPanel = new JPanel(new BorderLayout());
                regenPanel.setBackground(new Color(51, 51, 51));
                regenPanel.add(regenLabel, BorderLayout.WEST);
                regenPanel.add(continueButton, BorderLayout.EAST);

                portalFrame.setLayout(new BorderLayout());
                portalFrame.add(messagePanel, BorderLayout.NORTH);
                portalFrame.add(sanctuaryPanel, BorderLayout.CENTER);
                portalFrame.add(regenPanel, BorderLayout.SOUTH);
                portalFrame.setVisible(true);
            }
        }

        class Player {
            private final Character character;

            public Player(Account account) {
                this.character = account.getCharacters().get(0);
            }

            public int getLevel() {
                return character.getLevel();
            }

            public int getExperience() {
                return character.getExperience();
            }

            public int getHealth() {
                return character.getCurrentHealth();
            }

            public int getMana() {
                return character.getCurrentMana();
            }
        }
    }
}

