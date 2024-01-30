package com.mp.program6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mp.program6.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Thread myThread;
    private ActivityMainBinding binding;
    private boolean gameDone;

    //Will be updated based on button clicks, sent to server in run()
    private String currCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Landscape locked
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        binding.logger.setMovementMethod(new ScrollingMovementMethod());

        binding.logger.append("\n");

        currCmd = "";

        binding.hostName.setText("192.168.1.3");
        binding.port.setText("3012");

        binding.upButton.setOnClickListener(this);
        binding.rightButton.setOnClickListener(this);
        binding.downButton.setOnClickListener(this);
        binding.leftButton.setOnClickListener(this);
        binding.shootUpBtn.setOnClickListener(this);
        binding.shootRightBtn.setOnClickListener(this);
        binding.shootDownBtn.setOnClickListener(this);
        binding.shootLeftBtn.setOnClickListener(this);
        binding.scanBtn.setOnClickListener(this);

        binding.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play play = new play();
                myThread = new Thread(play);
                myThread.start();
            }
        });
    }

    class play implements Runnable {

        public PrintWriter out;
        public BufferedReader in;
        public void run(){
            //Change binding select
            int port = Integer.parseInt(binding.port.getText().toString());
            String host = binding.hostName.getText().toString();
            mkMsg("host is " + host);
            mkMsg("Port is " + port);
            int armor = Integer.parseInt(binding.armotEdit.getText().toString());
            int bullet = Integer.parseInt(binding.bulletEdit.getText().toString());
            int scan = Integer.parseInt(binding.scanEdit.getText().toString());

            try{
                InetAddress serverAddr = InetAddress.getByName(host);
                mkMsg("trying to connect... " + host + "\n");
                Socket socket = new Socket(serverAddr, port);

                //Setup read and write
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                gameDone = false;

                //Send message to server
                try{
                    //First, send setup message
                    String setupMsg = "Turgle " + armor + " " + bullet + " " + scan;
                    out.println(setupMsg);

                    //While not dead or gameover from server
                    while(!gameDone){
                        currCmd = "noop";
                        String serverMsg = in.readLine();
                        String[] splitServerMsg = serverMsg.split(" ");
                        mkMsg(serverMsg);

                        if(splitServerMsg[1].equals("Dead") || splitServerMsg[1].equals("GameOver")){
                            gameDone = true;
                        }

                        if(currCmd.contains("move") && (Integer.parseInt(splitServerMsg[3]) < 0)){
                            mkMsg("can't move yet");
                            currCmd = "noop";
                        }else if(currCmd.contains("fire") && (Integer.parseInt(splitServerMsg[4]) < 0)){
                            mkMsg("Can't shoot yet");
                            currCmd = "noop";
                        }
                        out.println(currCmd);
                    }
                    mkMsg("closing connection, game over");
                }catch(Exception e){
                    mkMsg("Error sending/receiving");
                }finally{
                    in.close();
                    out.close();
                    socket.close();
                }

            }catch(Exception e){
                mkMsg("Unable to connect\n" + e.toString());
            }
        }
    }

    @Override
    public void onClick(View view){
        if(view == binding.upButton){
            currCmd = "move 0 -1";
        }else if(view == binding.rightButton){
            currCmd = "move 1 0";
        }else if(view == binding.downButton){
            currCmd = "move 0 1";
        }else if(view == binding.leftButton){
            currCmd = "move -1 0";
        }else if(view == binding.shootUpBtn){
            currCmd = "fire 0";
        }else if(view == binding.shootRightBtn){
           currCmd = "fire 90";
        }else if(view == binding.shootDownBtn){
            currCmd = "fire 180";
        }else if(view == binding.shootLeftBtn){
            currCmd = "fire 270";
        }else if(view == binding.scanBtn){
            currCmd = "scan";
        }
    }

    public void mkMsg(String str){
        Message msg = new Message();
        Bundle b = new Bundle();
        b.putString("msg", str);
        msg.setData(b);
        handler.sendMessage(msg);
    }

    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            binding.logger.append(msg.getData().getString("msg"));
        }
    };
}