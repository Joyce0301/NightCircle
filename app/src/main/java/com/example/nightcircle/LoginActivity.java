package com.example.nightcircle;


import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        setContentView(R.layout.activity_login);

        EditText et = findViewById(R.id.etName);
        RadioGroup rg = findViewById(R.id.rgMood);
        Button btn = findViewById(R.id.btnGo);

        btn.setOnClickListener(v -> {
            String name = et.getText().toString().trim();
            int id = rg.getCheckedRadioButtonId();
            if(name.isEmpty() || id==-1){
                Toast.makeText(this,"Fill all",Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rb = findViewById(id);
            Intent i = new Intent(this, TopicListActivity.class);
            i.putExtra("name",name);
            i.putExtra("mood",rb.getText().toString());
            startActivity(i);
        });
    }
}

