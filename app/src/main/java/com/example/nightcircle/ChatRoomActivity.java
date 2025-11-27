package com.example.nightcircle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatRoomActivity extends AppCompatActivity {

    private TextView tvTopic, tvTimer;
    private EditText etMsg;
    private RecyclerView rvChat;

    private final List<ChatMessage> chatList = new ArrayList<>();
    private ChatAdapter adapter;
    private CountDownTimer timer;

    // 先用 5 分钟做测试
    private static final long TEST_TIME_MS = 5 * 60 * 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        // 从上一个页面拿到昵称和话题
        String name = getIntent().getStringExtra("name");
        String topicTitle = getIntent().getStringExtra("topicTitle");

        tvTopic = findViewById(R.id.tvTopic);
        tvTimer = findViewById(R.id.tvTimer);
        etMsg = findViewById(R.id.etMsg);
        rvChat = findViewById(R.id.rvChat);
        Button btnSend = findViewById(R.id.btnSend);

        tvTopic.setText(topicTitle);

        rvChat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(chatList);
        rvChat.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String text = etMsg.getText().toString().trim();
            if (text.isEmpty()) return;

            chatList.add(new ChatMessage(name, text));
            adapter.notifyItemInserted(chatList.size() - 1);
            rvChat.scrollToPosition(chatList.size() - 1);
            etMsg.setText("");
        });

        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(TEST_TIME_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long m = millisUntilFinished / 1000 / 60;
                long s = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
            }

            @Override
            public void onFinish() {
                // 清空消息并跳转到 ReflectionActivity（可以后面再完善）
                chatList.clear();
                adapter.notifyDataSetChanged();

                Intent i = new Intent(ChatRoomActivity.this, ReflectionActivity.class);
                startActivity(i);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}



