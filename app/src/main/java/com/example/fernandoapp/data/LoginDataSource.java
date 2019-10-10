package com.example.fernandoapp.data;

import android.content.Context;
import android.util.Log;

import com.example.fernandoapp.data.dao.UsuarioDAO;
import com.example.fernandoapp.data.model.Usuario;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private Context context;

    public Result<Usuario> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            Usuario usuario = usuarioDAO.getUsuario(username, password);
            if (usuario != null) {
                return new Result.Success<>(usuario);
            } else {
                return new Result.Error(new IOException("Usuário inválido."));
            }
        } catch (Exception e) {
            Log.e("LoginDataSource","erro ao ler usuairo",e);
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
