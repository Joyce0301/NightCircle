

package com.example.nightcircle;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TopicListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_topic_list);

        String name = getIntent().getStringExtra("name");
        String mood = getIntent().getStringExtra("mood");

        TextView tv = findViewById(R.id.tvShow);
        tv.setText(name + " " + mood);
    }
}

