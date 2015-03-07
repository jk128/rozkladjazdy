package com.tomasz.rozkladjazdy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.tomasz.model.FavoriteTimeTable;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper{
    
//The Android's default system path of your application database.
private static String DB_PATH = "/data/data/com.example.rozkladjazdy/databases/";
 
private static String DB_NAME = "rozkladAndroid.db3";
 
private SQLiteDatabase myDataBase;
 
private final Context myContext;
 
/**
  * Constructor
  * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
  * @param context
  */
public DataBaseHelper(Context context) {
 
super(context, DB_NAME, null, 1);
this.myContext = context;
}	


/**
  * Creates a empty database on the system and rewrites it with your own database.
  * */
public void createDataBase() throws IOException{
 
boolean dbExist = checkDataBase();
 
if(dbExist){
//do nothing - database already exist
}else{
 
//By calling this method and empty database will be created into the default system path
//of your application so we are gonna be able to overwrite that database with our database.
this.getReadableDatabase();
 
try {
 
copyDataBase();
 
} catch (IOException e) {
 
throw new Error("Error copying database");
 
}
}
 
}
 
/**
  * Check if the database already exist to avoid re-copying the file each time you open the application.
  * @return true if it exists, false if it doesn't
  */
private boolean checkDataBase(){
 
SQLiteDatabase checkDB = null;
 
try{
String myPath = DB_PATH + DB_NAME;
checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
}catch(SQLiteException e){
 
//database does't exist yet.
 
}
 
if(checkDB != null){
 
checkDB.close();
 
}
 
return checkDB != null ? true : false;
}
 
/**
  * Copies your database from your local assets-folder to the just created empty database in the
  * system folder, from where it can be accessed and handled.
  * This is done by transfering bytestream.
  * */
private void copyDataBase() throws IOException{
 
//Open your local db as the input stream
InputStream myInput = myContext.getAssets().open(DB_NAME);
 
// Path to the just created empty db
String outFileName = DB_PATH + DB_NAME;
 
//Open the empty db as the output stream
OutputStream myOutput = new FileOutputStream(outFileName);
 
//transfer bytes from the inputfile to the outputfile
byte[] buffer = new byte[1024];
int length;
while ((length = myInput.read(buffer))>0){
myOutput.write(buffer, 0, length);
}
 
//Close the streams
myOutput.flush();
myOutput.close();
myInput.close();
 
}
 
public void openDataBase() throws SQLException{
 
//Open the database

myDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);
 
}
 
@Override
public synchronized void close() {
 
if(myDataBase != null)
myDataBase.close();
 
super.close();
 
}
 
@Override
public void onCreate(SQLiteDatabase db) {
 
}
 
@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
}
public Cursor fetchAllBusStops() {
	 
	  Cursor mCursor = myDataBase.rawQuery("SELECT _id, nazwa, DL_GEOGR, SZER_GEOGR from przystanki;", null);
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 }
public void createFavoriteTable()throws SQLException {
	 //myDataBase.execSQL("DROP TABLE ULUBIONE_PRZYST");
	  myDataBase.execSQL("CREATE TABLE IF NOT EXISTS ULUBIONE_ROZKL(_id INTEGER PRIMARY KEY AUTOINCREMENT, busStopId CHAR(3) NOT NULL, lineId CHAR(3) NOT NULL, NAZWA CHAR(255) NOT NULL, LINIA CHAR(255) NOT NULL);");
	 }
public void insertFavoriteTimeTable(String busStopId, String lineId, String nazwa, String linia  )throws SQLException {
	ContentValues Val = new ContentValues();
	  Val.put("busStopId", busStopId);
	  Val.put("lineId", lineId);
	  Val.put("NAZWA", nazwa);
	  Val.put("LINIA", linia);
	  myDataBase.insert("ULUBIONE_ROZKL", null, Val);
	 }
