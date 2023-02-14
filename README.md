# Sensorit2023
Android sovellus sensorien esittelyyn. Tässä tutoriaalissa rakennetaan valosensoria kuunteleva sovellus. Valosensorin arvo esitetään ruudulla.
* oheismateriaalina codelabs esimerkki [https://codelabs.developers.google.com/codelabs/advanced-android-training-sensor-data/index.html#0](https://codelabs.developers.google.com/codelabs/advanced-android-training-sensor-data/index.html#0)
* Lisää dokumentaatioo [https://developer.android.com/guide/topics/sensors/sensors_overview.html](https://developer.android.com/guide/topics/sensors/sensors_overview.html)

## Sovellus
* Luo uusi projekti Empty activity - projektipohjalla.

### MainActivity.java
* Aluksi muokataan luokka määrittely alla olevan mukaiseksi, eli otetaan sensorien kuuntelija mukaan
```java
   public class MainActivity extends AppCompatActivity implements SensorEventListener {
```
* Jonka seurauksena on lisättävä metodit (alla) mukaan
```java
    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
```
* Esitellään muuttujia
```java
    private SensorManager mSensorManager;
    private Sensor mSensorLight;

    private TextView mTextSensorLight;
```
* SensorManager vastaa kaikista sensoreista.
* mSensorLight -muuttuja sensorille, jota käsitellään.
* mtextSensorLight - muuttujaa tarvitaan kun arvoa kirjoitetaan näkymään.
* (Jokainen riveistä vaatii myös import -lauseen)

* Määritellään onCreate() -metodissa äsken esitellyt muuttujat. 

```java
   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"alkoi");
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mTextSensorLight = (TextView) findViewById(R.id.label_light);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        String sensor_error = getResources().getString(R.string.error_no_sensor);

        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
   }
```
* mSensorManager hoitaa manageri tehtävät kaikille sensoreille
* mTextSensorLight kytketään näytön TextView -muuttujaan(myöhemmin lisää)
* mSensorLight kytketään managerin kautta haluttuun sensoriin
* if -lause varmistaa sensorin olemassaolon laitteessa
* Tämän tiedoston muokkausta jatketaan vielä myöhemmin...


### activity_main.xml
* Muokataan näkymä kuntoon, eli muutetaan TextView-komponentti allaolevan mukaiseksi.

```xml
   <TextView
        android:id="@+id/label_light"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:text="@string/label_light"
        android:textSize="30sp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

### values/strings.xml
* Lisätään kaksi muuttujaa.
* label_light -muuttujasta tehdään dynaaminen määrittelemällä sille muuttujan (myöhemmin mainittu **currentValue**) arvon esitystapa
```xml
    <string name="label_light">Light Sensor: %1$.2f</string>
    <string name="error_no_sensor">No sensor</string>
```

### onSensorChanged() -metodi
* Sensoritapahtumien käsittely
* Tässä käsitellään vain yhtä sensoria, mutta voisi olla usempiakin
* Jotkut sensorit antavat useamman arvon joten event.values[0] on määritelty talukoksi, josta tämänsensorin tapauksessa käytetään vain ensimmäinen.(orientation antaisi kolme arvoa, eli event.values[0], event.values[1] ja event.values[2])
* currentValue liitetään edellä mainittuun label_light -muuttujaan
```java
   public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                // Handle light sensor
                mTextSensorLight.setText(getResources().getString(R.string.label_light, currentValue));
                break;
            default:
                // do nothing
        }
    }
```


## Sovelluksen elinkaareen liittyvät metodit
* sovelluksen käynnistyessä asetetaan kuuntelija sensorille
```java
    @Override
    protected void onStart() {
        super.onStart();
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
```

* Ja sovelluksen lopettaessa poistetaan kuuntelija.

```java
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }
```

15.3.2021 - Toimii edelleen
14.3.2022 - Toimii edelleen
14.2.2023 - Updated