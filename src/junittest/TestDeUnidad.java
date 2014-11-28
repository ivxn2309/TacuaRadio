package junittest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Switch;

import peida.app.tacuaradio.MainActivity;
import peida.app.tacuaradio.R;

/**
 * Created by Jaime on 27/11/2014.
 */
public class TestDeUnidad extends ActivityInstrumentationTestCase2<MainActivity>{

    private MainActivity mFirstTestActivity;
    private String mFirstTestText;


    public TestDeUnidad() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFirstTestActivity = getActivity();
        mFirstTestText =
                mFirstTestActivity.getString(R.string.app_name);
    }

    public void testPreconditions() {
        assertNotNull("mFirstTestActivity is null", mFirstTestActivity);
        assertNotNull("mFirstTestText is null", mFirstTestText);
    }

    public void testMyFirstTestTextView_labelText() {
        final String expected = "TacuaRadio";
        final String actual = mFirstTestText; //.getText().toString();
        assertEquals("comparando el nombre de la aplicacion",expected, actual);
    }

    public void testSwitchName(){
        final String expected = "Radio";
        final Switch actual = (Switch)mFirstTestActivity.findViewById(R.id.power_button);
        final String sActual = actual.getText().toString();

        assertEquals("comparando el estado inicial del boton",expected,sActual);
    }

    public void testSwitchOFF(){
        final String expected = "OFF";
        final Switch actual = (Switch)mFirstTestActivity.findViewById(R.id.power_button);
        final String sActual = actual.getTextOff().toString();

        assertEquals("comparando el estado Apagado del Switch",expected,sActual);
    }

    public void testSwitchON(){
        final String expected = "ON";
        final Switch actual = (Switch)mFirstTestActivity.findViewById(R.id.power_button);
        final String sActual = actual.getTextOn().toString();

        assertEquals("comparando el estado ON del Switch",expected,sActual);
    }

    /*  Test para cubrir el formato de pantalla           */
    /*  Se probaran que los layouts de los ImageButtons   */
    /*  Esten colocados correctamente,                    */
    /*  Segun lo ya establecido en el diseño              */

     public void testStopButtonLayout() {

        ImageButton mClickMeButton = (ImageButton)
                mFirstTestActivity
                        .findViewById(R.id.imageButton1);

        View decorView = mFirstTestActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickMeButton);

        ViewGroup.LayoutParams layoutParams =
                mClickMeButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void testPlayButtonLayout() {

        ImageButton mClickMeButton = (ImageButton)
                mFirstTestActivity
                        .findViewById(R.id.imageButton2);

        View decorView = mFirstTestActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickMeButton);

        ViewGroup.LayoutParams layoutParams =
                mClickMeButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void testPauseButtonLayout() {

        ImageButton mClickMeButton = (ImageButton)
                mFirstTestActivity
                        .findViewById(R.id.imageButton3);

        View decorView = mFirstTestActivity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(decorView, mClickMeButton);

        ViewGroup.LayoutParams layoutParams =
                mClickMeButton.getLayoutParams();
        assertNotNull(layoutParams);
        assertEquals(layoutParams.width, WindowManager.LayoutParams.WRAP_CONTENT);
        assertEquals(layoutParams.height, WindowManager.LayoutParams.WRAP_CONTENT);
    }


}