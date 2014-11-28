package peida.app.tacuaradio;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
	
	private MediaPlayer mPlayer = new MediaPlayer();
	//private String mUrl = "http://yp.shoutcast.com/sbin/tunein-station.pls?id=237660";
	private String mUrl = "http://listen.radionomy.com/Sax4Love?Title1=Sax4Love&Length1=-1&Version=2";
	private SeekBar mVolumeSeekbar = null;
    private AudioManager mAudioManager = null; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.activity_main);
        prepareVolumeControl();
    }
    
    private void prepareVolumeControl() {
        try {
            mVolumeSeekbar = (SeekBar)findViewById(R.id.volume);
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mVolumeSeekbar.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            mVolumeSeekbar.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));   

            mVolumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {}

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {}

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void stopStreaming(View view){
    	Switch sw = (Switch)findViewById(R.id.power_button);
    	Toast.makeText(this, "Detenido", Toast.LENGTH_SHORT).show();
		mPlayer.pause();
		mPlayer.stop();
		mPlayer.reset();
		sw.setChecked(false);
    }
    
    public void pauseStreaming(View view){
    	Toast.makeText(this, "Pausa", Toast.LENGTH_SHORT).show();
		mPlayer.pause();
    }
    
    public void unpauseStreaming(View view){
    	mPlayer.start();
    }
    
    public void recordStreaming(View view){
    	Toast.makeText(this, "Sin implementar", Toast.LENGTH_SHORT).show();
    }
    
    public void openSettings(View view){
    	Toast.makeText(this, "Sin implementar", Toast.LENGTH_SHORT).show();
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
    		Toast.makeText(this, "Apagando Radio", Toast.LENGTH_SHORT).show();
    		mPlayer.pause();
    		mPlayer.stop();
    		mPlayer.reset();
    	}
    }
}
