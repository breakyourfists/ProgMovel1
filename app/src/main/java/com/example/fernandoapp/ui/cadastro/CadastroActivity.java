package com.example.fernandoapp.ui.cadastro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fernandoapp.R;
import com.example.fernandoapp.data.model.Usuario;
import com.example.fernandoapp.ui.login.LoginActivity;
import com.example.fernandoapp.ui.principal.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {
    private CadastroViewModel cadastroViewModel;
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
                File arquivoFoto = null;
                arquivoFoto = criarArquivoFoto();

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

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            File storageDir = criarArquivoFoto();
        }
    }*/

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
            //4
            File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
            try {
                file.createNewFile();
                FileOutputStream fo = new FileOutputStream(file);
                //5
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (IOException e) {
                Log.i("erro","xxx"+e.toString());
                Log.i("erro","xxx"+e.getLocalizedMessage());
                Log.i("erro","xxx"+e.getMessage());
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
        image.getCanonicalPath();
        } catch (IOException e) {
            Log.i("erro",e.getMessage());
        }
        return image;
    }

    void enviarCadastro(ArrayList<EditText> campos){

        Usuario usuario = new Usuario();

        for (EditText campo: campos) {
            switch (campo.getId()){
                case R.id.campoNome:{
                    usuario.setNome(campo.getText().toString());
                    break;
                }
                case R.id.campoEmail:{
                    usuario.setEmail(campo.getText().toString());
                    break;
                }
                case R.id.campoSenha:{
                    usuario.setSenha(campo.getText().toString());
                    break;
                }
                case R.id.disciplinaCampo:{
                    usuario.setDisciplina(campo.getText().toString());
                    break;
                }
                case R.id.telefoneCampo:{
                    usuario.setTelefone(campo.getText().toString());
                    break;
                }
                case R.id.turmaCampo:{
                    usuario.setTurma(campo.getText().toString());
                }
            }
        }

        Toast.makeText(this, "Por favor, faça o login.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("usuario",usuario);

        startActivity(intent);
    }

}
