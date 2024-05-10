package com.vitoroliveira.temporizador;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    EditText editTempo;
    Button btnIniciar;
    Button btnParar;
    TextView textoInfo;

    private CountDownTimer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTempo = findViewById(R.id.editTempo);
        btnIniciar = findViewById(R.id.buttonIniciar);
        btnParar = findViewById(R.id.buttonParar);
        textoInfo = findViewById(R.id.textInfo);

        btnIniciar.setOnClickListener(v -> {
            try {
                long time = Long.parseLong(editTempo.getText().toString());

                timer = new CountDownTimer(time * 60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long seconds = millisUntilFinished / 1000;
                        long minutes = seconds / 60;
                        seconds = seconds % 60;
                        textoInfo.setText(String.format("%02d:%02d", minutes, seconds));
                    }

                    @Override
                    public void onFinish() {
                        textoInfo.setText("Tempo esgotado!");
                    }
                };

                timer.start();
            } catch (Exception e) {
                Toast.makeText(this, "Digite um número no campo texto", Toast.LENGTH_LONG).show();
            }

            btnParar.setOnClickListener(l -> {
                textoInfo.setTextColor(ContextCompat.getColor(this, R.color.timerStop));
                timer.cancel();
            });

        });
    }


}