package bakhen.no.tictactoe3.Score;


import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import bakhen.no.tictactoe3.R;
import bakhen.no.tictactoe3.SQLLite.Player;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemHolder> {
    private ArrayList<Player> players;

    public RecyclerAdapter(ArrayList<Player> users) {
        this.players = users;
    }

    @Override
    public RecyclerAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_row, parent, false);
        return new ItemHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ItemHolder holder, int position) {
        Player itemPlayer = players.get(position);
        holder.bindPlayer(itemPlayer);
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView username;
        private TextView winScore;
        private TextView loseScore;
        private Player mPlayer;

        private static final String ITEM_KEY = "PLAYER";

        public ItemHolder(View v) {
            super(v);

            username = (TextView) v.findViewById(R.id.Score_Screen_Username_Textview);
            loseScore = (TextView) v.findViewById(R.id.Score_Screen_User_Lose_Score_textView);
            winScore = (TextView) v.findViewById(R.id.Score_Screen_User_Win_Score_textView);
            //v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showPlayerIntent = new Intent(context, ItemHolder.class);
            showPlayerIntent.putExtra(ITEM_KEY, mPlayer);
            context.startActivity(showPlayerIntent);
        }

        public void bindPlayer(Player player) {
            mPlayer = player;
            username.setText(mPlayer.getUserName());
            winScore.setText("Won: "+mPlayer.getWins());
            loseScore.setText("Lost: "+mPlayer.getLosses());
        }
    }
}
