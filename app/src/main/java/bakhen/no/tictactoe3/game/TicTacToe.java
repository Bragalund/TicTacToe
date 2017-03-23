package bakhen.no.tictactoe3.game;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.DBService;
import bakhen.no.tictactoe3.SQLLite.Player;
import bakhen.no.tictactoe3.Utils.CreateToast;


public class TicTacToe extends AppCompatActivity {
    private Player firstPlayer, secondPlayer;
    private boolean isAI;
    private Button topLeftBtn, topCenterBtn, topRightBtn;
    private Button centerLeftBtn, centerCenterBtn, centerRightBtn;
    private Button bottomLeftBtn, bottomCenterBtn, bottomRightBtn;
    private Button restartGameBtn;
    private ArrayList<Button> allGameButtons;
    private int counter = 1;
    private TextView userNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_game);
        initWidgets();
        initListeners();
        initPlayers();

        setPlayerNameToTextField();
    }

    private void initWidgets() {
        topLeftBtn = (Button) findViewById(R.id.topleftbutton);
        topCenterBtn = (Button) findViewById(R.id.topcenterbutton);
        topRightBtn = (Button) findViewById(R.id.toprightbutton);
        centerLeftBtn = (Button) findViewById(R.id.centerleftbutton);
        centerCenterBtn = (Button) findViewById(R.id.centercenterbutton);
        centerRightBtn = (Button) findViewById(R.id.centerrightbutton);
        bottomLeftBtn = (Button) findViewById(R.id.bottomleftbutton);
        bottomCenterBtn = (Button) findViewById(R.id.bottomcenterbutton);
        bottomRightBtn = (Button) findViewById(R.id.bottomrightbutton);

        allGameButtons = new ArrayList<>();
        allGameButtons.add(topLeftBtn);
        allGameButtons.add(topCenterBtn);
        allGameButtons.add(topRightBtn);
        allGameButtons.add(centerLeftBtn);
        allGameButtons.add(centerCenterBtn);
        allGameButtons.add(centerRightBtn);
        allGameButtons.add(bottomLeftBtn);
        allGameButtons.add(bottomCenterBtn);
        allGameButtons.add(bottomRightBtn);

        restartGameBtn = (Button) findViewById(R.id.restartGamebutton);
        restartGameBtn.setClickable(false);

        userNameTextView = (TextView) findViewById(R.id.tictactoe_game_username_textview);
    }

    private void initPlayers() {
        Intent intent = getIntent();
        String firstPlayerName = intent.getStringExtra(Intent.EXTRA_USER);
        String secondPlayerUsername = intent.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
        isAI = intent.getBooleanExtra(Intent.EXTRA_TEXT, false);
        DBService dbService = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);

        if (checkIfUserIsInDatabase(firstPlayerName)) {
            firstPlayer = dbService.getPlayer(firstPlayerName);
        } else {
            firstPlayer = new Player(null, firstPlayerName, 0, 0);
            dbService.insertPlayerIntoDatabase(firstPlayer);
        }

        if (isAI) {
            secondPlayer = new Player(null, secondPlayerUsername, 0, 0);
        } else if (checkIfUserIsInDatabase(secondPlayerUsername)) {
            secondPlayer = dbService.getPlayer(secondPlayerUsername);
        } else {
            secondPlayer = new Player(null, secondPlayerUsername, 0, 0);
            dbService.insertPlayerIntoDatabase(secondPlayer);
        }
    }

    private boolean checkIfUserIsInDatabase(String username) {
        DBService dbService = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        ArrayList<Player> players = dbService.getAllPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (username.equals(players.get(i).getUserName())) {
                return true;
            }
        }
        return false;
    }

    private void pressRandomButton() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getRandomUnclickedButton() == null) {
                    CreateToast.createToast(getApplicationContext(), "There is no clickable Buttons.");
                } else {
                    doStuffWhenButtonIsClicked(getRandomUnclickedButton());
                }
            }
        }, 1500);
    }

    private Button getRandomUnclickedButton() {
        Random random = new Random();
        if (findClickableButtons().size() > 0) {
            int randomNumber = random.nextInt(findClickableButtons().size());
            if (findClickableButtons().size() > 0) {
                return findClickableButtons().get(randomNumber);
            }
        }
        restartGameBtn.setClickable(true);
        return restartGameBtn;
    }

    private ArrayList<Button> findClickableButtons() {
        ArrayList<Button> clickableButtons = new ArrayList<>();
        for (int i = 0; i < allGameButtons.size(); i++) {
            if (allGameButtons.get(i).isClickable()) {
                clickableButtons.add(allGameButtons.get(i));
            }
        }
        return clickableButtons;
    }

    private Player getPlayingPlayer() {
        if (counter % 2 == 0) {
            return secondPlayer;
        }
        return firstPlayer;
    }

    private String getSymbol() {
        String text;
        if (getPlayingPlayer() == secondPlayer) {
            text = "O";
        } else {
            text = "X";
        }
        return text;
    }


    public void doStuffWhenButtonIsClicked(Button button) {
        changeButton(button);

        if (isWinner()) {
            setAllGameButtonsToNotClickable();
            restartGameBtn.setClickable(true);
        }
        counter++;

        setPlayerNameToTextField();

        if (getPlayingPlayer() == secondPlayer && isAI) {
            pressRandomButton();
        }
    }

    private void changeButton(Button button){
        button.setText(getSymbol());
        button.setClickable(false);
    }

    private void setAllGameButtonsToNotClickable() {
        for (Button button : allGameButtons) {
            button.setClickable(false);
        }
    }

    View.OnClickListener topLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(topLeftBtn);
        }
    };

    View.OnClickListener topCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(topCenterBtn);
        }
    };

    View.OnClickListener topRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(topRightBtn);
        }
    };

    View.OnClickListener centerLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(centerLeftBtn);
        }
    };

    View.OnClickListener centerCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(centerCenterBtn);
        }
    };

    View.OnClickListener centerRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(centerRightBtn);
        }
    };

    View.OnClickListener bottomLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(bottomLeftBtn);
        }
    };
    View.OnClickListener bottomCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(bottomCenterBtn);
        }
    };
    View.OnClickListener bottomRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStuffWhenButtonIsClicked(bottomRightBtn);
        }
    };

    View.OnClickListener restartGameBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            resetButtons();
        }
    };

    private void initListeners() {
        topLeftBtn.setOnClickListener(topLeftBtnOnClickListener);
        topCenterBtn.setOnClickListener(topCenterBtnOnClickListener);
        topRightBtn.setOnClickListener(topRightBtnOnClickListener);
        centerLeftBtn.setOnClickListener(centerLeftBtnOnClickListener);
        centerCenterBtn.setOnClickListener(centerCenterBtnOnClickListener);
        centerRightBtn.setOnClickListener(centerRightBtnOnClickListener);
        bottomLeftBtn.setOnClickListener(bottomLeftBtnOnClickListener);
        bottomCenterBtn.setOnClickListener(bottomCenterBtnOnClickListener);
        bottomRightBtn.setOnClickListener(bottomRightBtnOnClickListener);

        restartGameBtn.setOnClickListener(restartGameBtnOnClickListener);
    }

    private void resetButtons() {
        for (Button button : allGameButtons) {
            button.setText("");
            button.setBackgroundResource(android.R.drawable.btn_default);
            button.setClickable(true);
        }
    }

    private ArrayList<Button> buttonsClickedByPlayer() {
        String symbolToCheck = getSymbol();
        ArrayList<Button> buttonsClicked = new ArrayList<>();
        for (Button button : allGameButtons) {
            if (button.getText() == symbolToCheck) {
                buttonsClicked.add(button);
            }
        }
        return buttonsClicked;
    }

    private boolean isWinner() {

        //Checks all horizontal combinations
        if (buttonsClickedByPlayer().contains(topLeftBtn) && buttonsClickedByPlayer().contains(topCenterBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            colorWinningButtons(topLeftBtn, topCenterBtn, topRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(centerLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(centerRightBtn)) {
            colorWinningButtons(centerLeftBtn, centerCenterBtn, centerRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            colorWinningButtons(bottomLeftBtn, bottomCenterBtn, bottomRightBtn);
            return true;
        }

        //Checks all cross-combinations
        else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            colorWinningButtons(bottomLeftBtn, centerCenterBtn, topRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(topLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            colorWinningButtons(topLeftBtn, centerCenterBtn, bottomRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            colorWinningButtons(bottomLeftBtn, bottomCenterBtn, bottomRightBtn);
            return true;
        }

        //Checks all vertical combinations
        else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(centerLeftBtn) && buttonsClickedByPlayer().contains(topLeftBtn)) {
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(topCenterBtn)) {
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomRightBtn) && buttonsClickedByPlayer().contains(centerRightBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            return true;
        }

        return false;
    }

    private void setPlayerNameToTextField(){
        userNameTextView.setText(getPlayingPlayer().getUserName());
    }

    private void colorWinningButtons(Button... buttons) {
        for (Button button : buttons) {
            button.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }
}
