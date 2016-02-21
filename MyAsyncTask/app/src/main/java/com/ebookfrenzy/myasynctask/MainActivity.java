package com.ebookfrenzy.myasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ProgressBar progressBar;
    BackgroundTask task;

    int value = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         textView = (TextView) findViewById(R.id.textView);
         progressBar = (ProgressBar)findViewById(R.id.progressBar);
    }

    public void onButton1Clicked(View v){

         task = new BackgroundTask();
        task.execute(100);

    }
    public void onButton2Clicked(View v){

        task.cancel(true);

    }

    class BackgroundTask extends AsyncTask<Integer,Integer,Integer>{



        @Override
        protected void onPreExecute() {

            value = 0;
            progressBar.setProgress(value);

    }

        @Override
        protected void onPostExecute(Integer integer) {
            value = 0;
            progressBar.setProgress(value);
            textView.setText("중지됨.  ");

        }


        @Override
        protected void onProgressUpdate(Integer... values) {

            progressBar.setProgress(values[0].intValue());
            textView.setText("진행중"+values[0]);

            //publishProgress가 호출될 때 onProgressUpdate 호출됨
            super.onProgressUpdate(values);
        }


        //위의 3개는 메인스레드에서 동작

        //밑에 있는 것은 백그라운드 스레드에서 동작
        @Override
        protected Integer doInBackground(Integer... params) {
         while(!isCancelled()){
             //취소 되지 않은 상태-> 계속 동작

             value++;
                if(value>=100){break;}
             else{
                 publishProgress(value);
             }

             try{
                 Thread.sleep(200);
             }catch (Exception e){}

             return value;


         }

            return null;
        }
    }
}
