package bakhen.no.tictactoe3.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Switch;

import java.util.ArrayList;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.DBService;
import bakhen.no.tictactoe3.SQLLite.Player;
import bakhen.no.tictactoe3.Score.ScoreScreen;
import bakhen.no.tictactoe3.Utils.CreateToast;
import bakhen.no.tictactoe3.game.TicTacToe;

public class SecondPlayerLogin extends AppCompatActivity implements TextWatcher {
    private Switch AISwitch;
    private AutoCompleteTextView secondPlayerName;
    private String firstPlayerName;
    private ArrayAdapter adapter;
    private Button scoreScreenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_player_login);
        initWidgets();
        initAdapter();
        firstPlayerName = getFirstPlayerName(savedInstanceState);
    }

    private void initWidgets() {
        AISwitch = (Switch) findViewById(R.id.AISwitch);
        secondPlayerName = (AutoCompleteTextView) findViewById(R.id.login_second_player_edittext);
        scoreScreenBtn = (Button) findViewById(R.id.second_player_login_score_screen_button);
        scoreScreenBtn.setOnClickListener(scoreScreenBtnListener);
    }

    public void startGame(View v) {
        if (CreateToast.validateText(getApplicationContext(), firstPlayerName, secondPlayerName.getText().toString(), true)) {
            Intent intent = new Intent(this, TicTacToe.class);
            intent.putExtra(Intent.EXTRA_USER, firstPlayerName);
            intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, secondPlayerName.getText().toString());

            if (AISwitch.isChecked()) {
                intent.putExtra(Intent.EXTRA_TEXT, true);
            }

            startActivity(intent);
        }
    }

    private String getFirstPlayerName(Bundle savedInstanceState) {
        Intent intent = getIntent();
        firstPlayerName = intent.getStringExtra(Intent.EXTRA_USER);
        return firstPlayerName;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        CreateToast.validateText(getApplicationContext(), firstPlayerName, secondPlayerName.getText().toString(), true);
    }

    private void initAdapter() {
        DBService dbService = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        ArrayList<Player> players = dbService.getAllPlayers();
        String[] usernames = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            usernames[i] = players.get(i).getUserName();
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, usernames);
        secondPlayerName.setAdapter(adapter);
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

    protected void onStart(){
        super.onStart();
    }

    protected void onRestart(){
        super.onRestart();
    }

    protected void onResume(){
        super.onResume();
    }

    protected void onPause(){
        super.onPause();
    }

    protected void onStop(){
        super.onStop();
    }

    protected void onDestroy(){
        super.onDestroy();
    }

}
