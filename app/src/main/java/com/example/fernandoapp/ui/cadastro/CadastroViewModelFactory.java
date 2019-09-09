package com.example.fernandoapp.ui.cadastro;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.fernandoapp.ui.login.LoginViewModel;

public class CadastroViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CadastroViewModel.class)) {
            return (T) new CadastroViewModel();
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}

