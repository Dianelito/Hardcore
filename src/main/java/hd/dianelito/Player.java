package hd.dianelito;

public class Player {
    private String name;
    private int lives;
    private boolean banned;

    public Player(String name, int lives) {
        this.name = name;
        this.lives = lives;
        this.banned = false;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void decreaseLives() {
        lives--;
        if (lives <= 0) {
            banned = true;
        }
    }

    public boolean isBanned() {
        return banned;
    }
}