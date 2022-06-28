package com.example.overseas.consultancy.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.overseas.consultancy.Constant.ApplicationConstant;
import com.example.overseas.consultancy.entity.UserDto;
import com.example.overseas.consultancy.service.DocumentService;
import com.example.overseas.consultancy.service.RagistrationService;

@Service
public class DocumentServiceImpl implements DocumentService {
	public static final Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);

	@Autowired
	private RagistrationService ragistrationService;

	@Override
	public Map<String, Object> uplodeExcelData(MultipartFile[] files) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			for (MultipartFile file : files) {
				File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());

				file.transferTo(convFile);
				FileInputStream fileExcel = new FileInputStream(convFile);

				Integer rawCount = 0;
				Integer columnCount = 0;

				// Create Workbook instance holding reference to .xlsx file
				XSSFWorkbook workbook = new XSSFWorkbook(fileExcel);

				// Get first/desired sheet from the workbook
				XSSFSheet Sheet1 = workbook.getSheetAt(0);

				// Iterate through each rows one by one
				Iterator<Row> rowIterator = Sheet1.iterator();

				while (rowIterator.hasNext()) {
					UserDto userData = new UserDto();
					Row row = rowIterator.next();
					// For each row, iterate through all the columns
					Iterator<Cell> cellIterator = row.cellIterator();
					if (rawCount >= 1) {
						while (cellIterator.hasNext()) {
							// System.out.println("==========================excel=====================>>>>");
							Cell cell = cellIterator.next();
							// Check the cell type and format accordingly
							if (cell.getCellType().toString().equalsIgnoreCase("NUMERIC")) {
								System.out.print(cell.getNumericCellValue() + " rawCount : " + rawCount
										+ " And columnCount : " + columnCount);

								// System.out.println("========================NUMERIC=========================>>>>");

								if (columnCount == 1)
									userData.setName(Double.toString(cell.getNumericCellValue()));
								if (columnCount == 2)
									userData.setPassword(Integer.toString((int) cell.getNumericCellValue()));
								if (columnCount == 3)
									userData.setEmail(Double.toString(cell.getNumericCellValue()));
								if (columnCount == 4)
									userData.setRole(Double.toString(cell.getNumericCellValue()));
								if (columnCount == 5)
									userData.setSpecification(Double.toString(cell.getNumericCellValue()));

							} else if (cell.getCellType().toString().equalsIgnoreCase("STRING")) {
								System.out.print(cell.getStringCellValue() + " rawCount : " + rawCount
										+ " And columnCount : " + columnCount);

								// System.out.println("========================STRING=========================>>>>");

								if (columnCount == 1)
									userData.setName(cell.getStringCellValue());
								System.out.println("=======>>>" + userData.getName());
								if (columnCount == 2)
									userData.setPassword(cell.getStringCellValue());
								System.out.println("=======>>>" + userData.getPassword());
								if (columnCount == 3)
									userData.setEmail(cell.getStringCellValue());
								System.out.println("=======>>>" + userData.getEmail());
								if (columnCount == 4)
									userData.setRole(cell.getStringCellValue());
								System.out.println("=======>>>" + userData.getRole());
								if (columnCount == 5)
									userData.setSpecification(cell.getStringCellValue());
								System.out.println("=======>>>" + userData.getSpecification());
							}

							++columnCount;
						}

						ragistrationService.SaveUser(userData, "addByAdmin");
					}

					columnCount = 0;
					++rawCount;
					System.out.println("");
				}
				fileExcel.close();
				map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_200);
				map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.EXCEL_SHEET_UPLOAD_SUCCESS);

			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put(ApplicationConstant.RESPONSE_STATUS, ApplicationConstant.STATUS_400);
			map.put(ApplicationConstant.RESPONSE_MESSAGE, ApplicationConstant.EXCEL_LIST_NOT_FOUND);

		}
		return map;
	}
}