package bakhen.no.tictactoe3.game;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    private ArrayList<Button> buttons;
    private int counter = 1;

    //TODO listeners crasher appen. Finn ut av hvorfor de ikke fungerer!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tictactoe_game);
        initWidgets();
        initListeners();
        initPlayers();
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

        buttons = new ArrayList<>();
        buttons.add(topLeftBtn);
        buttons.add(topCenterBtn);
        buttons.add(topRightBtn);
        buttons.add(centerLeftBtn);
        buttons.add(centerCenterBtn);
        buttons.add(centerRightBtn);
        buttons.add(bottomLeftBtn);
        buttons.add(bottomCenterBtn);
        buttons.add(bottomRightBtn);

        restartGameBtn = (Button) findViewById(R.id.restartGamebutton);
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
            Player firstPlayer = new Player(null, firstPlayerName, 0, 0);
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
                    CreateToast.createToast(getApplicationContext(), "There is no clickable buttons.");
                } else {
                    changeButton(getRandomUnclickedButton());
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
        return restartGameBtn;
    }

    private ArrayList<Button> findClickableButtons() {
        ArrayList<Button> clickableButtons = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).isClickable()) {
                clickableButtons.add(buttons.get(i));
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


    public void changeButton(Button button) {
        button.setText(getSymbol());
        button.setClickable(false);
        counter++;

        if (getPlayingPlayer() == secondPlayer) {
            if (isAI) {
                pressRandomButton();
            }
        }
    }

    View.OnClickListener topLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(topLeftBtn);
        }
    };

    View.OnClickListener topCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(topCenterBtn);
        }
    };

    View.OnClickListener topRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(topRightBtn);
        }
    };

    View.OnClickListener centerLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(centerLeftBtn);
        }
    };

    View.OnClickListener centerCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(centerCenterBtn);
        }
    };

    View.OnClickListener centerRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(centerRightBtn);
        }
    };

    View.OnClickListener bottomLeftBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(bottomLeftBtn);
        }
    };
    View.OnClickListener bottomCenterBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(bottomCenterBtn);
        }
    };
    View.OnClickListener bottomRightBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            changeButton(bottomRightBtn);
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
        for (Button button : buttons) {
            button.setText("");
            button.setClickable(true);
        }
    }

    private ArrayList<Button> buttonsClickedByPlayer() {
        String symbolToCheck = getSymbol();
        ArrayList<Button> buttonsClicked = new ArrayList<>();
        for (Button button : buttons) {
            if (button.getText() == symbolToCheck) {
                buttonsClicked.add(button);
            }
        }
        return buttonsClicked;
    }

    private boolean isWinner() {
        if (buttonsClickedByPlayer().contains(topLeftBtn) && buttonsClickedByPlayer().contains(topCenterBtn) && buttonsClickedByPlayer().contains(topRightBtn)) {
            return true;
        }
        return false;
    }

    private ArrayList<Button> getWinningButtons() {
        ArrayList<Button> winningButtons = new ArrayList<>();
        for (Button button : buttons) {
            winningButtons.add(button);
        }
        return winningButtons;
    }
}
