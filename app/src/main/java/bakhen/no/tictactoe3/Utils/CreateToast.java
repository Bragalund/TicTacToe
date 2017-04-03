package bakhen.no.tictactoe3.Utils;

import android.content.Context;
import android.widget.Toast;

public class CreateToast {
    public static boolean validateText(Context context, String firstUsername, String secondUsername, boolean firstUsernameIsChecked) {

        if (firstUsernameIsChecked) {
            if (verifyUsername(context, secondUsername)) {
                if (firstUsername.equals(secondUsername)) {
                    createToast(context, "Usernames cannot be the same.");
                    return false;
                } else {
                    return true;
                }
            }

        } else if (!firstUsernameIsChecked) {
            if (verifyUsername(context, firstUsername)) {
                return true;
            }
        }
        return false;
    }

    public static void createToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static boolean verifyUsername(Context context, String username) {
        if (username.length() < 1) {
            CreateToast.createToast(context, "Username is too short.");
            return false;
        } else if (username.length() > 15) {
            createToast(context, "username too long.");
            return false;
        }
        return true;
    }
}
