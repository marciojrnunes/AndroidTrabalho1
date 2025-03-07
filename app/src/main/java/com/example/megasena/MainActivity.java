package com.example.megasena;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Double[] precosJogos = {5.00, 35.00, 140.00, 420.00, 1050.00, 2310.00, 4620.00, 8580.00, 15015.00,
                            25025.00, 40040.00, 61880.00, 92820.00, 135660.00, 193800.00};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Referencias do layout
        Button btnGerarJogos = (Button) findViewById(R.id.btnGerarJogos);
        Button btnLimpar = (Button) findViewById(R.id.BtnLimpar);
        EditText edtQntdJogos = (EditText) findViewById(R.id.qntdJogos);
        EditText edtQntdNums = (EditText) findViewById(R.id.qntdNumeros);
        TextView txtNumeros = (TextView) findViewById(R.id.numerosSorteados);

        //Botao Gerar Jogos
        btnGerarJogos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateQntdJogos(edtQntdJogos)){                 //Valida qntd jogos
                    if(validateQntdNumeros(edtQntdNums)){                //Valida qntd numeros
                        StringBuilder numerosSortedos = new StringBuilder();
                        //Converte os valores digitados para inteiro
                        Integer qntdJogos = Integer.valueOf(edtQntdJogos.getText().toString());
                        Integer qntdNums = Integer.valueOf(edtQntdNums.getText().toString());
                        //Gera os jogos
                        for (int i = 0; i < qntdJogos; i++){
                            int[] jogo = geraJogo(qntdNums);
                            numerosSortedos.append(formatSorteio(jogo));
                        }
                        //Limpa o texto anterior e mostra os numeros sorteados
                        txtNumeros.setText("");
                        txtNumeros.setText(numerosSortedos.toString());
                        valorAPagar(qntdNums, qntdJogos);
                        btnLimpar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        //Botao Limpar
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Limpa o texto anterior e mostra os numeros sorteados
                txtNumeros.setText("");
                edtQntdNums.setText(null);
                edtQntdJogos.setText(null);
                btnLimpar.setVisibility(View.GONE);
            }
        });
    }

    Boolean validateQntdJogos(EditText editText){
        //verifica se nao é texto vazio
        if(editText.getText().toString().trim().isEmpty()){
            Toast.makeText(getBaseContext(), "Insira quantidade de jogos", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    Boolean validateQntdNumeros(EditText editText){
        //verifica se nao é texto vazio
        if(editText.getText().toString().trim().isEmpty()){
            Toast.makeText(getBaseContext(), "Insira quantidade de números", Toast.LENGTH_LONG).show();
            return false;
        }
        //Verifica se valor esta entre 6 e 20
        Integer value = 0;
        value = Integer.valueOf(editText.getText().toString());
        if (value < 6 || value > 20){
            Toast.makeText(getBaseContext(), "Quantidade de números deve ser de 6 a 20", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    void valorAPagar(Integer qntdNumeros, Integer qntdJogos){
        //Calcula preco a pagar pelos jogos = preco por jogo armazenado no vetor * quantidade de jogos
        Double preco = precosJogos[qntdNumeros-6] * qntdJogos;
        //Formata para 2 casas decimais
        String precoFormatado = String.format("%.2f", preco);
        //Mostra o Toast
        Toast.makeText(getBaseContext(), "Valor a pagar: R$ " + precoFormatado, Toast.LENGTH_LONG).show();
    }

    private int[] geraJogo(Integer qntdNumeros){
        //Gera sequência números inteiros aleatórios entre 1 e 60
        return new Random().ints(1, 60 + 1)
                .distinct()
                .limit(qntdNumeros)
                .toArray();
    }

    private String formatSorteio (int[] nums){
        StringBuilder str = new StringBuilder();
        //Acrescenta um traco apos cada numero
        for (int num:nums) {
            str.append(num).append("-");
        }
        //Tira ultimo traço da sttring
        str.delete(str.length()-1, str.length());
        //Acrescenta quebra de linha
        str.append("\n\n");
        return str.toString();
    }



}