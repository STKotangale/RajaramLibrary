package com.raja.lib.auth.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public void generateReport(String outputFilePath) throws Exception {
        // Load the .jrxml file
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/AcessionReport.jrxml");

        // Parameters for the report
        Map<String, Object> parameters = new HashMap<>();

        // Fill the report with data from the database
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        // Export the report to a PDF file
        OutputStream outputStream = new FileOutputStream(new File(outputFilePath));
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
