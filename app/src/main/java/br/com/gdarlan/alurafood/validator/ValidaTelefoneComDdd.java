package br.com.gdarlan.alurafood.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.com.gdarlan.alurafood.formatter.FormataTelefoneComDdd;

public class ValidaTelefoneComDdd {

    public static final String DEVE_TER_ENTRE_10_A_11_DIGITOS = "Telefone deve ter entre 10 a 11 digitos";
    private final TextInputLayout textInputTelefoneComDdd;
    private final EditText campoTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataTelefoneComDdd formatador=new FormataTelefoneComDdd();

    public ValidaTelefoneComDdd(TextInputLayout textInputTelefoneComDdd) {
        this.textInputTelefoneComDdd = textInputTelefoneComDdd;
        this.campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputTelefoneComDdd);
    }

    private boolean validaEntreDezOuOnzeDigitos(String telefoneComDdd) {
        final int digitos = telefoneComDdd.length();
        if (digitos < 10 || digitos > 11) {
            textInputTelefoneComDdd.setError(DEVE_TER_ENTRE_10_A_11_DIGITOS);
            return false;
        }
        return true;
    }

    public boolean estaValido() {
        if (!validacaoPadrao.estaValido()) return false;
        final String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        if (!validaEntreDezOuOnzeDigitos(telefoneComDdd)) return false;
        adicionaFormatacao(telefoneComDdd);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormtado = formatador.formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormtado);
    }



}
