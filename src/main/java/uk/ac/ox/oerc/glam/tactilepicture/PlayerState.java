package uk.ac.ox.oerc.glam.tactilepicture;

/**
 * Created by iain.emsley on 20/08/2017.
 */

public class PlayerState {

    public enum PlayerStates{PAUSED,STOPPED,PLAYING};
    PlayerStates state;

    public int currPos = 0;

    public void setState(PlayerStates update) {
        state = update;
    }

    public PlayerStates getState(){
        return state;
    }

    public void setPosition(int position) {
        currPos = position;
    }

    public int getPosition(){
        return currPos;
    }
}
