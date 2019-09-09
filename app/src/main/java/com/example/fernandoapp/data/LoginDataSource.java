package com.example.fernandoapp.data;

import com.example.fernandoapp.data.model.LoggedInUser;
import com.example.fernandoapp.data.model.Usuario;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private static HashMap<String,Usuario> usuarios = new HashMap<String,Usuario>();

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            Usuario usuario = usuarios.get(username);
            if(usuario!=null && usuario.getSenha().equals(password)) {
                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                usuario.getNome());
                return new Result.Success<>(fakeUser);
            }else{
                return new Result.Error(new IOException("Usuário inválido."));
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }


    }

    public static HashMap<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
