package br.com.gdarlan.alurafood.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.gdarlan.alurafood.R;
import br.com.gdarlan.alurafood.formatter.FormataTelefoneComDdd;
import br.com.gdarlan.alurafood.validator.ValidaCpf;
import br.com.gdarlan.alurafood.validator.ValidaEmail;
import br.com.gdarlan.alurafood.validator.ValidaTelefoneComDdd;
import br.com.gdarlan.alurafood.validator.ValidacaoPadrao;
import br.com.gdarlan.alurafood.validator.Validador;

public class FormularioCadastroActivity extends AppCompatActivity {

    public static final String ERRO_FORMATACAO_CPF = "erro formatação cpf";
    private final List<Validador> validadores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro);

        inicializaCampos();
    }

    private void inicializaCampos() {
        configuraCampoNomeCompleto();
        configuraCampoCpf();
        configuraCampoTelefoneComDdd();
        configuraCampoEmail();
        configuraCampoSenha();
    }

    private void configuraCampoSenha() {
        final TextInputLayout textInputSenha = findViewById(R.id.formulario_cadastro_campo_senha);
        adicionaValidacaoPadrao(textInputSenha);
    }

    private void configuraCampoEmail() {
        final TextInputLayout textInputEmail = findViewById(R.id.formulario_cadastro_campo_email);
        final EditText campoEmail = textInputEmail.getEditText();
        final ValidaEmail validador = new ValidaEmail(textInputEmail);
        validadores.add(validador);
        campoEmail.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validador.estaValido();
            }
        });
    }

    private void configuraCampoTelefoneComDdd() {
        final TextInputLayout textInputTelefoneComDdd = findViewById(R.id.formulario_cadastro_campo_telefone_com_ddd);
        final EditText campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        final ValidaTelefoneComDdd validador = new ValidaTelefoneComDdd(textInputTelefoneComDdd);
        validadores.add(validador);
        final FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();
        campoTelefoneComDdd.setOnFocusChangeListener((v, hasFocus) -> {
            final String telefoneComDdd = campoTelefoneComDdd.getText().toString();
            if (hasFocus) {
                final String telefoneComDddSemFormtacao = formatador.remove(telefoneComDdd);
                campoTelefoneComDdd.setText(telefoneComDddSemFormtacao);
            } else {
                validador.estaValido();
            }
        });
    }


    private void configuraCampoCpf() {
        final TextInputLayout textInputCpf = findViewById(R.id.formulario_cadastro_campo_cpf);
        final EditText campoCpf = textInputCpf.getEditText();
        final CPFFormatter formatador = new CPFFormatter();
        final ValidaCpf validador = new ValidaCpf(textInputCpf);
        validadores.add(validador);
        assert campoCpf != null;
        campoCpf.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                removeFormatacao(campoCpf, formatador);
            } else {
                validador.estaValido();
            }
        });
    }

    private void removeFormatacao(EditText campoCpf, CPFFormatter formatador) {
        final String cpf = campoCpf.getText().toString();
        try {
            final String cpfSemFormato = formatador.unformat(cpf);
            campoCpf.setText(cpfSemFormato);
        } catch (IllegalArgumentException e) {
            Log.e(ERRO_FORMATACAO_CPF, e.getMessage());
        }
    }


    private void configuraCampoNomeCompleto() {
        final TextInputLayout textInputNomeCompleto = findViewById(R.id.formulario_cadastro_campo_nome_completo);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao(TextInputLayout textInputCampo) {
        final EditText campo = textInputCampo.getEditText();
        final ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
        validadores.add(validador);
        campo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validador.estaValido();
            }
        });
    }

    // Coloquei o onclick no xml para criar essa funcao
    public void cadastrar(View view) {
        boolean formularioEstaValido = validaCampos();
        if (formularioEstaValido) {
            cadastroRealizado();
        }
    }

    private void cadastroRealizado() {
        Toast.makeText(this, "Cadastro efetuado com sucesso", Toast.LENGTH_LONG).show();
    }

    private boolean validaCampos() {
        boolean formularioEstaValido = true;
        for (Validador validador :
                validadores) {
            if (!validador.estaValido()) {
                formularioEstaValido = false;
            }
        }
        return formularioEstaValido;
    }
}