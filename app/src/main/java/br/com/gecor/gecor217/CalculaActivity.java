package br.com.gecor.gecor217;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class CalculaActivity extends AppCompatActivity {
    private ListView listaRating;
    private String[] rating = {"AA", "A1", "A2",
            "B1", "B2", "B3", "B4",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7",
            "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9",
            "E1", "E2",
            "F1", "F2",
            "G1", "G2", "H"};
    private float[] ppcld = {0.0f, 0.5f, .75f,
            1.0f, 1.5f, 2.0f, 2.5f,
            3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f,
            10.0f, 11.0f, 12.0f, 13.0f, 14.0f, 15.0f, 16.0f, 18.0f, 23.0f,
            30.0f, 40.0f,
            50.0f, 60.0f,
            70.0f, 85.0f, 100.0f};
    private Button botao;
    private Button limpar;
    private Button voltar;
    private AlertDialog.Builder dialog;
    private float sddevedor;
    private float entrada;
    private float pec;
    private float taxa;
    private int carencia;
    private int prazo;
    private float iof;
    private float impacto;
    private float pcld;
    private float saldototal;
    private int codigoposicao = 0;
    private boolean flag;
    //   private float va;
    private float expo;
    private float numero;
    private float numero2;
    private float j3;
    private float j11;
    private float parcelamento;
    private String string;
    Locale ptBr = new Locale("pt", "BR");
    private EditText esddevedor;
    private EditText eentrada;
    private EditText epec;
    private EditText etaxa;
    private EditText ecarencia;
    private EditText eprazo;
    private TextView respostaIof;
    private TextView respostaParcela;
    private TextView respostaImpacto;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula);

        esddevedor = (EditText) findViewById(R.id.editSdDevedorId);
        // esddevedor.addTextChangedListener(new MascaraMonetaria(esddevedor)); // chama a classe para máscara
        epec = (EditText) findViewById(R.id.editPecId);
        // epec.addTextChangedListener(new MascaraVirgula(epec)); // chama a classe para máscara
        eentrada = (EditText) findViewById(R.id.editEntradaId);
        //eentrada.addTextChangedListener(new MascaraMonetaria(eentrada)); // chama a classe para máscara
        etaxa = (EditText) findViewById(R.id.editTaxaId);
        //  etaxa.addTextChangedListener(new MascaraVirgula(etaxa)); // chama a classe para máscara
        ecarencia = (EditText) findViewById(R.id.editCarenciaId);
        eprazo = (EditText) findViewById(R.id.editPrazoId);
        respostaIof = (TextView) findViewById(R.id.iofId);
        respostaParcela = (TextView) findViewById(R.id.parcelaId);
        respostaImpacto = (TextView) findViewById(R.id.impactoId);
        listaRating = (ListView) findViewById(R.id.listaRatingId);

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_2,
                android.R.id.text2,
                rating) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text2);
                text.setTextColor(Color.BLACK);
                text.setTextSize(20);
                return view;
            }
        };
        listaRating.setAdapter(adaptador);

        listaRating.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                codigoposicao = position;
            }
        });

        limpar = (Button) findViewById(R.id.btnLimparId);
        limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esddevedor.setText(String.valueOf(""));
                epec.setText(String.valueOf(""));
                eentrada.setText(String.valueOf(""));
                etaxa.setText(String.valueOf(""));
                ecarencia.setText(String.valueOf(""));
                eprazo.setText(String.valueOf(""));
                respostaIof.setText(String.valueOf(""));
                respostaParcela.setText(String.valueOf(""));
                respostaImpacto.setText(String.valueOf(""));
            }
        });

        voltar = (Button) findViewById(R.id.btnVoltarId);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        botao = (Button) findViewById(R.id.btnCalcularId);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flag = true;
                if (esddevedor.getText().length() == 0) {
                    flag = false;
                }
                if (epec.getText().length() == 0) {
                    flag = false;
                }
                if (eentrada.getText().length() == 0) {
                    flag = false;
                }
                if (etaxa.getText().length() == 0) {
                    flag = false;
                }
                if (ecarencia.getText().length() == 0) {
                    flag = false;
                }
                if (eprazo.getText().length() == 0) {
                    flag = false;
                }

                if (flag == false) {
                    aviso("Todos os campos são de preenchimento obrigatório!");
                    // flag = true;
                } else {
                    string = esddevedor.getText().toString();
                    string = string.replaceAll("[R$]", "").replaceAll("[.]", "").replaceAll("[,]", ".");
                    sddevedor = Float.parseFloat(string);
                    pec = Float.parseFloat(epec.getText().toString());
                    string = eentrada.getText().toString();
                    string = string.replaceAll("[R$]", "").replaceAll("[.]", "").replaceAll("[,]", ".");
                    entrada = Float.parseFloat(string);
                    taxa = Float.parseFloat(etaxa.getText().toString());
                    carencia = Integer.parseInt(ecarencia.getText().toString());
                    prazo = Integer.parseInt(eprazo.getText().toString());
                    calcular(sddevedor, pec, entrada, taxa, carencia, prazo, codigoposicao);
                    // aviso("Pronto para calcular!");
                }
            }
        });
    }

    private void calcular(float sddevedor, float pec, float entrada, float taxa, int carencia, int prazo, int codigoposicao) {

        //   float impacto;
        float rating;
        flag = true;
        rating = ppcld[codigoposicao];
        impacto = ((rating / 100) - (pec / 100)) * sddevedor;

        if (entrada > sddevedor) {
            flag = false;
            aviso("Valor da entrada é maior que o saldo devedor. Revise os parâmetros informados.");
        }
        if ((prazo == 0) || (prazo > 120)) {
            flag = false;
            aviso("Prazo não conforme. Revise os parâmetros informados.");
        }
        if ((pec < 0) || (pec > 100)) {
            flag = false;
            aviso("Valor da PEC é inválido. Revise os parâmetros informados.");
        }

        if (flag) {
            j3 = (sddevedor - entrada) - (sddevedor * (((pec / 100)))); // coluna j3 da planilha
            iof = (float) (((j3 * 0.0041 / 100) * (365)));  // calculo do iof
            j11 = (taxa / 100);
            expo = (float) Math.pow((1 + j11), prazo);
            parcelamento = (expo - 1) / (expo * j11);    // calcula o valor do parcelamento j4
            expo = (float) Math.pow((j11 + 1), carencia);
            saldototal = ((j3 + iof) * (expo));               // calcula o saldo total da operação j5
            float parcelas = saldototal / parcelamento;
            //     aviso(String.valueOf("Valor do IOF: "+NumberFormat.getCurrencyInstance().format(iof))+" - Valor das Parcelas: "+
            //    NumberFormat.getCurrencyInstance().format(parcelas));
            if ((iof < 0) || (parcelas < 0)) {
                aviso("Parametros incorretos. Reavalie os valores apresentados.");
            } else {
                if (impacto < 0) {
                    aviso(String.valueOf("Não é recomendável a contratação nestes parâmetros, impacto na PCLD em: " +
                            NumberFormat.getCurrencyInstance().format(impacto)) + ". Reavalie os valores apresentados.");
                }
                respostaIof.setText(String.valueOf("Valor do IOF: " + NumberFormat.getCurrencyInstance().format(iof)));
                respostaParcela.setText(String.valueOf("Valor da Parcela: " + NumberFormat.getCurrencyInstance().format(parcelas)));
                respostaImpacto.setText(String.valueOf("Impacto: " + NumberFormat.getCurrencyInstance().format(impacto)));
            }
        }
    }

    private void aviso(String mensagem) {
        // criar alert dialog
        dialog = new AlertDialog.Builder(CalculaActivity.this);
        //configurar o titulo
        dialog.setTitle("ATENÇAO!");
        //configurar a mensagem
        dialog.setMessage(mensagem);
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_alert); // seleciona icones nativos do android
        // botao negativo
        dialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   Toast.makeText(CalculaActivity.this, "Pressionado botão Não", Toast.LENGTH_SHORT).show();
                    }
                });
        dialog.create();
        dialog.show();
    }
}