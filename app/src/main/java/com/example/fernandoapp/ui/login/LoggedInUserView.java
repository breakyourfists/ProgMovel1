package com.example.fernandoapp.ui.login;

import com.example.fernandoapp.data.model.Usuario;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private Usuario usuario;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDisplayName() {
        return usuario.getNome();
    }


    public Usuario getUsuario() {
        return usuario;
    }
}
