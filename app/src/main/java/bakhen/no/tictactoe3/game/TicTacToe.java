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
                    getRandomUnclickedButton().performClick();
                }
            }
        }, 2000);
    }

    private Button getRandomUnclickedButton() {
        Random random = new Random();
        int randomNumber = random.nextInt(buttons.size());
        if (findClickableButtons().size() > 0) {
            return findClickableButtons().get(randomNumber);
        }
        return null; //Could tap the finish button
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

    private Player getPlayingPlayer(){
        if(counter % 2 == 0){
            return secondPlayer;
        }
        return firstPlayer;
    }

    private String getSymbol() {
        String text = "";
        if (getPlayingPlayer().equals(secondPlayer)) {
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
    }

    public void initListeners() {
        topLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(topLeftBtn);
            }
        });
        topCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(topCenterBtn);
            }
        });
        topRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(topRightBtn);
            }
        });
        centerLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(centerLeftBtn);
            }
        });
        centerCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(centerCenterBtn);
            }
        });
        centerRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(centerRightBtn);
            }
        });
        bottomLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(bottomLeftBtn);
            }
        });
        bottomCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(bottomCenterBtn);
            }
        });
        bottomRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeButton(bottomRightBtn);
            }
        });

    }
}
