package com.example.widjet.main.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Migrations {
    private final Context context;

    public Migrations(Context context) {
        this.context = context;
    }

    public final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try (InputStream is = context.getAssets().open("update_2026_2035.sql");
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder statement = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;
                    statement.append(line).append(' ');
                    if (line.endsWith(";")) {
                        String sql = statement.toString().trim();
                        statement.setLength(0);
                        // PRAGMA/BEGIN/COMMIT нельзя выполнять внутри транзакции Room
                        if (sql.startsWith("PRAGMA") || sql.startsWith("BEGIN") || sql.startsWith("COMMIT")) continue;
                        database.execSQL(sql.substring(0, sql.length() - 1));
                    }
                }
            } catch (java.io.IOException e) {
                throw new RuntimeException("Не удалось применить миграцию базы праздников", e);
            }
        }
    };
}
