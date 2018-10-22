package com.example.administrador.meuteste;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TelaNova extends AppCompatActivity {
    private EditText edNome;
    private EditText edCPF;
    private Pessoa pessoaclicada;
    Button btEditar;
    Button btlist;
    boolean ehedicao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        edNome = (EditText) findViewById(R.id.editText);
        edCPF = (EditText) findViewById(R.id.editText2);
        btlist = (Button)findViewById(R.id.btListcl);
        pessoaclicada = (Pessoa) getIntent().getSerializableExtra( "pessoaclicada" );

        btEditar = (Button) findViewById(R.id.button);
        if ( pessoaclicada != null ) {
            edNome.setText( pessoaclicada.getNome() );
            edCPF.setText( pessoaclicada.getCpf() );
            ehedicao = true;
        }
        else {
            ehedicao = false;
        }

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (  ehedicao  ) {
                    LeitorDb mDbHelper = new LeitorDb(getBaseContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("NOME", edNome.getText().toString());
                    values.put("CPFCGC", edCPF.getText().toString());
                    String ids = Integer.toString(pessoaclicada.getCodigo());
                    String[] args = { ids };
                    db.update("CLIENTES", values,"COD_CLIENTE=?", args  );
                    finish();

                }
                else{
                    LeitorDb mDbHelper = new LeitorDb(getBaseContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("NOME", edNome.getText().toString());
                    values.put("CPFCGC", edCPF.getText().toString());
                    db.insert( "CLIENTES", null, values );
                    finish();

                }
            }
        });

    }




}
