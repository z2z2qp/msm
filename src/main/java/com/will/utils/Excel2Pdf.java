package com.will.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * Created by zoumy on 2017/3/29 9:40.
 */
public class Excel2Pdf {

    public static void els2pdf(String els,String pdf){

        System.out.println("Starting excel...");
        long start = System.currentTimeMillis();
        ActiveXComponent app = new ActiveXComponent("Excel.Application");

        try {
            app.setProperty("Visible",false);
            Dispatch workbooks = app.getProperty("Workbooks").toDispatch();
            System.out.println("opening document:" + els);
            Dispatch workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method, new Object[]{els, new Variant(false),new Variant(false)}, new int[3]).toDispatch();
            Dispatch.invoke(workbook, "SaveAs", Dispatch.Method, new Object[] {
                    pdf, new Variant(57), new Variant(false),
                    new Variant(57), new Variant(57), new Variant(false),
                    new Variant(true), new Variant(57), new Variant(true),
                    new Variant(true), new Variant(true) }, new int[1]);
            Variant f = new Variant(false);
            System.out.println("to pdf " + pdf);
            Dispatch.call(workbook, "Close", f);
            long end = System.currentTimeMillis();
            System.out.println("completed..used:" + (end - start)/1000 + " s");
        } catch (Exception e) {
            System.out.println("========Error:Operation fail:" + e.getMessage());
        }finally {
            if (app != null){
                app.invoke("Quit", new Variant[] {});
            }
        }
    }
}
