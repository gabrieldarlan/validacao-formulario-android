package br.com.gdarlan.alurafood.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.gdarlan.alurafood.R;

public class FormularioCadastroActivity extends AppCompatActivity {

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
        final CPFFormatter cpfFormatter = new CPFFormatter();
        campoCpf.setOnFocusChangeListener((v, hasFocus) -> {
            final String cpf = campoCpf.getText().toString();
            if (!hasFocus) {
                if (!validaCampoObrigatorio(textInputCpf, cpf)) return;
                if (!validaCampoComOnzeDigitos(textInputCpf, cpf)) return;
                if (!validadorCalculoCpf(textInputCpf, cpf)) return;

                removeErro(textInputCpf);

                final String cpfFormatado = cpfFormatter.format(cpf);
                campoCpf.setText(cpfFormatado);

            } else {
                try {
                    final String cpfSemFormato = cpfFormatter.unformat(cpf);
                    campoCpf.setText(cpfSemFormato);
                } catch (IllegalArgumentException e) {
                    Log.e("erro formatação cpf", e.getMessage());
                }
            }
        });
    }

    private boolean validadorCalculoCpf(TextInputLayout textInputCpf, String cpf) {
        final CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            textInputCpf.setError("CPF inválido");
            return false;
        }
        return true;
    }

    private void removeErro(TextInputLayout textInput) {
        textInput.setError(null);
        textInput.setErrorEnabled(false);
    }

    private boolean validaCampoComOnzeDigitos(TextInputLayout textInputCpf, String cpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError("O CPF precisa ter 11 dígitos");
            return false;
        }
        return true;
    }

    private void configuraCampoNomeCompleto() {
        final TextInputLayout textInputNomeCompleto = findViewById(R.id.formulario_cadastro_campo_nome_completo);
        adicionaValidacaoPadrao(textInputNomeCompleto);
    }

    private void adicionaValidacaoPadrao(TextInputLayout textInputCampo) {
        final EditText campo = textInputCampo.getEditText();
        campo.setOnFocusChangeListener((v, hasFocus) -> {
            final String texto = campo.getText().toString();
            if (!hasFocus) {
                if (!validaCampoObrigatorio(textInputCampo, texto)) return;
                removeErro(textInputCampo);
            }
        });
    }

    private boolean validaCampoObrigatorio(TextInputLayout textInputCampo, String texto) {
        if (texto.isEmpty()) {
            textInputCampo.setError("Campo obrigatório");
            return false;
        }
        return true;
    }

}