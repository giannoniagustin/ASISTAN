package baseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




/**
 * Created by Agustín on 26/08/2015.
 */
public class BdHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="aiptu.sql";
    private static final int DB_SCHEME_VERSION=1;
    private SQLiteDatabase bd;
    public BdHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, DB_NAME, null, DB_SCHEME_VERSION);
 }

    public BdHelper(Context context, String bd_name) {
        super(context,bd_name,null,DB_SCHEME_VERSION);
  }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseMananger.CREATE_TABLE);
 }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void InsertTabla(String tabla , ContentValues registro)
    {
            bd=this.getWritableDatabase();
            bd.insert(tabla,null, registro);

}
    public Cursor ConsultaTabla(String consulta){
        OpenBd();
        Cursor fila = bd.rawQuery(consulta, null);
        return fila;
    }
    public Cursor ConsultaTablaa(String tabla, String where) {
        try {

            String s = "SELECT * FROM " + tabla +
                    " WHERE " + where;
            OpenBd();

            Cursor filas = bd.rawQuery(s, null);

            return filas;
        } catch (Exception e){
            e.toString();
            return  null;}
    }

    public void UpdateTabla(String tabla, ContentValues registro , String where){
        OpenBd();
        bd.update(tabla, registro, where, null);
        bd.close();
    }
    public void ConsultaTabla(String tabla, String where){
        OpenBd();
        bd.close();
    }

    public void OpenBd(){ bd=this.getWritableDatabase();}
    public void CloseBd(){ bd.close();}


    public Cursor ConsultaTablaArchNoEnviados() {
        try {

            String s = "SELECT * FROM " + DataBaseMananger.TABLE_NAME +
                    " WHERE " + DataBaseMananger.CN_ENVIADO + "=0";
            OpenBd();

            Cursor filas = bd.rawQuery(s, null);

        //    bd.close();

            return filas;
        } catch (Exception e){
            archivos.Log.LOGGER.severe(e.toString());
            return  null;}
    }



  public int GetIntColumna(Cursor resultado, String Columna){

      return resultado.getInt(resultado.getColumnIndex(Columna));
  }
    public String GetStringColumna(Cursor resultado, String Columna){

        return resultado.getString(resultado.getColumnIndex(Columna));
    }

    public void IncrementarIntentos(String nombreArchivo){//Incremente en 1 la cantidad de intentos de envios en la BD

        ContentValues data = new ContentValues();
        Cursor resultado = this.ConsultaTabla("SELECT * FROM " + DataBaseMananger.TABLE_NAME +
                " WHERE " + DataBaseMananger.CN_NAME + " ='" + nombreArchivo + "'");
        if (resultado.moveToNext()) {
            int intento =this.GetIntColumna(resultado,DataBaseMananger.CN_INTENTO);
            intento += 1;
            data.put(DataBaseMananger.CN_INTENTO, intento);
            this.UpdateTabla(DataBaseMananger.TABLE_NAME, data, DataBaseMananger.CN_NAME + "= '" + nombreArchivo + "'");
        }





    }




}
