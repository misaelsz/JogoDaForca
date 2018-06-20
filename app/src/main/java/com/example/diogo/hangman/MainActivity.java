package com.example.diogo.hangman;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //region Variáveis
    private String palavraSorteada;
    private String tracos = "";
    private String[] vetorLetrasCertas;
    private String letrasErradas = "";
    private ArrayList<String> listaLetrasErradas = new ArrayList<String>();
    //endregion
    //region Views
    private TextView txtPalavraSorteada;
    private TextView txtLetrasErradas;
    private Button btnVerificar;
    private EditText edtPalpite;
    private ImageView imgErros;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //region Vincular Views
        txtPalavraSorteada = (TextView) findViewById(R.id.txtPalavraSorteada);
        txtLetrasErradas = (TextView) findViewById(R.id.txtLetrasErradas);
        btnVerificar = (Button) findViewById(R.id.btnVerificar);
        edtPalpite = (EditText) findViewById(R.id.edtPalpite);
        imgErros = (ImageView) findViewById(R.id.imgErros);
        //endregion

        palavraSorteada = ListaDePalavras.retornarPalavra();
        vetorLetrasCertas = new String[palavraSorteada.length()];

        desenharTracos();

        //Printar informação no log
        Log.v("Tracos", tracos);
    }

    private void desenharTracos() {
        tracos = "";
        for (int i = 0; i < vetorLetrasCertas.length; i++) {
            if (vetorLetrasCertas[i] == null) {
                tracos += "- ";
                Log.v("Pos " + i, "-");
            } else {
                tracos += vetorLetrasCertas[i] + " ";
                Log.v("Pos " + i, vetorLetrasCertas[i]);
            }
        }
        tracos = tracos.trim();
        txtPalavraSorteada.setText(tracos);
    }

    public void desenharLetrasErradas() {
        letrasErradas = "";
        for (String letraErrada : listaLetrasErradas) {
            letrasErradas += letraErrada + " - ";
        }
        letrasErradas =
                letrasErradas.substring(0, letrasErradas.length() - 2);
        txtLetrasErradas.setText(letrasErradas);
    }

    public void btnVerificarClick(View view) {
        String letraDoPalpite = edtPalpite.getText().toString().toUpperCase();
        String letraDaPalavra;
        if (palavraSorteada.toUpperCase().contains(letraDoPalpite)) {
            for (int i = 0; i < palavraSorteada.length(); i++) {
                letraDaPalavra = String.valueOf(palavraSorteada.charAt(i)).toUpperCase();
                if (letraDaPalavra.equals(letraDoPalpite)) {
                    vetorLetrasCertas[i] = letraDoPalpite;
                }
            }
            desenharTracos();
        } else {
            if (!verificarLetraErrada(letraDoPalpite)) {
                listaLetrasErradas.add(letraDoPalpite);
                desenharLetrasErradas();
                trocarImagem();
            }
        }
        edtPalpite.setText("");
        verificarFimJogo();
    }

    public boolean verificarLetraErrada(String letraErrada) {
        for (String letra : listaLetrasErradas) {
            if (letra.equals(letraErrada)) {
                return true;
            }
        }
        return false;
    }

    public void trocarImagem() {
        switch (listaLetrasErradas.size()) {
            case 1:
                imgErros.setImageResource(R.drawable.image2);
                break;
            case 2:
                imgErros.setImageResource(R.drawable.image3);
                break;
            case 3:
                imgErros.setImageResource(R.drawable.image4);
                break;
            case 4:
                imgErros.setImageResource(R.drawable.image5);
                break;
            case 5:
                imgErros.setImageResource(R.drawable.image6);
                break;
            case 6:
                imgErros.setImageResource(R.drawable.image7);
                break;
            case 7:
                imgErros.setImageResource(R.drawable.image8);
                break;
            case 8:
                imgErros.setImageResource(R.drawable.image9);

                break;
        }
    }

    public boolean verificarPosicaoNula() {
        for (String letraCerta : vetorLetrasCertas) {
            if (letraCerta == null) {
                return true;
            }
        }
        return false;
    }

    public void verificarFimJogo() {
        if (!verificarPosicaoNula()) {
            reiniciarJogo("Você ganhou!");
        }
        if (listaLetrasErradas.size() == 8) {
            reiniciarJogo("Você perdeu!");
        }
    }

    public void reiniciarJogo(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hangman");
        builder.setMessage(mensagem);

        builder.setPositiveButton("Reiniciar jogo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                palavraSorteada = ListaDePalavras.retornarPalavra();
                vetorLetrasCertas = new String[palavraSorteada.length()];
                tracos = "";
                listaLetrasErradas.clear();
                letrasErradas = "";

                imgErros.setImageResource(R.drawable.image1);
                txtLetrasErradas.setText("");
                desenharTracos();
            }
        });
        builder.setCancelable(false);
//        builder.setNegativeButton("Negativo", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        builder.setNeutralButton("Talvez", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}











