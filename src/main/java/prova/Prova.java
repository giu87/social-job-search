/*
 * @(#)Prova.java   1.0   06/giu/2012
 *
 * Copyright 2012-2012 Politecnico di Milano. All Rights Reserved.
 *
 * This software is the proprietary information of Politecnico di Milano.
 * Use is subject to license terms.
 *
 * @(#) $Id$
 */
package prova;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class Prova {

//   public static void main(String[] args){
//      
//      DBCollection fColl = Facade.db.getCollection(Facade.TABLE_FACEBOOK);
//      System.out.println(fColl.find(new BasicDBObject("from._id",new BasicDBObject("$in",fColl.distinct("_id",new BasicDBObject("rType", "FACEBOOK_PAGE")))).append("resourceLang", new BasicDBObject("$ne", "unsopported")).append("tagMeEntitiesIdList", new BasicDBObject("$ne",null))).size());
//   }
   
 
    public static void main(String[] args){
    
       HSSFWorkbook workbook = new HSSFWorkbook();
       HSSFSheet sheet = workbook.createSheet("Java Class Info");
       sheet.setColumnWidth(0,10000);
       HSSFRow row = sheet.createRow(0);
       
       HSSFFont font = workbook.createFont();
       font.setColor(HSSFFont.COLOR_RED);
       font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
       // Create the style
       HSSFCellStyle cellStyle= workbook.createCellStyle();
       cellStyle.setFont(font);
       
       HSSFCell cell = row.createCell(0);
       cell.setCellStyle(cellStyle);
       cell.setCellType(HSSFCell.CELL_TYPE_STRING);
       cell.setCellValue("Query");
       row.createCell(1);
       cell.setCellValue("Precision");
       
       row = sheet.createRow(1);
       cell = row.createCell(0);
       cell.setCellValue("How many cocks Matteo can blow?");
       cell = row.createCell(1);
       cell.setCellValue(2);
       
       FileOutputStream fOut;
      try {
         fOut = new FileOutputStream("C:\\Users\\Giuliano\\provaExcel.xls");
       // Write the Excel sheet
          workbook.write(fOut);
          fOut.flush();
       // Done deal. Close it.
          fOut.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
    }
}