public Cursor fetchAllFavoriteTimeTables() {
	 
	  Cursor mCursor = myDataBase.rawQuery("SELECT _ID as _id, busStopId, lineId, nazwa ||' LINIA: ' || LINIA AS NAZWA from ULUBIONE_ROZKL;", null);
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 }
public void DeleteFavoriteTimeTable(int id) throws SQLException {
	 	myDataBase.delete("ULUBIONE_ROZKL","_id="+id, null);
	
	 }

public Cursor fetchBuStopsByName(String inputText) throws SQLException {
	  Cursor mCursor = null;
	  if (inputText == null  ||  inputText.length () == 0)  {
	   mCursor = myDataBase.rawQuery("SELECT  _id, nazwa from przystanki;", null);
	  }
	  else {
	   mCursor = myDataBase.rawQuery("SELECT  _id, nazwa from przystanki where nazwa like '%"+inputText+"%';", null);
	  }
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
	
public Cursor fetchBusStopByLine(String idLine)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select p._id, p.nazwa from przystanki p, trasa_przystanek tp where tp.trasa_id="+idLine+" and tp.przystanek_id= p._id;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
/////////////////////////////////////////
public Cursor fetchAllLines() {
	 
	  Cursor mCursor = myDataBase.rawQuery("SELECT _id, nazwa ||' ' || wariant as 'NAZWA' from trasy;", null);
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 }
//******************

public List<FavoriteTimeTable> getAllFavoriteTimeTables() {
  List<FavoriteTimeTable> comments = new ArrayList<FavoriteTimeTable>();

 // Cursor cursor = myDataBase.query("ULUBIONE_ROZKL",allColumns,null, null, null, null, null);
  Cursor cursor = myDataBase.rawQuery("SELECT _ID as _id, busStopId, lineId, nazwa ||' LINIA: ' || LINIA AS NAZWA from ULUBIONE_ROZKL;", null);
   

  cursor.moveToFirst();
  while (!cursor.isAfterLast()) {
	 FavoriteTimeTable comment = cursorToFavoriteTimeTable(cursor);
    comments.add(comment);
    cursor.moveToNext();
  }
  // make sure to close the cursor
  cursor.close();
  return comments;
}
private FavoriteTimeTable cursorToFavoriteTimeTable(Cursor cursor) {
	FavoriteTimeTable favoriteTimeTable = new FavoriteTimeTable();
	favoriteTimeTable.setId(cursor.getInt(0));
	favoriteTimeTable.setBusStopId(cursor.getString(1));
	favoriteTimeTable.setLineId(cursor.getString(2));
	favoriteTimeTable.setNazwa(cursor.getString(3));
    return favoriteTimeTable;
  }
//****************
public Cursor fetchLinesByName(String inputText) throws SQLException {
	  Cursor mCursor = null;
	  if (inputText == null  ||  inputText.length () == 0)  {
	   mCursor = myDataBase.rawQuery("SELECT  _id,  nazwa ||' ' || wariant as 'NAZWA' from trasy;", null);
	  }
	  else {
	   mCursor = myDataBase.rawQuery("SELECT  _id,  nazwa ||' ' || wariant as 'NAZWA' from trasy where nazwa like '%"+inputText+"%';", null);
	  }
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }

public Cursor fetchLineVariant(String variant) {
	 
	  Cursor mCursor = myDataBase.rawQuery("SELECT _id, nazwa ||' ' || wariant as 'NAZWA' from trasy where nazwa='"+variant+"';", null);
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 }
////////////////////////////////////////
public Cursor fetchLinesByBusStop(String idBusStop)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select t._id, t.nazwa ||' ' || t.wariant AS 'linia' from trasy t, trasa_przystanek tp where tp.przystanek_id="+idBusStop+" and t._id=tp.trasa_id;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public Cursor fetchLinesByBus(String idBusStop)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select t._id, t.nazwa from trasy t, trasa_przystanek tp where tp.przystanek_id="+idBusStop+" and t._id=tp.trasa_id;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public String fetchLinesNumberByBusStop(String idBusStop)throws SQLException {
	  Cursor cursor = null;
	  String s ="linie:";
	  String poprz = null;
	  cursor = myDataBase.rawQuery("select t._id, t.nazwa from trasy t, trasa_przystanek tp where tp.przystanek_id="+idBusStop+" and t._id=tp.trasa_id;", null);
	 
	   cursor.moveToFirst();
	   StringBuffer sB = new StringBuffer(s);
	   int i=0;
	   while (!cursor.isAfterLast()) {
		  if(i==0)
		  {
			  poprz=cursor.getString(1);
		   sB.append(cursor.getString(1));
		   sB.append(" ");
		  }
		  else if(!poprz.equals(cursor.getString(1)))
		  {
			  poprz=cursor.getString(1);
			   sB.append(cursor.getString(1));
			   sB.append(" ");
		  }
		   i++;
	     cursor.moveToNext();
	   }
	   
	   s=sB.toString();
	 
	   return s;
	 }
public Cursor fetchTimeTableWeekDay(String busId, String lineId)throws SQLException {
	  Cursor mCursor = null;
	  Cursor id =myDataBase.rawQuery("select _id from trasa_przystanek where przystanek_id="+busId+" and trasa_id="+lineId+";", null);
	  id.moveToFirst();
	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu  where trasa_przystanek_id="+id.getString(0)+" and dni_robocze;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public Cursor fetchTimeTableSaturday(String busId, String lineId)throws SQLException {
	  Cursor mCursor = null;

	  Cursor id =myDataBase.rawQuery("select _id from trasa_przystanek where przystanek_id="+busId+" and trasa_id="+lineId+";", null);
	  id.moveToFirst();
	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu  where trasa_przystanek_id="+id.getString(0)+" and sobota;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public Cursor fetchTimeTableSunday(String busId, String lineId)throws SQLException {
	  Cursor mCursor = null;
	  Cursor id =myDataBase.rawQuery("select _id from trasa_przystanek where przystanek_id="+busId+" and trasa_id="+lineId+";", null);
	  id.moveToFirst();
	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu  where trasa_przystanek_id="+id.getString(0)+" and niedziela;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public String[] getDescription(String lineId) throws SQLException {
	  Cursor mCursor = null;
	  String[] description = new String[2];
	   mCursor = myDataBase.rawQuery("select legenda, nazwa ||' ' || wariant from trasy where _id="+lineId+";",null);
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	   description[0]=mCursor.getString(0);
	   description[1]=mCursor.getString(1);
	  }
	  return description;
	 
	 }
//dodane**************************************************************************** 26.11
public Cursor fetchRouteBusStopId(String busId, String lineId)throws SQLException {
	  Cursor mCursor = null;
	 
	   mCursor = myDataBase.rawQuery("select _id, kol_na_trasie from trasa_przystanek where przystanek_id="+busId+" and trasa_id="+lineId+";", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	   
	  }
	  return mCursor;
	 
	 }
//dodane**************************************************************************** 26.11
public Cursor fetchTimeTableWeekDayNew(String lineBusStopId)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu where trasa_przystanek_id="+lineBusStopId+" and dni_robocze;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public Cursor fetchTimeTableSaturdayNew(String lineBusStopId)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu where trasa_przystanek_id="+lineBusStopId+" and sobota;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
public Cursor fetchTimeTableSundayNew(String lineBusStopId)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select czas from godz_odjazdu where trasa_przystanek_id="+lineBusStopId+" and niedziela;", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
//dodane**************************************************************************** 26.11
public Cursor fetchSpecificBusStops(int orderSource, int orderDestination, String trasaId)throws SQLException {
	  Cursor mCursor = null;

	   mCursor = myDataBase.rawQuery("select _id from trasa_przystanek where trasa_id="+trasaId+" and KOL_NA_TRASIE BETWEEN " + orderSource+" AND "+orderDestination+";", null);
	  
	  if (mCursor != null) {
	   mCursor.moveToFirst();
	  }
	  return mCursor;
	 
	 }
}