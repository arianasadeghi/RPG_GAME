package clase;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class Account {
    private Information information;
    private ArrayList<Character> characters;
    private int nrGames;


    public Account(ArrayList<Character> characters, int gamesNumber, Information information) {
        this.information = information;
        this.nrGames = gamesNumber;
        this.characters = characters;
    }

    public Account() {
        super();
    }

    public Information getInformation() {
        return information;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }
    public ArrayList<Character> getCharacters() {
        return characters;
    }
    public int getNrGames() {
        return nrGames;
    }
    public void increaseNrGames() {
        nrGames++;
    }

    @Override
    public String toString() {
        return information + ", characters=" + characters + ", gamesPlayed=" + nrGames + '}';
    }

    static class Information {
        private Credentials credentials;
        private TreeSet<String> favoriteGames;
        private String name;
        private String country;

        public Information(Credentials credentials, String name, String country, SortedSet<String> favoriteGames) {
            this.credentials = credentials;
            this.favoriteGames = (TreeSet<String>) favoriteGames;
            this.name = name;
            this.country = country;
        }
        public void addFavGame(String game) {
            favoriteGames.add(game);
        }
        public Credentials getCredentials() {
            return credentials;
        }
        public TreeSet<String> getFavGames() {
            return favoriteGames;
        }
        public String getName() {
            return name;
        }
        public String getCountry() {
            return country;
        }
        @Override
        public String toString() {
            return "Info{ name=" + name +", favGames=" + favoriteGames +  ", country=" + country;
        }
        public static class Builder {
            private Credentials credentials;
            private String name;
            private String country;
            private SortedSet<String> favGames = new TreeSet<>();

            public Builder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setCountry(String country) {
                this.country = country;
                return this;
            }

            public Builder setFavoriteGames(SortedSet<String> favoriteGames) {
                this.favGames = favoriteGames;
                return this;
            }

            public Builder addFavoriteGame(String game) {
                this.favGames.add(game);
                return this;
            }

            public Information build() {
                return new Information(credentials, name, country, favGames);
            }
        }
    }
}
