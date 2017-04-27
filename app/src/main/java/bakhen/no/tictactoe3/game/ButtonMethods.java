package bakhen.no.tictactoe3.game;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import bakhen.no.tictactoe3.R;

public class ButtonMethods {

    public static void colorWinningButtons(Context context, Button... buttons) {
        for (Button button : buttons) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
        }
    }
}
