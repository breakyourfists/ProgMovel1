package com.example.fernandoapp.ui.cadastro;

import androidx.annotation.Nullable;

class CadastroFormState {
    @Nullable
    private Integer campoVazioErro;
    @Nullable
    private boolean isDataValid;

    CadastroFormState(@Nullable Integer campoVazioErro) {
        this.campoVazioErro = campoVazioErro;
        this.isDataValid = false;
    }

    CadastroFormState(boolean isDataValid) {
        this.campoVazioErro = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getCampoVazioErro() {
        return campoVazioErro;
    }

    @Nullable
    boolean isDataValid() {
        return isDataValid;
    }
}
