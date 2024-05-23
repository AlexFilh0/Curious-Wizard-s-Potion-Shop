package com.example.wizardspotionshop;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EasterEgg extends BaseMainActivity {

    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easteregg);

        // Define as variáveis dos botões
        Button btnSalvar = findViewById(R.id.btnSalvar);

        // Define as variáveis dos campos
        EditText edtMensagem = findViewById(R.id.edtMensagem);
        TextView txtMensagem = findViewById(R.id.txtMensagem);

        // Define as variáveis do banco ("tabelas")
        DatabaseReference mensagens = referencia.child("EasterEgg");

        mensagens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtMensagem.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txtMensagem.setText(error.toString());
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensagens.setValue(edtMensagem.getText().toString());
                edtMensagem.setText("");
            }
        });
    }

    @Override
    protected void onViewCreated() {

    }
}