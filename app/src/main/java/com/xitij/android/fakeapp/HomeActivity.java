package com.xitij.android.fakeapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class HomeActivity extends ActionBarActivity {

    private Button btnDetectGame;
    private Button btnPatchGame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDetectGame = (Button) findViewById(R.id.btnDetectGame);
        btnPatchGame = (Button) findViewById(R.id.btnPatchGame);
        btnPatchGame.setOnClickListener(patchListner);
        btnDetectGame.setOnClickListener(detectListner);

    }

    private View.OnClickListener detectListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            processDetect();
        }
    };


    private View.OnClickListener patchListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            processPatch();

        }
    };

    private void processPatch() {
        final Handler updateBarHandler;
        updateBarHandler = new Handler();

        final Handler updateBarHandlerAnother;
        updateBarHandlerAnother = new Handler();


        final ProgressDialog progressPatch;
        progressPatch = new ProgressDialog(this);
        progressPatch.setMessage("Connecting Server");
        progressPatch.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //   progressPatch.setIndeterminate(true);
        progressPatch.setProgress(0);
        progressPatch.setMax(100);
        progressPatch.show();

        final int totalProgressTime = 100;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Here you should write your time consuming task...
                    while (progressPatch.getProgress() <= progressPatch.getMax()) {

                        Thread.sleep(200);
                        updateBarHandler.post(new Runnable() {

                            public void run() {
                                progressPatch.incrementProgressBy(1);

                            }

                        });

                        if (progressPatch.getProgress() == progressPatch.getMax()) {
                            progressPatch.dismiss();

                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    showPatchSuccessDialog();
                                }
                            });

                        }

                        updateBarHandlerAnother.post(new Runnable() {
                            @Override
                            public void run() {

                                if (progressPatch.getProgress() > 0 && progressPatch.getProgress() <= 12) {
                                    progressPatch.setMessage("Validating blocks");
                                } else if (progressPatch.getProgress() > 13 && progressPatch.getProgress() <= 20) {
                                    progressPatch.setMessage("Connecting to server");
                                } else if (progressPatch.getProgress() > 20 && progressPatch.getProgress() <= 35) {
                                    progressPatch.setMessage("Downloding data");
                                } else if (progressPatch.getProgress() > 35 && progressPatch.getProgress() <= 45) {
                                    progressPatch.setMessage("Extracting data");
                                } else if (progressPatch.getProgress() > 45 && progressPatch.getProgress() <= 65) {
                                    progressPatch.setMessage("Calculating CRC values");
                                } else if (progressPatch.getProgress() > 65 && progressPatch.getProgress() <= 75) {
                                    progressPatch.setMessage("Packaging data");
                                } else if (progressPatch.getProgress() > 75 && progressPatch.getProgress() <= 85) {
                                    progressPatch.setMessage("Creating files");
                                } else if (progressPatch.getProgress() > 85 && progressPatch.getProgress() <= 100) {
                                    progressPatch.setMessage("Patching..");
                                }

                            }
                        });

                    }
                } catch (Exception e) {
                }
            }
        }).start();

    }

    private void showDetectSuccessDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Congrates")
                .setMessage("Game detected")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showPatchSuccessDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Congrates")
                .setMessage("Game Patched")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void processDetect() {

        final ProgressDialog progressDialogPatch = ProgressDialog.show(HomeActivity.this, "Please wait", "Detecting Game");

        // 8 seconds wait
        new CountDownTimer(8000, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                progressDialogPatch.dismiss();
                showDetectSuccessDialog();
            }
        }.start();

    }


}
