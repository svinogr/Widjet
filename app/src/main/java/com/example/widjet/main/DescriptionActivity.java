package com.example.widjet.main;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.widjet.R;
import com.example.widjet.main.database.App;
import com.example.widjet.main.database.dao.PrazdnikDao;
import com.example.widjet.main.database.database.PrazdnikDataBase;
import com.example.widjet.main.database.entity.PrazdnikEntity;
import com.example.widjet.main.database.tdo.PrazdnikDTO;

public class DescriptionActivity extends AppCompatActivity {
    private static final String TAG = "DescriptionActivity";
    public static final String ID = "id";

    public static PendingIntent getActivityIntent(Context context, long idPrasdnik) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        Log.i(TAG, "getActivityIntent: " +idPrasdnik);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
        intent.putExtra(ID, idPrasdnik);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) idPrasdnik, intent, 0);
        return pendingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ID, -1);
        Log.i(TAG, "onCreate: id " + id + " " + intent.getAction());
        PrazdnikDTO prazdnik  = getPrazdnik(id);

        ImageView imageView = findViewById(R.id.image_description_activity);
        imageView.setImageResource(getResources().getIdentifier("drawable/" + prazdnik.getImg(), null, getPackageName()));

        TextView name = findViewById(R.id.name_description_activity);
        name.setText(prazdnik.getName());

        TextView description = findViewById(R.id.description_description_activity);
        description.setText(prazdnik.getDescription());

        setResult(RESULT_CANCELED);
    }

    private PrazdnikDTO getPrazdnik(long id) {
        if(id == -1 ) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    @Override
    protected void onDestroy() {
       // App.getInstance().getPrazdnikDataBase().close();
        Log.i(TAG, "onDestroy: ");
        super.onDestroy();
    }
}