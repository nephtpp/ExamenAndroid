package com.nep.examenandroid.data.repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.nep.examenandroid.data.dto.FileResponse;
import com.nep.examenandroid.data.dto.User;
import com.nep.examenandroid.data.network.RetrofitClient;
import com.nep.examenandroid.ui.auth.AuthViewModel;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.nep.examenandroid.Utils.logErrorMessage;

public class Repo {

    private MutableLiveData<FileResponse> fileMutable;
    private MutableLiveData<User> userAuntenticado;
    private MutableLiveData<File> myFile;
    private MutableLiveData<String> onError = new MutableLiveData<>();

    FirebaseAuth auth = FirebaseAuth.getInstance();
//    FirebaseStorage storage = FirebaseStorage.getInstance();

    public MutableLiveData<User> getUserAuntenticado() {
        return userAuntenticado;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }

    public MutableLiveData<File> getMyFile() {
        return myFile;
    }

    public void firebaseSignIn(String email, String pass) {
         userAuntenticado = new MutableLiveData<>();
//        User userAuntenticado = new User();
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    if (firebaseUser != null ) {
                        String uid = firebaseUser.getUid();
                        String e = firebaseUser.getEmail();
                        String name = firebaseUser.getDisplayName();
                        User user1 = new User(uid, e, name);
                        userAuntenticado.postValue(user1);

                        Log.i(AuthViewModel.TAG, " signInWithEmailAndPassword");
                    }
                } else {
                    String s = task.getException().getMessage();
                    onError.setValue(s);
                    logErrorMessage(task.getException().getMessage());
                }
            }
        });
    }

    public MutableLiveData<FileResponse> getFile() {
        fileMutable = new MutableLiveData<>();
        Call<FileResponse> call = RetrofitClient.getInstance().getMyConexion().getFile();
        call.enqueue(new Callback<FileResponse>() {
            @Override
            public void onResponse(Call<FileResponse> call, Response<FileResponse> response) {
                fileMutable.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FileResponse> call, Throwable t) {
//               Toast.makeText(this, "persona nombre " + persona.getNombre(), Toast.LENGTH_SHORT).show();
                Log.i("", t.getMessage());
            }
        });

        return fileMutable;
    }

//    public MutableLiveData<File> downloadFile(Context context, String url) {
//        myFile = new MutableLiveData<>();
//        IConexion conexion = FileDownloader.createService(IConexion.class);
//        Call<ResponseBody> call = conexion.downloadFile(url);
////        Call<ResponseBody> call = RetrofitClient.getInstance().getMyConexion().downloadFile(url);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                String fileName = "employes_data_json";
//                if (response.isSuccessful()) {
//
//                    if (myFile != null) {
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.i("loggin", "myFile " + t.getMessage());
//
//            }
//        });
//        return myFile;
//    }

    public void downloadFileFromFirebase(String url, Context context) throws IOException {
//        String data = downLoadHttp(new URL(url));

        new RetrieveTask(context).execute(url);

    }

    public static File downLoadHttp(URL url, Context context) throws IOException {

        // código de conexión,
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setRequestMethod("GET");
        c.setReadTimeout(15 * 1000);
        c.setUseCaches(true);
        c.connect();


        String fileName = "";
        ZipInputStream zis;
        InputStream is = c.getInputStream();
        zis = new ZipInputStream(new BufferedInputStream(is));
        ZipEntry ze;
        byte[] buffer = new byte[1024];
        int count;
        while ((ze = zis.getNextEntry()) != null) {
            fileName = ze.getName();
            if (ze.isDirectory()) {
                File fmd = new File(fileName);
                fmd.mkdirs();
                continue;
            }
            FileOutputStream fout = new FileOutputStream(new File(context.getCacheDir() + "/" + fileName));//
            while ((count = zis.read(buffer)) != -1) {
                fout.write(buffer, 0, count);
            }
            fout.close();
            zis.closeEntry();
        }
//        FileInputStream fis = null;// new File(context.getCacheDir(), fileName)

        return new File(context.getCacheDir(), fileName);
    }



    class RetrieveTask extends AsyncTask<String, Void, File> {

        private Exception exception;
        Context context;

        public RetrieveTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myFile = new MutableLiveData<>();
        }

        @Override
        protected File doInBackground(String... urls) {
            File myFile= null;
            try {
               myFile =  downLoadHttp(new URL(urls[0]), context);
            } catch (Exception e) {
                this.exception = e;

            }
            return myFile;
        }

        @Override
        protected void onPostExecute(File file) {
            super.onPostExecute(file);
            myFile.setValue(file);
        }
    }

}
