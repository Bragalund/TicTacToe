package bakhen.no.tictactoe3.Score;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import bakhen.no.tictactoe3.R;

public class ScoreScreen extends AppCompatActivity{

    private Button goBackBtn;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
    }

    private void initWidgets(){
        goBackBtn = (Button) findViewById(R.id.Score_Screen_go_Back_Button);
        linearLayout = (LinearLayout) findViewById(R.id.Score_Screen_Linearlayout);
    }
}
