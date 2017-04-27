package bakhen.no.tictactoe3.game;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import java.util.ArrayList;

import bakhen.no.tictactoe3.R;

public class ButtonMethods {

    public static void colorWinningButtons(Context context, Button... buttons) {
        for (Button button : buttons) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
        }
    }

    public static void disableButton(Button button, String symbol) {
        button.setText(symbol);
        button.setClickable(false);
    }

    protected static void disableClickableButtonsForSomeTime(ArrayList<Button> allGameButtons) {
        //Disables all buttons and adds the current buttons in play into a arraylist
        //The reason is to avoid the playing player to press buttons while the computer is "thinking"
        ArrayList<Button> buttonsInPlay = new ArrayList<>();
        for (Button button : allGameButtons) {
            if (button.isClickable()) {
                buttonsInPlay.add(button);
                button.setClickable(false);
            }
        }
        //Waits a little bit to pretend that the computer is thinking
        waitForSomeTime();
        //Sets the buttons back to their state when the computer is ready to press a button
        setButtonsClickable(buttonsInPlay);
    }

    private static void waitForSomeTime() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setButtonsClickable(ArrayList<Button> buttonsInPlay) {
        for (Button button : buttonsInPlay) {
            button.setClickable(true);
        }
    }
}
