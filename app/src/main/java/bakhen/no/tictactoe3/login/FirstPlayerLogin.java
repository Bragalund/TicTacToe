package bakhen.no.tictactoe3.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import java.util.ArrayList;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.DBService;
import bakhen.no.tictactoe3.SQLLite.Player;
import bakhen.no.tictactoe3.Utils.CreateToast;

import static android.content.Intent.EXTRA_USER;

public class FirstPlayerLogin extends AppCompatActivity implements TextWatcher {
    private AutoCompleteTextView firstPlayerName;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_player_login);
        initWidgets();
        initAdapter();
    }

    private void initWidgets() {
        firstPlayerName = (AutoCompleteTextView) findViewById(R.id.login_first_player_edittext);
        firstPlayerName.addTextChangedListener(this);
    }

    public void nextPlayer(View v) {
        if (CreateToast.validateText(getApplicationContext(), firstPlayerName.getText().toString(), "")) {
            Intent intent = new Intent(this, SecondPlayerLogin.class);
            intent.putExtra(EXTRA_USER, firstPlayerName.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        CreateToast.validateText(getApplicationContext(), firstPlayerName.getText().toString(), "");
    }

    private void initAdapter() {
        DBService dbService = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        ArrayList<Player> players = dbService.getAllPlayers();
        String[] usernames = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            usernames[i] = players.get(i).getUserName();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, usernames);
        firstPlayerName.setAdapter(adapter);
    }


}
