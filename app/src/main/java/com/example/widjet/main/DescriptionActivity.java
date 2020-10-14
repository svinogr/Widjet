package com.example.widjet.main;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.widjet.R;
import com.example.widjet.main.dao.PrazdnikDao;
import com.example.widjet.main.database.PrazdnikDataBase;
import com.example.widjet.main.entity.PrazdnikEntity;
import com.example.widjet.main.tdo.PrazdnikDTO;

public class DescriptionActivity extends AppCompatActivity {
    private static final String TAG = "DescriptionActivity";
    public static final String ID = "id";

    public static PendingIntent getActivityIntent(Context context, long idPrasdnik) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        Log.i(TAG, "getActivityIntent: " + idPrasdnik);
        intent.putExtra(ID, idPrasdnik);
        return PendingIntent.getActivity(context, (int) idPrasdnik, intent, 0);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, -1);
        Log.i(TAG, "onCreate: id " + id + " " + intent.getAction());
        PrazdnikDTO prazdnik = getPrazdnik(id);
        Log.i(TAG, "onCreate: id " + id + " " + prazdnik.getImg());
        setTitle(prazdnik.getName());

        ImageView imageView = findViewById(R.id.image_description_activity);

        setupSizeImView(imageView);

        Glide.with(this).load(getBitmap(prazdnik.getImg())).fitCenter().into(imageView);
     //   imageView.setImageBitmap(getBitmap(prazdnik.getImg()));

        TextView name = findViewById(R.id.name_description_activity);
        name.setText(prazdnik.getName());

        TextView description = findViewById(R.id.description_description_activity);
        description.setText(prazdnik.getDescription());

        setResult(RESULT_CANCELED);
    }

    private void setupSizeImView(ImageView imageView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        double searchWidth = width - 200;
        double searchHeight = searchWidth / 700 * 555;

        Log.i(TAG, "setupSizeImView: " + width + " "+ searchHeight + " " + searchWidth);

        imageView.getLayoutParams().width = (int) searchWidth;
        imageView.getLayoutParams().height = (int) searchHeight;
        imageView.requestLayout();

    }

    private Bitmap getBitmap(String image) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("drawable/" + image,
                null,
                getPackageName()));
        return bitmap;
    }

    private Bitmap roundedBitmap(Context context, PrazdnikDTO prazdnik) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), context.getResources().getIdentifier("drawable/" + prazdnik.getImg(),
                null,
                context.getPackageName()));

        Bitmap imageRounded = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight())), 200, 200, mpaint); // Round Image Corner 100 100 100 100
        return imageRounded;
    }

    private PrazdnikDTO getPrazdnik(long id) {
        if (id == -1) {
            return defaultPrasdnik();
        }

        PrazdnikDataBase prazdnikDataBase = App.getInstance().getPrazdnikDataBase();
        PrazdnikDao prazdnikDao = prazdnikDataBase.prazdnikDao();

        return new PrazdnikDTO(prazdnikDao.getById(id));
    }

    private PrazdnikDTO defaultPrasdnik() {
        PrazdnikEntity prazdnikEntity = new PrazdnikEntity();
        prazdnikEntity.setName(getString(R.string.default_name_prasdnik));
        prazdnikEntity.setDescription(getString(R.string.fish_text));
        prazdnikEntity.setImg(getString(R.string.default_name_image));
        prazdnikEntity.setId(-1);
        return new PrazdnikDTO(prazdnikEntity);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: ");
        super.onStop();
        finish();
    }

/*    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }*/

    @Override
    protected void onDestroy() {
        // App.getInstance().getPrazdnikDataBase().close();
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
