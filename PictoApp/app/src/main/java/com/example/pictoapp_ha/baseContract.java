package com.example.pictoapp_ha;

import android.provider.BaseColumns;

public class baseContract
{//Esta clase tendra toda la informacion de nombre de las tablas y sus atributos
    public static abstract class UsuarioEntry implements BaseColumns
    {//Esta clase contrendra la info de la tabla Usuarios

        //Nombre de la tabla
        public static final String NOMBRE_TUSUARIO="usuarios";

        //Nombre de los atributos
        public static final String NOMBRE="nombre";
        public static final String CLAVE="clave";
        public static final String TIPO="tipo";
    }

    public static abstract class PictogramasEntry implements BaseColumns
    {//Esta clase contrendra la info de la tabla Pictograma

        //Nombre de la tabla
        public static final String NOMBRE_TPICTOGRAMAS="pictogramas";

        //Nombre de los atributos
        public static final String NOMBRE="nombre";
        public static final String ID_CATEGORIA="id_categoria";
        public static final String ID_CAT_SUBCATEGORIA="id_cat_subcategoria";
        public static final String NOMBRE_SUB="nombre_sub";
        public static final String IMAGEN="imagen";
    }
    public static abstract class CategoriasEntry implements BaseColumns
    {
        //Nombre de la tabla
        public static final String NOMBRE_TCATEGORIAS="categorias";

        //Nombre de los atributos
        public static final String ID_CATEGORIA="id_categoria";
        public static final String NOMBRE="nombre";
    }
    public static abstract class SubCategoriasEntry implements BaseColumns
    {
        //Nombre de la tabla
        public static final String NOMBRE_TSUBCATEGORIAS="subcategorias";

        //Nombre de los atributos
        public static final String ID_CATEGORIA="id_categoria";
        //public static final String ID_SUBCATEGORIA="id_subcategoria";
        public static final String NOMBRE="nombre";
    }

}
