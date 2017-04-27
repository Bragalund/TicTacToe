package bakhen.no.tictactoe3.game;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.DBService;
import bakhen.no.tictactoe3.SQLLite.Player;
import bakhen.no.tictactoe3.Score.ScoreScreen;


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
    private Drawable greyColor;
    private RelativeLayout relativeLayout;
    private Button scoreScreenBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_game);

        initWidgets();
        initListeners();
        initPlayers();

        setPlayerNameToTextField();
        setBackgroundColorForPlayer();
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

        greyColor = topRightBtn.getBackground();
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        userNameTextView = (TextView) findViewById(R.id.tictactoe_game_username_textview);

        scoreScreenBtn = (Button) findViewById(R.id.tictactoe_game_score_screen_button);
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
                    ButtonMethods.disableClickableButtonsForSomeTime(allGameButtons);
                    pressGameButton(getRandomUnclickedButton());
            }
        }, 0);
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

    public void pressGameButton(Button button) {
        ButtonMethods.disableButton(button, getSymbol());
        if (isWinner()) {
            winningController();
        } else {
            continueMatch();
        }
    }

    private void setBackgroundColorForPlayer() {
        if (getPlayingPlayer() == firstPlayer) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } else if (getPlayingPlayer() == secondPlayer) {
            relativeLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.dark_red));
        }
    }

    private void changePlayer() {
        counter++;
        if (counter > 100) {
            if (getPlayingPlayer() == firstPlayer) {
                counter = 1;
            } else {
                counter = 2;
            }
        }
        setPlayerNameToTextField();
        setBackgroundColorForPlayer();
    }

    private boolean activeAI() {
        return getPlayingPlayer() == secondPlayer && isAI;
    }

    private void continueMatch() {
        changePlayer();
        if (activeAI()) {
            if(findClickableButtons().size() > 0){
                pressRandomButton();
            }else{
                restartGameButtonController();
            }
        }
    }

    private void restartGameButtonController() {
        resetButtons();
        changePlayer();
        if (activeAI()) {
            pressRandomButton();
        }
    }

    private void winningController() {
        setAllGameButtonsToNotClickable();
        setRestartButtonClickable();
        saveResultToDatabase();
        celebrateWinner();
        if (activeAI()) {
            restartGameButtonController();
        }
    }

    private void setRestartButtonClickable() {
        restartGameBtn.setClickable(true);
    }

    private void saveResultToDatabase() {
        if (getPlayingPlayer() == firstPlayer) {
            firstPlayer.setWins(firstPlayer.getWins() + 1);
            secondPlayer.setLosses(secondPlayer.getLosses() + 1);
        } else {
            secondPlayer.setWins(secondPlayer.getWins() + 1);
            firstPlayer.setLosses(firstPlayer.getLosses() + 1);
        }
        updatePlayersInDatabase();
    }

    private void updatePlayersInDatabase() {
        DBService dbservice = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        dbservice.updatePlayerInDatabase(firstPlayer);
        if (!activeAI()) {
            dbservice.updatePlayerInDatabase(secondPlayer);
        }
    }



    private void setAllGameButtonsToNotClickable() {
        for (Button button : allGameButtons) {
            button.setClickable(false);
        }
    }

    View.OnClickListener scoreScreenBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showScoreScreen();
        }
    };

    private void showScoreScreen(){
        Intent intent = new Intent(this, ScoreScreen.class);
        startActivityForResult(intent,1);
    }

    View.OnClickListener topLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(topLeftBtn);
        }
    };

    View.OnClickListener topCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(topCenterBtn);
        }
    };

    View.OnClickListener topRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(topRightBtn);
        }
    };

    View.OnClickListener centerLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(centerLeftBtn);
        }
    };

    View.OnClickListener centerCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(centerCenterBtn);
        }
    };

    View.OnClickListener centerRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(centerRightBtn);
        }
    };

    View.OnClickListener bottomLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(bottomLeftBtn);
        }
    };
    View.OnClickListener bottomCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(bottomCenterBtn);
        }
    };
    View.OnClickListener bottomRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            pressGameButton(bottomRightBtn);
        }
    };

    View.OnClickListener restartGameBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            restartGameButtonController();
        }
    };

    private void initListeners() {
        //Sets onClickListeners for all tictactoe-buttons
        topLeftBtn.setOnClickListener(topLeftBtnOnClickListener);
        topCenterBtn.setOnClickListener(topCenterBtnOnClickListener);
        topRightBtn.setOnClickListener(topRightBtnOnClickListener);
        centerLeftBtn.setOnClickListener(centerLeftBtnOnClickListener);
        centerCenterBtn.setOnClickListener(centerCenterBtnOnClickListener);
        centerRightBtn.setOnClickListener(centerRightBtnOnClickListener);
        bottomLeftBtn.setOnClickListener(bottomLeftBtnOnClickListener);
        bottomCenterBtn.setOnClickListener(bottomCenterBtnOnClickListener);
        bottomRightBtn.setOnClickListener(bottomRightBtnOnClickListener);

        //Sets onClickListener for restartGameButton
        restartGameBtn.setOnClickListener(restartGameBtnOnClickListener);
        scoreScreenBtn.setOnClickListener(scoreScreenBtnListener);
    }

    private void resetButtons() {
        for (Button button : allGameButtons) {
            button.setText("");
            button.setBackground(greyColor);
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
            ButtonMethods.colorWinningButtons(getApplicationContext(),topLeftBtn, topCenterBtn, topRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(centerLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(centerRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),centerLeftBtn, centerCenterBtn, centerRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),bottomLeftBtn, bottomCenterBtn, bottomRightBtn);
            return true;
        }

        //Checks all cross-combinations
        else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),bottomLeftBtn, centerCenterBtn, topRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(topLeftBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),topLeftBtn, centerCenterBtn, bottomRightBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(bottomRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),bottomLeftBtn, bottomCenterBtn, bottomRightBtn);
            return true;
        }

        //Checks all vertical combinations
        else if (buttonsClickedByPlayer().contains(bottomLeftBtn) && buttonsClickedByPlayer().contains(centerLeftBtn) && buttonsClickedByPlayer().contains(topLeftBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),bottomLeftBtn, centerLeftBtn, topLeftBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomCenterBtn) && buttonsClickedByPlayer().contains(centerCenterBtn) && buttonsClickedByPlayer().contains(topCenterBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(),bottomCenterBtn, centerCenterBtn, topCenterBtn);
            return true;
        } else if (buttonsClickedByPlayer().contains(bottomRightBtn) && buttonsClickedByPlayer().contains(centerRightBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            ButtonMethods.colorWinningButtons(getApplicationContext(), bottomRightBtn, centerRightBtn, topRightBtn);
            return true;
        }
        return false;
    }

    private void setPlayerNameToTextField() {
        String username;
        if (activeAI()) {
            username = getPlayingPlayer().getUserName() + " [AI]";
        } else {
            username = getPlayingPlayer().getUserName();
        }
        userNameTextView.setText(username);
    }



    private void celebrateWinner() {
        String winnerText;
        int unicode = 0x1F60A;
        if (getPlayingPlayer() == firstPlayer) {
            winnerText = getEmoji(unicode) + " " + firstPlayer.getUserName() + " wins!";
        } else if (activeAI()) {
            winnerText = "Robots Win!" + " " + getEmoji(unicode);
        } else if (getPlayingPlayer() == secondPlayer) {
            winnerText = getEmoji(unicode) + " " + secondPlayer.getUserName() + " wins!";
        } else {
            winnerText = "Something went wrong...";
        }
        userNameTextView.setText(winnerText);
    }

    public String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }

}
