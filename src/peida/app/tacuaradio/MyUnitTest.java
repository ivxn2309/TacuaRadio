package peida.app.tacuaradio;


import peida.app.tacuaradio.MainActivity;
import peida.app.tacuaradio.R;
import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Jaime on 27/11/2014.
 */
public class MyUnitTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mFirstTestActivity;
    private String mFirstTestText;

    public MyUnitTest() {
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

