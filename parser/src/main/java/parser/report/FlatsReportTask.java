package parser.report;

import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.concurrent.Task;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;

import parser.abstractparsers.flatparser.entities.Flat;
import parser.config.entities.TaskConfig;
import parser.interfaces.ObjectsRepository;
import parser.utils.RegParser;

public class FlatsReportTask extends Task<Void> {

    private Workbook book=null;
    private Sheet sheet=null;



    
    private ObjectsRepository<Flat> repo=null;
    private TaskConfig config;
    private int currentRowIndex=1;

    private  final String [] headers={
        "Тип квартиры",
        "Город",
        "Текст объявления",
        "Тип Н.П.",
        "Населенный пункт",
        "Район",
        "Тип улицы",
        "Улица",
        "Номер дома",
        "Площадь",
        "Цена",
        "Тип ремонта",
        "Этаж",
        "Этажность",
        "Количество комнат",
        "Дата объявления",
        "Источник",
        "Номер источника",
        "Агенство",
        "Ссылка",
        "Полный адрес",

        
           
};



    public FlatsReportTask( ObjectsRepository<Flat> repo,TaskConfig config){
        this.repo =repo;
        this.config=config;
        book=new HSSFWorkbook();
        sheet=book.createSheet("Квартиры");
       
    }


    public Void call(){
        try{
            save();
        }  catch (FileNotFoundException e){
            e.printStackTrace();
             System.out.println("Error saving XLS");
           } 
           catch(IOException e){
               e.printStackTrace();
              System.out.println("Error saving XLS");
           }

        return null;

    }

    private String getString(Float value){
        try{
            return value.toString();
        } catch(Exception e){
            return null;
        }

    }

    private String getString(Integer value){
        try{
            return value.toString();
        } catch(Exception e){
            return null;
        }

    }

    private ArrayList<String> getExcelRecord(Flat flat){
        
        ArrayList<String> values =new ArrayList<>();

        values.add(flat.type.toString());
        values.add(config.cityName);
        values.add(flat.bulletinText);
        values.add(flat.address.settlementType);
        values.add(flat.address.settlementName);
        values.add(flat.townArea);
        values.add(flat.address.streetType);
        values.add(flat.address.streetName);
        values.add(flat.address.buildingNumber);
        values.add(getString(flat.square));
        values.add(flat.price);
        values.add(flat.repairType);
        values.add(getString(flat.floorNumber));
        values.add(getString(flat.numberOfFloors));
        
        values.add(getString(flat.roomsAmount));
        values.add(flat.dateOfPlacing);
        values.add(flat.infoSource);
        values.add(flat.numberOfSource);
        values.add(flat.agency);
        values.add(flat.urlLink);
        values.add(flat.address.streetName+", "+flat.address.buildingNumber);

          


        return values;
    }


    private void addHeader(){

        Row row=sheet.createRow(0);
        Cell cell=null;

        int index=0;
        
        for (String header:headers){
            cell=row.createCell(index++);
            cell.setCellValue(header);
        }


    }

    private String getPdfFileName(String urlLink){
         
        RegParser reg=new RegParser();

        reg.parseString("/([^/]+)$",urlLink);
        return reg.getGroup(1);



  }


    private void addRecord(Flat flat){
        
        Row row=sheet.createRow(currentRowIndex++);
        Cell cell=null;
       

        
        int index=0;
        for (String currentValue:getExcelRecord(flat)){
            cell=row.createCell(index++);
            try{
                
                cell.setCellValue(currentValue);
            } catch(Exception e){

            }    
        }

        cell=row.createCell(index);   
        CreationHelper creationHelper=book.getCreationHelper();
        Hyperlink fileLink=creationHelper.createHyperlink(HyperlinkType.FILE);
        
        String pdfFileName=getPdfFileName(flat.urlLink);
        System.out.println(pdfFileName);
        fileLink.setAddress(".\\screenshots\\"+pdfFileName+".pdf");

        try{
            cell.setCellValue(pdfFileName);
            cell.setHyperlink(fileLink);
        } catch(Exception e){

        }


    }

    public void save() throws FileNotFoundException, IOException{
        
        long numberOfObjects=repo.getObjectsAmount();

        addHeader();
        
        updateProgress(0,numberOfObjects);
        for (Flat flat:repo.getAllObjects()){
            try{
                addRecord(flat);
            } catch(Exception e){
                e.printStackTrace();
            }   
            updateProgress(currentRowIndex-1,numberOfObjects);

        }
        
        FileOutputStream outStream=new FileOutputStream(config.pathToExcel);
        book.write(outStream);
        book.close();
        outStream.close();
     
    
    
    }











    







}



