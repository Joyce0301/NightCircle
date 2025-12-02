package com.example.nightcircle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);

        TextView tvTitle = findViewById(R.id.tvReflectionTitle);
        EditText etReflection = findViewById(R.id.etReflection);
        Button btnDone = findViewById(R.id.btnDone);

        // 从 ChatRoom 里带过来的名字和话题标题（如果有）
        String name = getIntent().getStringExtra("name");
        String topic = getIntent().getStringExtra("topicTitle");

        if (name != null && !name.isEmpty()) {
            tvTitle.setText("Before you leave, " + name);
        }

        btnDone.setOnClickListener(v -> {
            String text = etReflection.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Write at least one line 💭", Toast.LENGTH_SHORT).show();
                return;
            }

            // 很简单地用 SharedPreferences 存一条记录，够交作业了
            SharedPreferences sp = getSharedPreferences("nightcircle_reflections", MODE_PRIVATE);
            long ts = System.currentTimeMillis();
            String value = "Topic: " + (topic == null ? "" : topic) + " | " + text;

            sp.edit()
                    .putString("reflection_" + ts, value)
                    .apply();

            Toast.makeText(this, "Saved. Good night 🌙", Toast.LENGTH_SHORT).show();

            // 这里简单点：直接关掉当前页面
            finish();
        });
    }
}

