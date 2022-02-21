package Clase13.Utilities;

import Clase13.Clases.Lead;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeadDataLoad {

    public static List<String> dataList = new ArrayList<>();
    public static List<Lead> listOfLead = new ArrayList<>();


    public static List<Lead> getListLeadFromCSV() throws CsvValidationException, IOException {

        csvReader();
        createALeadList();

        return listOfLead;

    }

    private static void csvReader() throws CsvValidationException, IOException {

        CSVReader reader = new CSVReader(new FileReader("C:\\Users\\esteb\\IdeaProjects\\CursoApisSelenium\\src\\test\\java\\Clase13\\LeadCSV.csv"));

        String[] celda;

        while( ( celda = reader.readNext()) != null){

            for (int i = 0; i < celda.length; i++)
            {
                String info = celda[i];

                //System.out.println(info);
                dataList.add(info);
            }
        }

    }

    private static void createALeadList(){

        for (String textLeadLine : dataList){
            String [] infoLead = textLeadLine.split(";");
            String name = infoLead[0];
            String company = infoLead[1];

            Lead aLead = new Lead(name, company);

            listOfLead.add(aLead);
        }
       /* for (Lead aLead : listOfLead){
            System.out.println("----->"+aLead);
        }*/
    }
}
