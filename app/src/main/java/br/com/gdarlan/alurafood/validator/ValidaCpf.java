package br.com.gdarlan.alurafood.validator;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidaCpf {

    public static final String DEVE_TER_ONZE_DIGITOS = "O CPF precisa ter 11 dígitos";
    public static final String CPF_INVALIDO = "CPF inválido";
    private final TextInputLayout textInputCpf;
    private final EditText campoCpf;
    private final ValidacaoPadrao validadorPadrao;
    private final CPFFormatter formatador = new CPFFormatter();

    public ValidaCpf(TextInputLayout textInputCpf) {
        this.textInputCpf = textInputCpf;
        this.campoCpf = textInputCpf.getEditText();
        this.validadorPadrao = new ValidacaoPadrao(textInputCpf);

    }

    private boolean validadorCalculoCpf(String cpf) {
        final CPFValidator cpfValidator = new CPFValidator();
        try {
            cpfValidator.assertValid(cpf);
        } catch (InvalidStateException e) {
            textInputCpf.setError(CPF_INVALIDO);
            return false;
        }
        return true;
    }

    private boolean validaCampoComOnzeDigitos(String cpf) {
        if (cpf.length() != 11) {
            textInputCpf.setError(DEVE_TER_ONZE_DIGITOS);
            return false;
        }
        return true;
    }

    private String getCpf() {
        return campoCpf.getText().toString();
    }

    public boolean estaValido() {
        if (!validadorPadrao.estaValido()) return false;
        final String cpf = getCpf();
        if (!validaCampoComOnzeDigitos(cpf)) return false;
        if (!validadorCalculoCpf(cpf)) return false;
        adicionaFormatacao(cpf);
        return true;
    }

    private void adicionaFormatacao(String cpf) {
        final String cpfFormatado = formatador.format(cpf);
        campoCpf.setText(cpfFormatado);
    }
}
