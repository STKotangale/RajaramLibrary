package com.raja.lib.auth.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class IssueReportService {

    @Autowired
    private DataSource dataSource;

    public void generateIssueReport(String outputFilePath) throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/IssueReport.jrxml");

        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
