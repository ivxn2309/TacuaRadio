package peida.app.tacuaradio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.ByteArrayBuffer;

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
    
    public void startStreaming(View view){
    	Switch sw = (Switch)findViewById(R.id.power_button);
    	if(sw.isChecked()){
    		Toast.makeText(this, "Encendiendo Radio...", Toast.LENGTH_SHORT).show();
    		String streamingAddr = getStreamingURL(mUrl);
    		Toast.makeText(this, "Conectando a: " + streamingAddr, Toast.LENGTH_SHORT).show();
            try {
    			mPlayer.setDataSource(streamingAddr);
    			Toast.makeText(this, "Id de sesion: " + mPlayer.getAudioSessionId(), Toast.LENGTH_SHORT).show();
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
    		Toast.makeText(this, "Off", Toast.LENGTH_LONG).show();
    	}
    }
    
    //Prepara una URL para ser usada
    public String getStreamingURL(String string) {
    	Toast.makeText(this, "URL: " + string, Toast.LENGTH_SHORT).show();
		String html = "";
		String result = "";
		try {
			URL updateURL = new URL(string);
			URLConnection conn = updateURL.openConnection();
			conn.setConnectTimeout(6000);
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			html = new String(baf.toByteArray());
			Toast.makeText(this, "HTML: " + html, Toast.LENGTH_SHORT).show();
			String re1 = ".*?"; // Non-greedy match on filler
			String re2 = "((?:http|https)(?::\\/{2}[\\w]+)(?:[\\/|\\.]?)(?:[^\\s\"]*))"; // HTTP
			// URL
			// 1

			Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);
			Matcher m = p.matcher(html);
			if (m.find()) {
				String httpurl1 = m.group(1);
				result = httpurl1.toString();
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
}
