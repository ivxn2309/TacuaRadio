package peida.app.tacuaradio;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	private MediaPlayer mPlayer = new MediaPlayer();
	//private String mUrl = "http://yp.shoutcast.com/sbin/tunein-station.pls?id=237660";
	private String mUrl = "http://listen.radionomy.com/Sax4Love?Title1=Sax4Love&Length1=-1&Version=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void stopStreaming(View view){
    	Toast.makeText(this, "Detenido", Toast.LENGTH_LONG).show();
		mPlayer.stop();
    }
    
    public void pauseStreaming(View view){
    	Toast.makeText(this, "Pausa", Toast.LENGTH_LONG).show();
		mPlayer.pause();
    }
    
    public void unpauseStreaming(View view){
    	mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer stream) {
				stream.start();
			}
		});
        Toast.makeText(this, "Listo", Toast.LENGTH_SHORT).show();
        try {
        	mPlayer.prepare();
        }catch(Exception ex){
        	ex.printStackTrace();
        }
    }
    
    public void startStreaming(View view){
    	Switch sw = (Switch)findViewById(R.id.power_button);
    	if(sw.isChecked()){
    		Toast.makeText(this, "Encendiendo Radio...", Toast.LENGTH_SHORT).show();
    		String streamingAddr = mUrl;
            try {
    			mPlayer.setDataSource(streamingAddr);
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (SecurityException e) {
    			e.printStackTrace();
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
    			@Override
    			public void onPrepared(MediaPlayer stream) {
    				stream.start();
    			}
    		});
            Toast.makeText(this, "Iniciado", Toast.LENGTH_SHORT).show();
            try {
            	mPlayer.prepare();
            	Toast.makeText(this, "Preparado", Toast.LENGTH_SHORT).show();
            }catch(Exception ex){
            	ex.printStackTrace();
            }
    	}
    	else{
    		Toast.makeText(this, "Apagando Radio", Toast.LENGTH_LONG).show();
    		mPlayer.stop();
    	}
    }
}
