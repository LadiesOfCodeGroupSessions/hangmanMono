package hangmanMono.com.example.hangmanMono.model;

import javax.persistence.*;
import java.util.ArrayList;


@Entity(name = "GAME")
@Table
public class ResponseToGuess {
    // TODO decide whether we need the numberOfIncorrectGuesses here because we also have it in Hangman
    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    private Long gameId;

    @OneToOne(cascade=CascadeType.ALL) //one-to-one
    @JoinColumn(name="PLAYER_ID")
    private Player player;

    private String secretWord;
    private boolean gameInProgress;
    private int numberOfIncorrectGuesses;

    @Transient
    private ArrayList<Letter> incorrectLetters;
    @Transient
    private ArrayList<Letter> correctLetters;

    public ResponseToGuess() {
    }

    public ResponseToGuess(String secretWord, Player player, boolean gameInProgress){
        this.secretWord = secretWord;
        this.player = player;
        this.gameInProgress = gameInProgress;
    }

    public ResponseToGuess(int numberOfIncorrectGuesses, boolean gameInProgress, ArrayList<Letter> incorrectLetters, ArrayList<Letter> correctLetters) {
        this.numberOfIncorrectGuesses = numberOfIncorrectGuesses;
        this.gameInProgress = gameInProgress;
        this.incorrectLetters = incorrectLetters;
        this.correctLetters = correctLetters;
    }

    public int getNumberOfIncorrectGuesses() {
        return numberOfIncorrectGuesses;
    }

    public void setNumberOfIncorrectGuesses(int numberOfIncorrectGuesses) {
        this.numberOfIncorrectGuesses = numberOfIncorrectGuesses;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }

    public ArrayList<Letter> getIncorrectLetters() {
        return incorrectLetters;
    }

    public void setIncorrectLetters(ArrayList<Letter> incorrectLetters) {
        this.incorrectLetters = incorrectLetters;
    }

    public ArrayList<Letter> getCorrectLetters() {
        return correctLetters;
    }

    public void setCorrectLetters(ArrayList<Letter> correctLetters) {
        this.correctLetters = correctLetters;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getSecretWord() {
        return secretWord;
    }
}
