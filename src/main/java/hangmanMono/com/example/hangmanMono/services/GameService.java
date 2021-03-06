package hangmanMono.com.example.hangmanMono.services;

import hangmanMono.com.example.hangmanMono.library.Hangman;
import hangmanMono.com.example.hangmanMono.model.*;
import hangmanMono.com.example.hangmanMono.repository.GameRepository;
import hangmanMono.com.example.hangmanMono.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final SecretWordService secretWordService;

    @Autowired
    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, SecretWordService secretWordService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.secretWordService = secretWordService;
    }

    public ResponseToGuess guess(Guess guess) {
        // TODO rename ResponseToGuess to guess?
        Optional<ResponseToGuess> game = gameRepository.findById(guess.getGameId());
        System.out.println("****" + game.get());

        if (game.isPresent()){
            Hangman hangman = new Hangman(game.get().getSecretWord());
            String resultOfGuess = hangman.guess(guess.getLetter());
            int numberOfIncorrectGuesses = hangman.getNumberOfGuessesLeft();
            boolean isGameInProgress = hangman.isGameInProgress();
        }

        // Continue adding on
        game.get().setGameInProgress(isGameInProgress);

        return game.get();
    }

    public StartGameResponse startTheGame(StartGameRequest startGameRequest) {
        Long playerId = startGameRequest.getPlayerId();

        boolean gameInProgress = startGameRequest.getGameInProgress();

        secretWordService.randomizeSecretWord();

        String secretWord = secretWordService.getSecretWord();

        Optional<Player> player = playerRepository.findById(playerId);

        if (player.isPresent()){
            ResponseToGuess responseToGuess = new ResponseToGuess(secretWord, player.get(), gameInProgress);

            try {
                ResponseToGuess savedGame = gameRepository.save(responseToGuess);
                System.out.println(savedGame);
                return new StartGameResponse(secretWord.length(), savedGame.getGameId());
            } catch (NullPointerException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "Cannot save the game"
                );
            }

        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "player not found"
            );
        }
    }
}
