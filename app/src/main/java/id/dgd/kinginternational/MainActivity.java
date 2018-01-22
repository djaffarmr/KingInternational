package id.dgd.kinginternational;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // variables
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout viewPagerIndicator;
    private Timer timer;
    private int dotsCount;
    private ImageView[] dot;
    private String feature;
    private Intent intent;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // custom toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // disable home button
        ImageButton btnHome = (ImageButton) findViewById(R.id.btn_home);
        btnHome.setVisibility(View.GONE);

        // state the viewpager and set the adapter to viewpager adapter class
        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // set the indicator for viewpager
        viewPagerIndicator = (LinearLayout) findViewById(R.id.main_viewpager_indicator);
        dotsCount = viewPagerAdapter.getCount();
        dot = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dot[i] = new ImageView(this);
            dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            viewPagerIndicator.addView(dot[i], params);
        }

        dot[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // do nothing
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));
                }
                dot[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // do nothing
            }
        });

        // automatically call the viewpager timer class every 6s
        timer = new Timer();
        timer.scheduleAtFixedRate(new ViewPagerTimer(), 3000, 6000);
    }

    // viewpager adapter class that manage viewpager content
    private class ViewPagerAdapter extends PagerAdapter {
        // variables
        private Context context;
        private LayoutInflater layoutInflater;
        private int[] images =  {R.drawable.img_viewpager_1,
                R.drawable.img_viewpager_2,
                R.drawable.img_viewpager_3
        };

        private ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.main_viewpager, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.img_viewpager);
            imageView.setImageResource(images[position]);

            ViewPager viewPager = (ViewPager) container;
            viewPager.addView(view, 0);
            return  view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    // viewpager timer class
    private class ViewPagerTimer extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    // inflate the menu. add item to toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    // overflow menu content
    Toast toast;
    String message = "icons made by Freepik from\nwww.flaticon.com";
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.credits) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
        }
        return  super.onOptionsItemSelected(item);
    }

    // features onClick method. set from XML. to determine what string to pass
    public void pesawatBtn(View v) {
        feature = "pesawat";
        loadWeb(feature);
    }

    public void keretaBtn(View v) {
        feature = "kereta";
        loadWeb(feature);
    }

    public void hotelBtn(View v) {
        feature = "hotel";
        loadWeb(feature);
    }

    public void umrohBtn(View v) {
        feature = "umroh";
        loadWeb(feature);
    }

    public void busBtn(View v) {
        feature = "bus";
        loadWeb(feature);
    }

    public void transportBtn(View v) {
        feature = "transport";
        loadWeb(feature);
    }

    public void ppobBtn(View v) {
        feature = "ppob";
        loadWeb(feature);
    }

    public void pulsaBtn(View v) {
        feature = "pulsa";
        loadWeb(feature);
    }

    public void turBtn(View v) {
        feature = "tur";
        loadWeb(feature);
    }

    public void agenBtn(View v) {
        feature = "agen";
        loadWeb(feature);
    }

    public void kontakBtn(View v) {
        feature = "kontak";
        loadWeb(feature);
    }

    public void keluarBtn(View v) {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // pass the feature string. start the activity
    public void loadWeb(String extraString) {
        intent = new Intent(MainActivity.this, WebPagesActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, extraString);

        // check if target device has app to open web page or not
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // set that user must press back button twice to exit the app
    @Override
    public void onBackPressed() {

        if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
            // super.onBackPressed();
            moveTaskToBack(true);
        } else {
            Toast.makeText(MainActivity.this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}
