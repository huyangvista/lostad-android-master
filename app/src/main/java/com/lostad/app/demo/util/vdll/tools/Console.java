package com.lostad.app.demo.util.vdll.tools;

import com.lostad.applib.util.ReflectUtil;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;


public class Console extends PrintStream {

    public Console(String fileName) throws FileNotFoundException {
        super(fileName);
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        StringBuilder sb = new StringBuilder("");
        List<Class<?>> packageClasses = ReflectUtil.getPackageClasses("com.lostad.app.demo.view");
        for (Class c : packageClasses) {
            Field[] fields = ReflectUtil.getFieldsLocal(ReflectUtil.getClassFromName(c.getName()));
            for (int j = 0; j < fields.length; j++) {
                Field f = fields[j];
                String n = f.getName();
                if (n != null) {
                    sb.append(n);
                }
            }
        }
        String text = sb.toString();
        System.out.printf(text);
    }

    public static void Write(Object ow) {
        System.out.print(ow);
    }

    public static void WriteLine(Object ow) {
        System.out.println(VWhoCall() + ow);
    }

    public static void WriteLine() {
        System.out.println(VWhoCall());
    }

    public static void Err(Object ow) {
        System.err.print(ow);
    }

    public static void ErrLine(Object ow) {
        System.err.println(VWhoCall() + ow);
    }

    public static void ErrLine() {
        System.err.println(VWhoCall());
    }

    public static String VWhoCall() {
        StackTraceElement vste = Thread.currentThread().getStackTrace()[3];
        String vs = vste.getClassName() + "." + vste.getMethodName() + "-->";
        return vs;
    }
}
