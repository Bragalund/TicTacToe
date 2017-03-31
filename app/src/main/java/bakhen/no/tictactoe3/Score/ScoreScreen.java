package bakhen.no.tictactoe3.Score;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import bakhen.no.tictactoe3.R;

public class ScoreScreen extends AppCompatActivity {

    private Button goBackBtn;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        initWidgets();
    }

    private void initWidgets() {
        goBackBtn = (Button) findViewById(R.id.Score_Screen_go_Back_Button);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.Score_Screen_recyclerView);
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
