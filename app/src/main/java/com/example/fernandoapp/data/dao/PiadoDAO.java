package com.example.fernandoapp.data.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.example.fernandoapp.data.model.Piado;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class PiadoDAO extends AsyncTask<String, String, String> {
    private final String API_URL = "https://bunmiw2kwb.execute-api.us-east-2.amazonaws.com/Teste2/";

    public List<Piado> getPiadosPorUsuario(int autorId) {
        String stringUrlGetPiados = API_URL + "?funcao=getPiadosPorAutor&autor_id=" + autorId;
        ArrayList<Piado> piados = new ArrayList<>();
        InputStream responseBody = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };
        try {
            URL urlGetPiados = new URL(stringUrlGetPiados);
            HttpsURLConnection myConnection = (HttpsURLConnection) urlGetPiados.openConnection();
            if (myConnection.getResponseCode() == 200) {
                responseBody = myConnection.getInputStream();

                BufferedInputStream in = new BufferedInputStream(responseBody);
                byte[] contents = new byte[1024];

                int bytesRead = 0;
                String strFileContents = "";
                while ((bytesRead = in.read(contents)) != -1) {
                    strFileContents += new String(contents, 0, bytesRead);
                }
                JSONArray piadosJson = new JSONArray(strFileContents);
                for (int i = 0; i < piadosJson.length(); i++) {
                    piados.add(new Piado(piadosJson.getJSONObject(i)));
                }
            } else {
                Log.e("PiadoDAO", "Erro no request para a API. Erro: " + myConnection.getResponseCode());
            }
        } catch (Exception e) {
            Log.e("PiadoDAO", "Erro interno no processamento da resposta da API. Erro: ", e);
        } finally {
            try {
                responseBody.close();
            } catch (IOException e) {
                Log.e("PiadoDAO", "Não foi possível fechar o response body. Erro: ", e);
            }
        }

        return piados;
    }

    public boolean addPiado(Piado piado) {
        String[] valores = {"addPiado", String.valueOf(piado.getAutorId()), piado.getMensagem(), piado.getUrl()};
        doInBackground(valores);
        return true;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params[0] == "addPiado") {
            try {
                URL url = new URL(API_URL + "?funcao=addPiado");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST"); // here you are telling that it is a POST request, which can be changed into "PUT", "GET", "DELETE" etc.
                httpURLConnection.setRequestProperty("Content-Type", "application/json"); // here you are setting the `Content-Type` for the data you are sending which is `application/json`
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 ( compatible ) ");
                httpURLConnection.setRequestProperty("Accept", "application/json, text/plain, */*");
                httpURLConnection.connect();

                JSONObject piado = new JSONObject();
                piado.put("usuario_id", params[1]);
                piado.put("mensagem", params[2]);
                piado.put("url", params[3]);

                JSONObject main = new JSONObject();
                main.put("piado", piado);


                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.writeBytes(main.toString());
                wr.flush();
                wr.close();

                int status = httpURLConnection.getResponseCode();
                if (status != 200) {
                    Log.e("UsuarioDAO", "resposta: " + status);
                    InputStream response = httpURLConnection.getErrorStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(response));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        Log.e("UsuarioDAO", "resposta: " + sb.toString());
                    } catch (IOException e) {
                        Log.e("UsuarioDAO", "Erro interno na leitura do post da API. Erro: ", e);
                    } finally {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            Log.e("UsuarioDAO", "Erro interno na leitura do post da API 2. Erro: ", e);
                        } finally {
                        }
                    }
                }

            } catch (Exception e) {
                Log.e("UsuarioDAO", "Erro interno no post da API. Erro: ", e);
            }
            return "Sucesso";
        }
        return null;
    }
}
