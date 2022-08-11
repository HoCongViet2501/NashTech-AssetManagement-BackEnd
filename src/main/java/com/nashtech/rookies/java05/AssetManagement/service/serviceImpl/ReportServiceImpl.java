package com.nashtech.rookies.java05.AssetManagement.service.serviceImpl;

import com.nashtech.rookies.java05.AssetManagement.generator.ReportExcelExport;
import com.nashtech.rookies.java05.AssetManagement.model.interfaces.ReportInterface;
import com.nashtech.rookies.java05.AssetManagement.repository.ReportRepository;
import com.nashtech.rookies.java05.AssetManagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<ReportInterface> getReportListFinal() {
        return this.reportRepository.findAssetByCategoryAndState();
    }

    @Override
    public void exportReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=report" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ReportInterface> listReport = getReportListFinal();

        ReportExcelExport reportExcelExport = new ReportExcelExport(listReport);

        reportExcelExport.generateExcelFile(response);
    }
}
