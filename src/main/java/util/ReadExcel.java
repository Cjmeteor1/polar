package util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReadExcel {

    public static void main(String[] args) {
        CreateExcel("D:\\program\\mywork\\webset\\src\\java\\resources\\config\\scene.xls");
    }

    //原始版本
    public static void readExcel(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(path);
        FileInputStream fis = null;
        Workbook workBook = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                workBook = WorkbookFactory.create(fis);
                int numberOfSheets = workBook.getNumberOfSheets();
                // sheet工作表
                for (int s = 0; s < numberOfSheets; s++) {
                    Sheet sheetAt = workBook.getSheetAt(s);
                    //获取工作表名称
                    String sheetName = sheetAt.getSheetName();
                    System.out.println("工作表名称：" + sheetName);
                    // 获取当前Sheet的总行数
                    int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
                    System.out.println("当前表格的总行数:" + rowsOfSheet);
                    // 第一行
                    Row row0 = sheetAt.getRow(0);
                    int physicalNumberOfCells = sheetAt.getRow(0).getPhysicalNumberOfCells();
                    String[] title = new String[physicalNumberOfCells];
                    for (int i = 0; i < physicalNumberOfCells; i++) {
                        title[i] = row0.getCell(i).getStringCellValue();
                    }
                    for (int r = 1; r < rowsOfSheet; r++) {
                        Row row = sheetAt.getRow(r);
                        if (row == null) {
                            continue;
                        } else {
                            int rowNum = row.getRowNum() + 1;
                            System.out.println("当前行:" + rowNum);
                            // 总列(格)
                            Cell cell0 = row.getCell(0);
                            Cell cell1 = row.getCell(1);
                            Cell cell2 = row.getCell(2);
                            Cell cell3 = row.getCell(3);
                            Cell cell4 = row.getCell(4);

                            if ((cell0.getCellTypeEnum() == CellType.NUMERIC) && (!DateUtil.isCellDateFormatted(cell0))) {
                                int numericCellValue = (int) cell0.getNumericCellValue();
                                System.out.println(numericCellValue);
                            } else {
                                System.out.println("第" + rowNum + "行，第一列[" + title[0] + "]数据错误！");
                            }
                            if (cell1.getCellTypeEnum() == CellType.STRING) {
                                String stringCellValue = cell1.getStringCellValue();
                                System.out.println(stringCellValue);
                            } else {
                                System.out.println("第" + rowNum + "行，第二列[" + title[1] + "]数据错误！");
                            }
                            if (cell2.getCellTypeEnum() == CellType.STRING) {
                                String stringCellValue = cell2.getStringCellValue();
                                System.out.println(stringCellValue);
                            } else {
                                System.out.println("第" + rowNum + "行，第三列[" + title[2] + "]数据错误！");
                            }
                            if ((cell3.getCellTypeEnum() == CellType.NUMERIC) && DateUtil.isCellDateFormatted(cell3)) {
                                Date dateCellValue = cell3.getDateCellValue();
                                System.out.println(sdf.format(dateCellValue));
                            } else {
                                System.out.println("第" + rowNum + "行，第四列[" + title[3] + "]数据错误！");
                            }
                            if ((cell4.getCellTypeEnum() == CellType.NUMERIC) && (!DateUtil.isCellDateFormatted(cell4))) {
                                double numericCellValue = cell4.getNumericCellValue();
                                System.out.println(numericCellValue);
                            } else {
                                System.out.println("第" + rowNum + "行，第五列[" + title[4] + "]数据错误！");
                            }
                        }
                    }
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    public static void readExcel2(String path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File file = new File(path);
        FileInputStream fis = null;
        Workbook workBook = null;
        if (file.exists()) {
            try {
                fis = new FileInputStream(file);
                workBook = WorkbookFactory.create(fis);
                int numberOfSheets = workBook.getNumberOfSheets();
                // sheet工作表
                Sheet sheetAt = workBook.getSheetAt(0);
                //获取工作表名称
                String sheetName = sheetAt.getSheetName();
                System.out.println("工作表名称：" + sheetName);
                // 获取当前Sheet的总行数
                int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
                System.out.println("当前表格的总行数:" + rowsOfSheet);
                // 第一行
                Row row0 = sheetAt.getRow(0);
                //ColNumber 列数
                int ColNumber = sheetAt.getRow(0).getPhysicalNumberOfCells();
                String[] title = new String[ColNumber];
                for (int i = 0; i < ColNumber; i++) {
                    title[i] = row0.getCell(i).getStringCellValue();
                }
                System.out.println("列数:" + ColNumber);
                for (String str : title) {
                    System.out.println("标题:" + str);
                }
                //CellType.NUMERIC
                for (int r = 1; r < rowsOfSheet; r++) {
                    Row row = sheetAt.getRow(r);
                    for (int i = 0; i < ColNumber; i++) {
                        //getNumericCellValue获取数字  getStringCellValue获取字符串
                        System.out.print(row.getCell(i).getNumericCellValue() + ";");
                    }
                    System.out.println("");
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    //读取Excel组装成 List-Map
    public static List<Map> readExcelToListMap(String path, String name, String rate) {
        File file = new File(path);
        FileInputStream fis = null;
        Workbook workBook = null;
        if (!file.exists()) {
            System.out.println("文件不存在");
            return null;
        }

        try{
            fis = new FileInputStream(file);
            workBook = WorkbookFactory.create(fis);
        }catch (IOException | InvalidFormatException e){
            e.printStackTrace();
        }

        // sheet工作表
        Sheet sheetAt = workBook.getSheetAt(0);
        // 获取当前Sheet的总行数
        int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
        // 第一行
        Row row0 = sheetAt.getRow(0);
        //ColNumber 列数
        int ColNumber = sheetAt.getRow(0).getPhysicalNumberOfCells();
        String[] title = new String[ColNumber];

        //判断name与rate的列号
        int col_name = -1, col_rate = -1;

        for (int i = 0; i < ColNumber; i++) {
            title[i] = row0.getCell(i).getStringCellValue();
            if (title[i].equals(name)) {
                col_name = i;
            }
            if (title[i].equals(rate)) {
                col_rate = i;
            }
        }
        //传入列名出错时抛出异常
        if (col_name < 0) {
            System.out.println("Excel中不存在列名：" + name);
            return null;
        }
        if (col_rate<0 && !"NullOfRate".equals(rate)) {
            System.out.println("Excel中不存在列名：" + rate);
            return null;
        }

        Row row;
        Cell cell_name, cell_rate;
        List<Map> list = new ArrayList<>();
        for (int r = 1; r < rowsOfSheet; r++) {
            row = sheetAt.getRow(r);
            cell_name = row.getCell(col_name);
            cell_rate = row.getCell(col_rate);
            Map<String,Object> map = new HashMap<>();

            if (isString(cell_name)) {
                map.put(name, cell_name.getStringCellValue());
            } else {
                map.put(name, cell_name.getNumericCellValue());
            }
            if("NullOfRate".equals(rate)){
                map.put(rate, 1.0);
            }else{
                if (isString(cell_rate)) {
                    map.put(rate, cell_rate.getStringCellValue());
                } else {
                    map.put(rate, cell_rate.getNumericCellValue());
                }
            }
            list.add(map);
        }
        return list;
    }

    //判断Excel单元格数据类型
    public static boolean isString(Cell cell) {
        return cell.getCellTypeEnum() == CellType.STRING;
    }

    //获取单元格数据(作废)
    public static Object getCellData(Cell cell) throws Exception {
        if(cell.getCellTypeEnum() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellTypeEnum() == CellType.NUMERIC){
            return cell.getNumericCellValue();
        }else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
            return cell.getBooleanCellValue();
        }else{
            throw new Exception("未知类型");
        }
    }

    //从Excel某列中获取平均权重随机值 num-生成随机值个数
    public static List<Object> randomFromExcel(String path, int num, String colName) {
        File file = new File(path);
        FileInputStream fis = null;
        Workbook workBook = null;
        if (!file.exists()) {
            System.out.println("文件不存在");
            return null;
        }

        try{
            fis = new FileInputStream(file);
            workBook = WorkbookFactory.create(fis);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }

        // sheet工作表
        Sheet sheetAt = workBook.getSheetAt(0);
        // 获取当前Sheet的总行数
        int rowsOfSheet = sheetAt.getPhysicalNumberOfRows();
        // 第一行
        Row row0 = sheetAt.getRow(0);
        //ColNumber 列数
        int ColNumber = sheetAt.getRow(0).getPhysicalNumberOfCells();
        String[] title = new String[ColNumber];

        //判断colName的列号
        int colNumber = -1;
        for (int i = 0; i < ColNumber; i++) {
            title[i] = row0.getCell(i).getStringCellValue();
            if (title[i].equals(colName)) {
                colNumber = i;
            }
        }
        //传入列名出错时抛出异常
        if (colNumber < 0) {
            System.out.println("Excel中不存在列名：" + colName);
            return null;
        }

        Row row;
        Cell cell;
        List<Object> list = new ArrayList<>();
        for(int i=1;i<=num;i++){
            int rad = Randomizer.random(1,rowsOfSheet-1);
            row = sheetAt.getRow(rad);
            cell = row.getCell(colNumber);
            if (isString(cell)) {
                list.add(cell.getStringCellValue());
            } else {
                list.add(cell.getNumericCellValue());
            }
        }
        return list;
    }

    public static void CreateExcel(String filepath) {
        try {
            FileInputStream excelFileInputStream = new FileInputStream(filepath);
            XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);//拿到文件转化为javapoi可操纵类型
            excelFileInputStream.close();
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 获取行数
            int rows = sheet.getLastRowNum();
            // 获取列数
            int clos = sheet.getRow(0).getPhysicalNumberOfCells();

            XSSFWorkbook work = new XSSFWorkbook();
            XSSFSheet she = work.createSheet("tt3");
            for (int i = 1; i < rows; i++) {
                XSSFRow row = sheet.getRow(i);//得到行
                // 创建行
                XSSFRow nowRow = she.createRow(i);
                for (int j = 0; j < clos; j++) {
                    XSSFCell cell = row.getCell(j);//得到列
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    //System.out.println(cell.getStringCellValue());
                    // 创建列
                    XSSFCell nowCell = nowRow.createCell(j);
                    nowCell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    nowCell.setCellValue(cell.getStringCellValue() + "+");
                }
                for (int j = 0; j < clos; j++) {
                    XSSFCell cell = nowRow.getCell(j);//得到列
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    System.out.print(cell.getStringCellValue() + " ");
                }
                System.out.println();
            }
            //保存
            FileOutputStream excelFileOutPutStream = new FileOutputStream(filepath);
            work.write(excelFileOutPutStream);
            excelFileOutPutStream.flush();
            excelFileOutPutStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("done");
    }
}
