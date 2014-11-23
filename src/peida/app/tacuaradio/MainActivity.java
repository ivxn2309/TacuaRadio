package peida.app.tacuaradio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.ByteArrayBuffer;

import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        MediaPlayer player = new MediaPlayer();
        String url = "http://50.22.217.113:29446";
        String streamingAddr = getStreamingURL(url);
        
        try {
			player.setDataSource(streamingAddr);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer stream) {
				// Set the Wake Lock - CPU on, keyboard and screen off
				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
				WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
						getString(R.string.app_name));
				wl.acquire();
				stream.start();
			}
		});
        
        try {
        	player.prepare();
        }catch(Exception ex){
        	ex.printStackTrace();
        }
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
    
    public String getStreamingURL(String string) {

		String html = "";
		String result = "";
		try {
			URL updateURL = new URL(string);
			URLConnection conn = updateURL.openConnection();
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);

			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			html = new String(baf.toByteArray());

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
		return result;
	}
}
