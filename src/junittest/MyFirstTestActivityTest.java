import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import peida.app.tacuaradio.MainActivity;
import peida.app.tacuaradio.R;

/**
 * Created by Jaime on 27/11/2014.
 */
public class MyFirstTestActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mFirstTestActivity;
    private String mFirstTestText;

    public MyFirstTestActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFirstTestActivity = getActivity();
        mFirstTestText =
                 mFirstTestActivity.getString(R.string.app_name);
    }

    public void testMyFirstTestTextView_labelText() {
        final String expected = "TacuaRadio";
        final String actual = mFirstTestText; //.getText().toString();
        assertEquals("comparando el nombre de la aplicacion",expected, actual);
    }

    //public void testMyButton_labelText() {
      //  final String expected = "Apagado";
        //final View v = (View)mFirstTestActivity.findViewById(R.id.power_button);
        //final String actual =  v.toString();//.getText().toString();
        //assertEquals("comparando el label del boton",expected, actual);
    //}

}
