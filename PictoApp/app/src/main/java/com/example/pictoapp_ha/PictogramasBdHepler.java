package com.example.pictoapp_ha;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class PictogramasBdHepler extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_NAME="pictogramas";

    //Para crear la base de datos o mas bien conectarse a ella
    public PictogramasBdHepler(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        //Semmanda el nombre de la base de datos y la version
    }

    //Este metodo se realiza solo una vez, cuando la BD no existe, y aqui se
    //pone todas las creaciones de las tablas
    //y se insertan los datos que se quiere que tenga la BD por Default
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //CREACION DE TABLAS
        //el sig procedimiento permite enviar un scrip para creacion de tablas
        //un scrip que no devuelva valores
        /*
         * CABE DESTACAR QUE SE PONEN LOS NOMBRES DE LAS TABLAS Y LOS ATRIBUTOS
         * HACIENDO REFERENCIA A LA CLASE baseContract DONDE ESTAN GUARDADOS
         * TODOS ESTOS NOMBRES, ESO ES PARA QUE EN CASO DE MODIFICACIONES NO
         * SE TENGA QUE ESTAR MODIFICANDO TABLA POR TABLA, METODO POR METODO,
         * SI NO SOLO DESDE LA CLASE baseContract
         * */
            //CREACION DE LA TABLA CATEGORIAS
                sqLiteDatabase.execSQL("CREATE TABLE "+baseContract.CategoriasEntry.NOMBRE_TCATEGORIAS+" ( "+
                        baseContract.CategoriasEntry.ID_CATEGORIA+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                        baseContract.CategoriasEntry.NOMBRE+" VARCHAR(20)NOT NULL);");
            //CREACION DE LA TABLA SUBCATEGORIAS
                sqLiteDatabase.execSQL("CREATE TABLE "+baseContract.SubCategoriasEntry.NOMBRE_TSUBCATEGORIAS+" ( "+
                        baseContract.SubCategoriasEntry.ID_CATEGORIA+" INTEGER NOT NULL, "+
                        //baseContract.SubCategoriasEntry.ID_SUBCATEGORIA+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                        baseContract.SubCategoriasEntry.NOMBRE+" VARCHAR(20) NOT NULL, "+
                        "CONSTRAINT fk_id_cat_sub FOREIGN KEY ("+baseContract.SubCategoriasEntry.ID_CATEGORIA+")" +
                            " REFERENCES "+baseContract.CategoriasEntry.NOMBRE_TCATEGORIAS+"("+baseContract.CategoriasEntry.ID_CATEGORIA+"), "+
                        "PRIMARY KEY ("+baseContract.SubCategoriasEntry.ID_CATEGORIA+","+baseContract.SubCategoriasEntry.NOMBRE+"));");//Creacion
                                                                                                //llave primaria
            //CREACION DE LA TABLA PICTOGRAMAS
                sqLiteDatabase.execSQL("CREATE TABLE "+baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS+" ( "+
                        baseContract.PictogramasEntry.NOMBRE+" VARCHAR(20) PRIMARY KEY NOT NULL, "+
                        baseContract.PictogramasEntry.ID_CATEGORIA+" INTEGER NOT NULL, "+
                        baseContract.PictogramasEntry.ID_CAT_SUBCATEGORIA+" INTEGER, "+
                        baseContract.PictogramasEntry.NOMBRE_SUB+" VARCHAR(20), "+
                        baseContract.PictogramasEntry.IMAGEN+" BLOB NOT NULL," +//Tipo de dato de imagen BLOB
                        "CONSTRAINT fk_id_cat_pic FOREIGN KEY ("+baseContract.PictogramasEntry.ID_CATEGORIA+") " +
                            "REFERENCES "+baseContract.CategoriasEntry.NOMBRE_TCATEGORIAS+"("+baseContract.CategoriasEntry.ID_CATEGORIA+")," +
                        "CONSTRAINT fk_id_cat_sub_pic FOREIGN KEY ("+baseContract.PictogramasEntry.ID_CAT_SUBCATEGORIA+", "+baseContract.PictogramasEntry.NOMBRE_SUB+")" +
                            "REFERENCES "+baseContract.SubCategoriasEntry.NOMBRE_TSUBCATEGORIAS+"("+baseContract.SubCategoriasEntry.ID_CATEGORIA+","+baseContract.SubCategoriasEntry.NOMBRE+"));");
            //Creacion de la tabla Usuarios:
                sqLiteDatabase.execSQL("CREATE TABLE "+baseContract.UsuarioEntry.NOMBRE_TUSUARIO+
                    " ( "+baseContract.UsuarioEntry.NOMBRE+" VARCHAR(20) PRIMARY KEY NOT NULL, "+
                    baseContract.UsuarioEntry.CLAVE+" VARCHAR(8), "+
                    baseContract.UsuarioEntry.TIPO+" VARCHAR(10));");
        //INSERCIONES

            //INSERCION DE LOS USUARIOS QUE EXISTIRAN, LOS PONEMOS AQUI PORQUE POR DEFAULT
            //A LA HORA DE HACER LA BASE DE DATOS QUEREMOS QUE ESTOS YA EXISTAN

            //INSERCION DE DATOS

                //USUARIOS
                    //SE CREA UN CONTENTVALUES CON LOS DATOS, PARA QUE ESTE SE ENVIE Y SE GUARDE EN LA TABLA
                        ContentValues temp=new ContentValues();

                    //INSERTAMOS LOS VALORES EN TEMP (cabe destacar que se inserta atributo por atributo
                    //despues de poner el nombre del atributo)

                        temp.put(baseContract.UsuarioEntry.NOMBRE,"Usuario General");
                        temp.put(baseContract.UsuarioEntry.TIPO,"General");

                    //INSERCION EN LA BD
                        sqLiteDatabase.insert(baseContract.UsuarioEntry.NOMBRE_TUSUARIO, null,temp );

                    //Segunda insercion (usuario admin)
                        temp=null; //Borramos datos de temp
                        temp=new ContentValues();
                        temp.put(baseContract.UsuarioEntry.NOMBRE,"Administrador");
                        temp.put(baseContract.UsuarioEntry.CLAVE,"123");
                        temp.put(baseContract.UsuarioEntry.TIPO,"Admin");
                    //INSERCION EN LA BD
                        sqLiteDatabase.insert(baseContract.UsuarioEntry.NOMBRE_TUSUARIO, null,temp );



    }

    //Metodo para consultar la contraseña, no necesita parametros ya que solo
    //hay un registro que cuenta con contraseña
    public String consultaContra()
    {
        //Se crea un cursor donde se guardara la informacion recibida
        Cursor temp= getReadableDatabase().rawQuery("SELECT "+baseContract.UsuarioEntry.CLAVE+
                                                        " FROM "+baseContract.UsuarioEntry.NOMBRE_TUSUARIO+
                                                        " WHERE "+baseContract.UsuarioEntry.TIPO+" = Admin",null);
        return temp.getString(0);//Se obtiene el String que se encuentra en la posicion 0 del cursor
                                            //Como solo es un dato la clave debe de estar en la posicion 0
    }

    public void updateContra(String newClave)
    {
        ContentValues temp=new ContentValues();
        temp.put(baseContract.UsuarioEntry.CLAVE, newClave);//Poner en clave-valor la nueva contraseña recibida
        //Realizar el Update
        getWritableDatabase().update(baseContract.UsuarioEntry.NOMBRE_TUSUARIO,//Nombre de tabla
                                temp,//Valor al que se actualizara
                                baseContract.UsuarioEntry.TIPO+"=Admin",null); //Condicion
    }
    public String consultaNombre()
    {
        Cursor temp= getReadableDatabase().rawQuery("SELECT "+ baseContract.UsuarioEntry.NOMBRE+" "+
                                                        "FROM "+baseContract.UsuarioEntry.NOMBRE_TUSUARIO+" "+
                                                        "WHERE "+baseContract.UsuarioEntry.TIPO+"=General",null);
        return temp.getString(0);
    }
    public void updateNombre(String newNombre)
    {
        ContentValues temp= new ContentValues();
        temp.put(baseContract.UsuarioEntry.NOMBRE, newNombre);
        //Realizar el Update
        getWritableDatabase().update(baseContract.UsuarioEntry.NOMBRE_TUSUARIO,//nombre de la tabla
                                        temp,//Valor que actualizara
                                        baseContract.UsuarioEntry.TIPO+"=General",//Sentencia Where
                                        null);
    }
    //Consulta pictograma por nombre
    public Cursor consultaPictoNombre(String nombre)
    {
        Cursor temp=getReadableDatabase().rawQuery("SELECT "+baseContract.PictogramasEntry.IMAGEN+
                        "FROM "+baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS+
                        "WHERE "+baseContract.PictogramasEntry.NOMBRE+"="+nombre,
                null);

        if(!temp.moveToFirst())//Si el cursor esta vacio, se cierra, si no se envia
        {
            temp.close();
        }
        return temp;
    }
    //Consulta pictograma por Categoria
    public Cursor consultaPicto(String categia)
    {
        Cursor temp=getReadableDatabase().rawQuery("SELECT "+baseContract.PictogramasEntry.IMAGEN+
                        "FROM "+baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS+
                        "WHERE "+baseContract.PictogramasEntry.ID_CATEGORIA+"="+categia,
                null);

        if(!temp.moveToFirst())//Si el cursor esta vacio, se cierra, si no se envia
        {
            temp.close();
        }
        return temp;
    }
    //Consulta pictograma por SubCategoria
    public Cursor consultaPictoSubCategoria(String subCategia)
    {
        Cursor temp=getReadableDatabase().rawQuery("SELECT "+baseContract.PictogramasEntry.IMAGEN+
                        "FROM "+baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS+
                        "WHERE "+baseContract.PictogramasEntry.NOMBRE_SUB+"="+subCategia,
                null);

        if(!temp.moveToFirst())//Si el cursor esta vacio, se cierra, si no se envia
        {
            temp.close();
        }
        return temp;
    }
    public void borrarPicto(String nombre)
    {
        getWritableDatabase().delete(baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS,
                                    baseContract.PictogramasEntry.NOMBRE+"="+nombre,
                                    null);
    }
    //Metodo para insertar un pictograma, tomar en cuenta que se recibiran todos los parametros
    public long insertaPicto(String Nombre, String Categoria, String Subcategoria, Bitmap Imagen)
    {
        // Convertir la imagen en un byte Array
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        Imagen.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] imagenByte= stream.toByteArray();

        //Crear un content values para guardar ahi los datos:
        ContentValues temp=new ContentValues();
        temp.put(baseContract.PictogramasEntry.NOMBRE, Nombre);
        temp.put(baseContract.PictogramasEntry.ID_CATEGORIA, Categoria);
        temp.put(baseContract.PictogramasEntry.ID_CAT_SUBCATEGORIA, Categoria);
        temp.put(baseContract.PictogramasEntry.NOMBRE_SUB, Subcategoria);
        temp.put(baseContract.PictogramasEntry.IMAGEN, imagenByte);
        return getWritableDatabase().insert(baseContract.PictogramasEntry.NOMBRE_TPICTOGRAMAS, null,temp);
    }


    //Si se quiere actualizar la base de datos, en ese caso aqui se especifica
    //lo que se realizara, como eliminar tablas, agreagar tablas nuevas, etc,
    //para este caso no es necesario
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //
}
