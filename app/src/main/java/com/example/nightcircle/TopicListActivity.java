package com.example.nightcircle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TopicListActivity extends AppCompatActivity {

    private TextView tvGreeting, tvStats;
    private RecyclerView rvTopics;
    private TopicAdapter adapter;
    private List<Topic> topicList = new ArrayList<>();
    private Random random = new Random();
    private String nickname;
    private String moodFromLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        // 从 LoginActivity 拿数据
        nickname = getIntent().getStringExtra("name");
        moodFromLogin = getIntent().getStringExtra("mood");

        tvGreeting = findViewById(R.id.tvGreeting);
        tvStats = findViewById(R.id.tvStats);
        rvTopics = findViewById(R.id.rvTopics);
        Button btnRefresh = findViewById(R.id.btnRefresh);

        tvGreeting.setText(String.format(Locale.getDefault(),
                "Good evening, %s %s", nickname, moodFromLogin));

        rvTopics.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TopicAdapter(topicList, topic -> {
            // 点击进入 ChatRoom（下一步再实现真正聊天）
            Intent i = new Intent(this, ChatRoomActivity.class);
            i.putExtra("name", nickname);
            i.putExtra("topicTitle", topic.getTitle());
            startActivity(i);
        });
        rvTopics.setAdapter(adapter);

        btnRefresh.setOnClickListener(v -> generateTopics());

        // 初次进入也生成一次
        generateTopics();
    }

    private void generateTopics() {
        topicList.clear();

        int onlineUsers = 80 + random.nextInt(60);  // 80~139
        int topicCount = Math.max(5, Math.min(30, onlineUsers / 4));

        String[] titles = {
                "Gratitude sparks",
                "Quiet corner tonight",
                "Things unsaid",
                "Small joy today",
                "Midnight thoughts",
                "Rainy memories",
                "Soft regrets",
                "Secret wishes"
        };
        String[] moods = {
                "✨ Gratitude",
                "🌙 Calm",
                "💜 Nostalgic",
                "🕯 Comfort"
        };

        for (int i = 0; i < topicCount; i++) {
            String title = titles[random.nextInt(titles.length)];
            String moodTag = moods[random.nextInt(moods.length)];
            int capacity = 4;
            int current = 1 + random.nextInt(4); // 1~4
            topicList.add(new Topic(title, moodTag, current, capacity));
        }

        adapter.notifyDataSetChanged();
        tvStats.setText("Online: " + onlineUsers + "   Topics: " + topicCount);
    }
}


