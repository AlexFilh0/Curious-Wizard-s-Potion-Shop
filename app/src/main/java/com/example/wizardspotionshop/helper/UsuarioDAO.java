package com.example.wizardspotionshop.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class UsuarioDAO implements IUsuarioDAO {
    private Context appContext;
    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public UsuarioDAO(Context context) {
        DbHelper db = new DbHelper(context);
        appContext = context;
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();
    }

    @Override
    public boolean registrar(String usuario, String senha) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("usuario", usuario);
            cv.put("senha", senha);

            write.insert(DbHelper.TABELA_USUARIOS, null, cv);
        } catch (Exception e) {
            Toast.makeText(appContext, "Algo deu errado.", Toast.LENGTH_SHORT).show();
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean entrar(String usuario, String senha) {

        try {
            // Constroi a consulta sql para o usuário
            String sql = "SELECT usuario, senha " +
                    "FROM " + DbHelper.TABELA_USUARIOS + " " +
                    "WHERE usuario = '" + usuario + "' AND " +
                    "      senha = '" + senha + "';";

            // Executa a query
            Cursor c = read.rawQuery(sql, null);

            // Verifica se retornou dados
            while (c.moveToNext()) {
                String nome = c.getString(c.getColumnIndex("usuario"));

                // Insere usuario na tabela de usuário logado
                ContentValues cv = new ContentValues();
                cv.put("usuario", nome);

                write.insert(DbHelper.TABELA_USUARIO_LOG, null, cv);

                Toast.makeText(appContext, "Bem-vindo " + nome.toUpperCase() + "!", Toast.LENGTH_SHORT).show();
                return true;
            }

            // Mostra mensagem de sucesso
            Toast.makeText(appContext, "Usuário/Senha incorretos", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(appContext, "Algo deu errado", Toast.LENGTH_SHORT).show();
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
            return false;
        }

        return false;
    }

    @Override
    public boolean verificarLogado() {
        try {
            // Constroi a consulta sql para usuário logado
            String sql = "SELECT usuario " +
                          "FROM " + DbHelper.TABELA_USUARIO_LOG + ";";

            // Executa a query
            Cursor c = read.rawQuery(sql, null);

            // Verifica se retornou dados
            while (c.moveToNext()) {
                return true;
            }

            return false;
        } catch (Exception e) {
            Toast.makeText(appContext, "Algo deu errado", Toast.LENGTH_SHORT).show();
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String retornaUsuarioLog() {
        try {
            // Constroi a consulta sql para usuário logado
            String sql = "SELECT usuario " +
                    "FROM " + DbHelper.TABELA_USUARIO_LOG + ";";

            // Executa a query
            Cursor c = read.rawQuery(sql, null);

            // Verifica se retornou dados
            while (c.moveToNext()) {
                String nome = c.getString(c.getColumnIndex("usuario"));
                return nome;
            }

            return "Ninguém";
        } catch (Exception e) {
            Toast.makeText(appContext, "Algo deu errado", Toast.LENGTH_SHORT).show();
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean sair() {
        try {
            // Constroi a consulta sql para usuário logado
            String sql = "SELECT usuario " +
                         "FROM " + DbHelper.TABELA_USUARIO_LOG + ";";

            // Executa a query
            Cursor c = read.rawQuery(sql, null);

            // Inicializa a variável
            String nome = "";

            // Verifica se retornou dados
            while (c.moveToNext()) {
                nome = c.getString(c.getColumnIndex("usuario"));
            }

            write.delete(DbHelper.TABELA_USUARIO_LOG, "usuario=?", new String[]{nome});

        } catch (Exception e) {
            Toast.makeText(appContext, "Algo deu errado", Toast.LENGTH_SHORT).show();
            Log.i("INFO DB", "Ocorreram os seguintes problemas: " + e.getMessage());
            return false;
        }

        return false;
    }
}
