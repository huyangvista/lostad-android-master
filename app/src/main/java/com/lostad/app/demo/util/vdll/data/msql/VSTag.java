package com.lostad.app.demo.util.vdll.data.msql;

public class VSTag
{
	//标记
	public final static  String  _PK        = "PRIMARY KEY"       ;
	public final static  String  _AI        = "AUTO_INCREMENT"       ;

	
	
	//字段
	public final static  String  _tinyint            = "tinyint"           ;
	public final static  String  _smallint           = "smallint"          ;
	public final static  String  _mediumint          = "mediumint"         ;
	public final static  String  _int                = "int"               ;
	public final static  String  _integer            = "integer"           ;
	public final static  String  _bigint             = "bigint"            ;
	public final static  String  _bit                = "bit"               ;
	public final static  String  _real               = "real"              ;
	public final static  String  _double             = "double"            ;
	public final static  String  _float              = "float"             ;
	public final static  String  _decimal            = "decimal"           ;
	public final static  String  _numeric            = "numeric"           ;
	public final static  String  _char          	 = "char"              ;
	public final static  String  _varchar            = "varchar"           ;
	public final static  String  _date               = "date"              ;
	public final static  String  _time               = "time"              ;
	public final static  String  _year               = "year"              ;
	public final static  String  _timestamp          = "timestamp"         ;
	public final static  String  _datetime           = "datetime"          ;
	public final static  String  _tinyblob           = "tinyblob"          ;
	public final static  String  _blob               = "blob"              ;
	public final static  String  _mediumblob         = "mediumblob"        ;
	public final static  String  _longblob           = "longblob"          ;	
	public final static  String  _tinytext           = "tinytext"          ;
	public final static  String  _text               = "text"              ;
	public final static  String  _mediumtext         = "mediumtext"        ;
	public final static  String  _longtext           = "longtext"          ;	
	public final static  String  _enum               = "enum"              ;
	public final static  String  _set                = "set"               ;
	public final static  String  _binary             = "binary"            ;
	public final static  String  _varbinary          = "varbinary"         ;
	public final static  String  _point              = "point"             ;
	public final static  String  _linestring         = "linestring"        ;
	public final static  String  _polygon            = "polygon"           ;
	public final static  String  _geometry           = "geometry"          ;
	public final static  String  _multipoint         = "multipoint"        ;
	public final static  String  _multilinestring    = "multilinestring"   ;
	public final static  String  _multipolygon       = "multipolygon"      ;
	public final static  String  _geometrycollection = "geometrycollection"; 
	
	
	public static String vCount(final String vin,final int count) 
	{
		//return String.format("%s(%s)", vin, count);
		final String vString = vin + "(" + count + ")";
		return vString;
	}
}

enum Kind {
    /**
     * Source files written in the Java programming language.  For
     * example, regular files ending with {@code .java}.
     */
    SOURCE(".java"),

    /**
     * Class files for the Java Virtual Machine.  For example,
     * regular files ending with {@code .class}.
     */
    CLASS(".class"),

    /**
     * HTML files.  For example, regular files ending with {@code
     * .html}.
     */
    HTML(".html"),

    /**
     * Any other kind.
     */
    OTHER("");
    /**
     * The extension which (by convention) is normally used for
     * this kind of file object.  If no convention exists, the
     * empty string ({@code ""}) is used.
     */
    public final String extension;
    private Kind(String extension) {
        extension.getClass(); // null check
        this.extension = extension;
    }
};
