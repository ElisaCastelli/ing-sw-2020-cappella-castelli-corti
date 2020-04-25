package it.polimi.ingsw.model.gameState;

public class GameStateManager {
    private final GameState going;
    private final GameState pause;
    private final GameState ready;
    private final GameState end;
    private GameState currentState;

    public GameStateManager(){
        going = new GoingState();
        pause = new PauseState();
        ready = new ReadyState();
        end = new EndState();
        currentState = ready;
    }


}
