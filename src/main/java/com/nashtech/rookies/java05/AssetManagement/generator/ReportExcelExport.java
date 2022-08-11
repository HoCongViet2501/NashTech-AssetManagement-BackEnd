package com.nashtech.rookies.java05.AssetManagement.generator;

import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReportExcelExport {
    List<ReportInterface> listReport;
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet xssfSheet;

    public ReportExcelExport(List<ReportInterface> listReport) {
        this.listReport = listReport;
        xssfWorkbook = new XSSFWorkbook();
    }

    private void writeHeader(){
        xssfSheet = xssfWorkbook.createSheet("Report");
        Row row = xssfSheet.createRow(0);
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Category", style);
        createCell(row, 1, "Total", style);
        createCell(row, 2, "Assigned", style);
        createCell(row, 3, "Available", style);
        createCell(row, 4, "Not available", style);
        createCell(row, 5, "Waiting for recycling", style);
        createCell(row, 6, "Recycled", style);
    }

    private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
        xssfSheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (valueOfCell instanceof Integer) {
            cell.setCellValue((Integer) valueOfCell);
        } else if (valueOfCell instanceof Long) {
            cell.setCellValue((Long) valueOfCell);
        } else if (valueOfCell instanceof String) {
            cell.setCellValue((String) valueOfCell);
        } else {
            cell.setCellValue((Boolean) valueOfCell);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        for (ReportInterface record: listReport) {
            Row row = xssfSheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getTotal(), style);
            createCell(row, columnCount++, record.getAssigned(), style);
            createCell(row, columnCount++, record.getAvailable(), style);
            createCell(row, columnCount++, record.getNotAvailable(), style);
            createCell(row, columnCount++, record.getWaitingForRecycling(), style);
            createCell(row, columnCount++, record.getRecycled(), style);
        }
    }

    public void generateExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        xssfWorkbook.write(outputStream);
        xssfWorkbook.close();
        outputStream.close();
    }
}
