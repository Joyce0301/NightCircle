package com.example.nightcircle;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ChatRoomActivity extends AppCompatActivity {

    private TextView tvTopic, tvTimer;
    private EditText etMsg;
    private RecyclerView rvChat;

    private final List<ChatMessage> chatList = new ArrayList<>();
    private ChatAdapter adapter;
    private CountDownTimer timer;

    // ------------ 新增：假用户相关 ------------
    private Handler fakeHandler = new Handler(Looper.getMainLooper());
    private Runnable fakeRunnable;
    private boolean sessionActive = true;
    private Random random = new Random();

    private String[] fakeNames = {
            "MoonWalker", "NightOwl", "Stranger-47", "QuietSoul",
            "Skyline", "LofiCat", "StarDust", "SoftEcho"
    };

    private String[] fakeTexts = {
            "Just needed a quiet place tonight.",
            "Today was heavier than I expected.",
            "Grateful for one small good thing.",
            "Trying to slow down my thoughts.",
            "It’s hard, but I’m still here.",
            "I miss someone I shouldn’t miss.",
            "Tonight feels strangely calm.",
            "I’m learning to be kind to myself."
    };
    // ------------ 新增结束 ------------

    // 先用 10 秒测试，确定流程后再改回 60 分钟
    // private static final long SESSION_TIME_MS = 10 * 1000L; // 测试
    private static final long SESSION_TIME_MS = 60 * 60 * 1000L; // 正式 60 分钟

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        String name = getIntent().getStringExtra("name");
        String topicTitle = getIntent().getStringExtra("topicTitle");

        tvTopic = findViewById(R.id.tvTopic);
        tvTimer = findViewById(R.id.tvTimer);
        etMsg = findViewById(R.id.etMsg);
        rvChat = findViewById(R.id.rvChat);
        Button btnSend = findViewById(R.id.btnSend);

        tvTopic.setText(topicTitle);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rvChat.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(chatList);
        rvChat.setAdapter(adapter);

        // 自己发消息
        btnSend.setOnClickListener(v -> {
            String text = etMsg.getText().toString().trim();
            if (text.isEmpty()) return;

            chatList.add(new ChatMessage(name, text));
            adapter.notifyItemInserted(chatList.size() - 1);
            rvChat.scrollToPosition(chatList.size() - 1);
            etMsg.setText("");
        });

        startTimer();
        startFakeMessages();   // ⭐ 启动假用户发言
    }

    private void startTimer() {
        timer = new CountDownTimer(SESSION_TIME_MS, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long m = millisUntilFinished / 1000 / 60;
                long s = (millisUntilFinished / 1000) % 60;
                tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", m, s));
            }

            @Override
            public void onFinish() {
                // 会话结束，不再让假用户发消息
                sessionActive = false;
                fakeHandler.removeCallbacksAndMessages(null);

                chatList.clear();
                adapter.notifyDataSetChanged();

                Intent i = new Intent(ChatRoomActivity.this, ReflectionActivity.class);
                i.putExtra("name", getIntent().getStringExtra("name"));
                i.putExtra("topicTitle", getIntent().getStringExtra("topicTitle"));
                startActivity(i);
                finish();
            }
        }.start();
    }

    // ============== 下面是新增的“假用户聊天”逻辑 ==============

    private void startFakeMessages() {
        // 第一次延迟 3 秒出现一个陌生人消息，以后每 4~10 秒随机一次
        fakeRunnable = new Runnable() {
            @Override
            public void run() {
                if (!sessionActive) return;

                addOneFakeMessage();

                // 下一条消息的时间间隔：4~10 秒随机
                long delay = 4000L + random.nextInt(7000);
                fakeHandler.postDelayed(this, delay);
            }
        };

        fakeHandler.postDelayed(fakeRunnable, 3000L);
    }

    private void addOneFakeMessage() {
        String sender = fakeNames[random.nextInt(fakeNames.length)];
        String text = fakeTexts[random.nextInt(fakeTexts.length)];

        chatList.add(new ChatMessage(sender, text));
        adapter.notifyItemInserted(chatList.size() - 1);
        rvChat.scrollToPosition(chatList.size() - 1);
    }

    // ============== 新增逻辑结束 ==============

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sessionActive = false;
        if (timer != null) {
            timer.cancel();
        }
        fakeHandler.removeCallbacksAndMessages(null);
    }
}



