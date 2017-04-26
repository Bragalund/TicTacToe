package bakhen.no.tictactoe3.Score;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.DBService;
import bakhen.no.tictactoe3.SQLLite.Player;

public class ScoreScreen extends AppCompatActivity {

    private Button goBackBtn;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        initWidgets();
        initAdapters();
    }

    private void initAdapters() {
        DBService dbService = new DBService(getApplicationContext(), DBService.DB_NAME, null, DBService.DATABASE_VERSION);
        ArrayList<Player> players = dbService.getAllPlayers();

        mAdapter = new RecyclerAdapter(players);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initWidgets() {
        goBackBtn = (Button) findViewById(R.id.Score_Screen_go_Back_Button);
        mRecyclerView = (RecyclerView) findViewById(R.id.Score_Screen_recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        goBackBtn.setOnClickListener(goBackButtonListener);
    }

    View.OnClickListener goBackButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };


}
