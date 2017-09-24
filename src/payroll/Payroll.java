/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import dao.LineSequential;

/**
 *
 * @author Clarence
 */
public class Payroll {

        static String inFile = "E:/yao/classwork/Payroll/PayrollMaster.dat";
        static String inFileStreamName = "payrollMaster";
        static String outFile = "E:/yao/classwork/Payroll/PayrollListing.dat";
        static String outFileStreamName = "payrollList";
        static SimpleDateFormat dateFormatStandard = new SimpleDateFormat("M/d/YYYY");
        static Date today = new Date();
        static String heading1String = moveSpaces(19) + "Payroll Listing" + moveSpaces(16)
                + dateFormatStandard.format(today) + moveSpaces(22);
        static String heading2String = "EMP. No. ";
        static int [] employeeRecordInMarks = {5, 25, 27,29, 35};
        static int [] employeeRecordOutMarks = {5, 9, 29, 37, 39, 46, 54};
        static String blankRecord = moveSpaces(80);
        static DecimalFormat annualSalaryFormat = new DecimalFormat("###,##0");
        static Employee employee = new Employee();
        static String inputLine;
        static StringBuilder employeeRecordOut;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        initialization();
        
        while((inputLine = LineSequential.read(inFileStreamName)) != null)
        {
            moveFields();
        }
        
        terminationRoutine();
        
    }

    private static void initialization(){
        LineSequential.open(inFile, inFileStreamName, "input");
        LineSequential.open(outFile, outFileStreamName, "output");
        writeHeadings();
    }
    
    private static void writeHeadings(){
        LineSequential.write(outFileStreamName, blankRecord);
        LineSequential.write(outFileStreamName, heading1String);
        LineSequential.write(outFileStreamName, blankRecord);
        LineSequential.write(outFileStreamName, heading1String);
        LineSequential.write(outFileStreamName, blankRecord);
    }
   
    private static void moveFields(){
        employeeRecordOut = new StringBuilder(blankRecord);
        initializeEmployeeFields();
        makeEmployeeRecord();
        LineSequential.write(outFileStreamName, employeeRecordOut.toString());
    }
    
    private static void initializeEmployeeFields(){
        employee.setEmployeeNumber(Integer.valueOf(inputLine.substring(0, employeeRecordInMarks[0])));
        employee.setEmployeeName(inputLine.substring(employeeRecordInMarks[0], employeeRecordInMarks[1]));
        employee.setTerritoryNumber(Integer.valueOf(inputLine.substring(employeeRecordInMarks[1], employeeRecordInMarks[2])));
        employee.setAnnualSalary(Integer.valueOf(inputLine.substring(employeeRecordInMarks[3], employeeRecordInMarks[4])));
    }
    
    private static void makeEmployeeRecord(){
        String employeeNumberString = String.valueOf(employee.getEmployeeNumber());
        String employeeName = employee.getEmployeeName();
        String territoryNumberString = String.valueOf(employee.getTerritoryNumber());
        String annualSalaryString = annualSalaryFormat.format(employee.getAnnualSalary());
        employeeRecordOut.replace(employeeRecordOutMarks[0] - employeeNumberString.length(), employeeRecordOutMarks[0], employeeNumberString);
        employeeRecordOut.replace(employeeRecordOutMarks[1], employeeRecordOutMarks[2], employeeName);
        employeeRecordOut.replace(employeeRecordOutMarks[4] - territoryNumberString.length(), employeeRecordOutMarks[4], territoryNumberString);
        employeeRecordOut.replace(employeeRecordOutMarks[5], employeeRecordOutMarks[5] + 1, "$");
        employeeRecordOut.replace(employeeRecordOutMarks[6] - annualSalaryString.length(), employeeRecordOutMarks[6], annualSalaryString);
    }
    
    private static void terminationRoutine(){
        LineSequential.close(inFileStreamName, "input");
        LineSequential.close(outFileStreamName, "output");
        System.out.println("File is complete.");
    }
    
    private static String moveSpaces(int numberOfSpaces){
        StringBuilder sb1 = new StringBuilder(numberOfSpaces);
        for(int i = 0; i < numberOfSpaces; i++)
        {
            sb1.append(" ");
        }
        
        return sb1.toString();
    }    
}
