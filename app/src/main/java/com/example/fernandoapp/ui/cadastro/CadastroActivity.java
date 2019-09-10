package com.example.fernandoapp.ui.cadastro;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.dao.UsuarioDAO;
import com.example.fernandoapp.ui.login.LoginActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CadastroActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imageView;
    String currentPhotoPath;
    EditText campoNomeEditText;
    EditText campoEmailEditText;
    EditText campoSenhaEditText;
    EditText disciplinaCampoEditText;
    EditText telefoneCampoEditText;
    EditText turmaCampoEditText;
    ArrayList<EditText> campos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Bundle extras = getIntent().getExtras();

        campoNomeEditText = findViewById(R.id.campoNome);
        campoEmailEditText = findViewById(R.id.campoEmail);
        campoSenhaEditText = findViewById(R.id.campoSenha);
        disciplinaCampoEditText = findViewById(R.id.disciplinaCampo);
        telefoneCampoEditText = findViewById(R.id.telefoneCampo);
        turmaCampoEditText = findViewById(R.id.turmaCampo);
        imageView = findViewById(R.id.imageView);

        campos = new ArrayList<>();
        campos.add(campoNomeEditText);
        campos.add(campoEmailEditText);
        campos.add(campoSenhaEditText);
        campos.add(disciplinaCampoEditText);
        campos.add(telefoneCampoEditText);
        campos.add(turmaCampoEditText);

        if(extras !=null) {
            String email = extras.getString("email");
            campoEmailEditText.setText(email);
        }
    }

    public void editarAvatarButton(View view){
        enviarCapturaDeFoto();
    }

    public void cadastrarButton(View view){
        if(isCamposValidos(campos)){
            enviarCadastro(campos);
        }
    }

    boolean isCamposValidos(ArrayList<EditText> campos){
        for (EditText campo: campos) {
                String valor = campo.getText().toString();
            if (valor.matches("")) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void enviarCapturaDeFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void enviarCapturaDefoto2() {
        Intent fotoCapturada = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            if (fotoCapturada.resolveActivity(getPackageManager()) != null) {
                File arquivoFoto = criarArquivoFoto();

                if (arquivoFoto != null) {

                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.provider",
                            arquivoFoto);

                    fotoCapturada.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(fotoCapturada, REQUEST_IMAGE_CAPTURE);
                    imageView.setImageURI(photoURI);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i("erro","xxx"+e.toString());
            Log.i("erro","xxx"+e.getLocalizedMessage());
            Log.i("erro","xxx"+e.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_IMAGE_CAPTURE);

            //2
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            //3
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String filename = campoEmailEditText.getText().toString().substring(0, 3) + "_avatar.jpg";
            File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "fernandoApp");
            dir.mkdirs();
            File file = new File(dir + File.separator + filename);

            try {
                FileOutputStream out = new FileOutputStream(file);
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                // PNG is a lossless format, the compression factor (100) is ignored
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private File criarArquivoFoto() {
        String imageFileName = "Avatar_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

        currentPhotoPath = image.getAbsolutePath();
        } catch (IOException e) {
            Log.i("erro",e.getMessage());
        }
        return image;
    }

    void enviarCadastro(ArrayList<EditText> campos){

        String nomeStr = campoNomeEditText.getText().toString();
        String emailStr = campoEmailEditText.getText().toString();
        String senhaStr = campoSenhaEditText.getText().toString();
        String disciplinaStr = disciplinaCampoEditText.getText().toString();
        String telefoneStr = telefoneCampoEditText.getText().toString();
        int turmaInt = Integer.parseInt(turmaCampoEditText.getText().toString());
        UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
        try {
            usuarioDAO.inserirUsuario(nomeStr, emailStr, telefoneStr, turmaInt, senhaStr, disciplinaStr);
            Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } catch (SQLiteConstraintException e) {
            String msg = e.getMessage();
            if (e.getMessage().contains("UNIQUE") && e.getMessage().contains("usuarios.email"))
                msg = "Email já cadastrado.";
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            Log.i("usuario", "Email: " + emailStr + " Senha: " + senhaStr + " Erro: " + e.getMessage());
            e.printStackTrace();
            reload();
        }

    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

}
