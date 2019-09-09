package com.example.fernandoapp.ui.cadastro;

import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fernandoapp.R;


public class CadastroViewModel extends ViewModel {

    private MutableLiveData<CadastroFormState> cadastroFormState = new MutableLiveData<>();

    LiveData<CadastroFormState> getCadastroFormStatee() {
        return cadastroFormState;
    }

    public void campoTextoMudou(EditText campo, String valor) {
        if (!isCampoValido(valor)) {
            cadastroFormState.setValue(new CadastroFormState(R.string.NullField));
        } else {
            cadastroFormState.setValue(new CadastroFormState(true));
        }
    }

    private boolean isCampoValido(String valor){
        return valor!=null;
    }

    LiveData<CadastroFormState> getCadastroFormState() {
        return cadastroFormState;
    }
}
