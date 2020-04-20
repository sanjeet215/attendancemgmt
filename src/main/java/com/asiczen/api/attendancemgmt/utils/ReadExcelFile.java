package com.asiczen.api.attendancemgmt.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.SomeInternalException;
import com.asiczen.api.attendancemgmt.model.LeaveTypes;
import com.asiczen.api.attendancemgmt.services.LeaveTypesServiceImple;

/* Read Excel File and upload data into table.*/

@Service
public class ReadExcelFile {

	private static final Logger logger = Logger.getLogger(ReadExcelFile.class);
	
	@Autowired
	LeaveTypesServiceImple leaveService;
	
	@Value("${file.upload-dir}")
	private String uploadDir;
	
	
	public void readLeaveTypeExcel(String filePath,String orgId) {

		
		logger.debug("File path :"+ filePath);
		
		List<String> fileData = new ArrayList<String>();
		String readRow = null;
		

		try {
			File excel = new File(uploadDir+"/"+filePath);
			FileInputStream fis = new FileInputStream(excel);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) {
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						readRow=readRow+cell.getStringCellValue() + ";";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						readRow=readRow+cell.getNumericCellValue() + ";";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						readRow=readRow+cell.getBooleanCellValue()+";";
						break;
					default:
					}
				}
				
				fileData.add(readRow);
				
				readRow = null;
			}

			
			book.close();
			fis.close();
			
			uploadLeaveData(fileData,orgId);
			
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
			throw new SomeInternalException("File Not Found to Upload",fe.getCause());
		} catch (IOException ie) {
			ie.printStackTrace();
			throw new SomeInternalException("File Not Found to Upload",ie.getCause());
		}catch (Exception ep) {
			ep.printStackTrace();
			throw new SomeInternalException("File Not Found to Upload",ep.getCause());
		}
	}
	
	@Transactional
	public void uploadLeaveData(List<String> fileContent,String orgId){
		
		fileContent.remove(0);
		
		for(String item:fileContent){
			String[] elements = item.split(";");
			LeaveTypes leaves = new LeaveTypes();
			
			logger.info("Processing:"+ item);
			
			leaves.setQuantity(Integer.parseInt(elements[2].replace(".0", "")));
			leaves.setLeaveTypeName(elements[1]);
			leaves.setStatus(true);
			leaves.setOrgId(orgId);
		
			
			leaveService.postLeaves(leaves);
		}
		
	}
	
	
}
