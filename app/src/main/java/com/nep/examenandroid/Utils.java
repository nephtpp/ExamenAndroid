package com.nep.examenandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.Observable;
import android.os.Environment;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Flow;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;

public class Utils {
    public static final String TAG = "log_error";

    public static void logErrorMessage(String errorMessage) {
        Log.d(TAG, errorMessage);
    }

    public static String fileToString(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fis = null;// new File(context.getCacheDir(), fileName)
        try {
            fis = new FileInputStream(file);

            if (fis != null) {
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(fis, "UTF-8"));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                Log.i("users", stringBuilder.toString());

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }





}
