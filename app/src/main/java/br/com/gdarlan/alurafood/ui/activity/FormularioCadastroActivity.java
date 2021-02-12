package br.com.gdarlan.alurafood.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.gdarlan.alurafood.R;
import br.com.gdarlan.alurafood.validator.ValidaCpf;
import br.com.gdarlan.alurafood.validator.ValidacaoPadrao;

public class FormularioCadastroActivity extends AppCompatActivity {

    public static final String ERRO_FORMATACAO_CPF = "erro formatação cpf";

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
        adicionaValidacaoPadrao(textInputEmail);
    }

    private void configuraCampoTelefoneComDdd() {
        final TextInputLayout textInputTelefoneComDdd = findViewById(R.id.formulario_cadastro_campo_telefone_com_ddd);
        adicionaValidacaoPadrao(textInputTelefoneComDdd);
    }

    private void configuraCampoCpf() {
        final TextInputLayout textInputCpf = findViewById(R.id.formulario_cadastro_campo_cpf);
        final EditText campoCpf = textInputCpf.getEditText();
        final CPFFormatter formatador = new CPFFormatter();
        final ValidaCpf validador = new ValidaCpf(textInputCpf);
        assert campoCpf != null;
        campoCpf.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                adicionaFormatacao(campoCpf, formatador);
            } else {
                validador.estaValido();
            }
        });
    }

    private void adicionaFormatacao(EditText campoCpf, CPFFormatter formatador) {
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
        campo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                validador.estaValido();
            }
        });
    }


}