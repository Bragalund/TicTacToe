package bakhen.no.tictactoe3.Utils;

import android.content.Context;
import android.widget.Toast;

public class CreateToast {
    public static boolean validateText(Context context, String username, String secondUsername) {
        if (username.length() < 1) {
            CreateToast.createToast(context, "Username is to short.");
            return false;
        } else if (username.length() > 15) {
            createToast(context, "username to long.");
            return false;
        }else if(username.equals(secondUsername)){
            createToast(context, "Username cannot be the same as player 1");
            return false;
        }
        return true;
    }

    public static void createToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
