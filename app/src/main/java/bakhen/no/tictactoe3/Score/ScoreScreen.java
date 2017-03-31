package bakhen.no.tictactoe3.Score;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import bakhen.no.tictactoe3.R;

public class ScoreScreen extends AppCompatActivity {

    private Button goBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        initWidgets();
    }

    private void initWidgets() {
        goBackBtn = (Button) findViewById(R.id.Score_Screen_go_Back_Button);
    }

}
