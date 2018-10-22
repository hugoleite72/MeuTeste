package com.example.administrador.meuteste;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VoltarComParametro extends AppCompatActivity {
    Button btinsericl,btListcl,btvoltarcl;
    ListView listadecliente;
    ProgressDialog mprogressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btinsericl = (Button)findViewById(R.id.btinserircl);
        btListcl = (Button)findViewById(R.id.btListcl);
        btListcl.post(new Runnable(){
            @Override
            public void run() {
                btListcl.performClick();
            }
        });
        listadecliente = (ListView) findViewById(R.id.listadecliente);
        btvoltarcl = (Button)findViewById(R.id.btvoltarcl);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listadecliente.setOnItemClickListener(new ItemClickedListener());
        btinsericl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaNova.class);
                startActivity( intent );
            }
        });
        btListcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogressDialog = ProgressDialog.show(VoltarComParametro.this, "Aguarde", "Verificando Produto(s)...");
                new Thread(new Runnable() {
                    Handler handler = new Handler();
                    List<Pessoa> listadepessoas = new ArrayList<Pessoa>();
                    String erro="";
                    public void run() {
                        try {
                            handler.post(new Runnable() {
                                public void run() {
                                    LeitorDb mDbHelper = new LeitorDb(getBaseContext());
                                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                                    Cursor c = db.query("CLIENTES",new String[]{"COD_CLIENTE","NOME","CPFCGC"},null,null,null,null,"NOME");
                                    //listadepessoas = new ArrayList<Pessoa>();
                                    boolean proximo = true;

                                    if (c.moveToFirst())
                                    {
                                        while (proximo)
                                        {
                                            Pessoa pessoa = new Pessoa();
                                            pessoa.setCodigo(c.getInt(0));
                                            pessoa.setNome(c.getString(1));
                                            pessoa.setCpf(c.getString(2));
                                            listadepessoas.add(pessoa);
                                            proximo=c.moveToNext();
                                        }
                                    }
                                    if (listadepessoas.size() > 0)
                                    {
                                        ArrayAdapter<Pessoa> adapter = new ArrayAdapter<Pessoa>(
                                                VoltarComParametro.this,
                                                android.R.layout.simple_list_item_1, listadepessoas);
                                        listadecliente.setAdapter(adapter);
                                    }
//                                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                                    imm.hideSoftInputFromWindow(edtpesqprodutos.getWindowToken(), 0);
//                                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                                }
                            });
                        } catch (Exception e) {
                            //mprogressDialog.setMessage("Erro: "+e.toString());
                            mprogressDialog.dismiss();
                            erro = e.toString();
                            handler.post(new Runnable() {
                                public void run() {
                                    //Toast.makeText(produtos.this, "ERRO "+erro.toString(), Toast.LENGTH_SHORT).show();
                                    //mensagem("ERRO", erro);
                                    Log.i("Clientes","ERRO "+erro.toString());
//			        					mprogressDialog.setMessage(erro);
                                }
                            });
                            Log.i("Clientes", "ERRO "+e.toString());
                        }
                        /////
                        mprogressDialog.dismiss();
                    }
                }).start();

            }
        });
        btvoltarcl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private class ItemClickedListener implements android.widget.AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int
                position, long id) {
            Pessoa pessoa = (Pessoa) arg0.getItemAtPosition(position);
            Intent intent = new Intent(getApplicationContext(), TelaNova.class);
            intent.putExtra("pessoaclicada", pessoa);
            startActivity( intent );
        }
    }


}
