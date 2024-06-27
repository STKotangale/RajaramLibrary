package com.raja.lib.invt.report.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateReport() throws Exception {
        System.out.println("SK10 ");

        // Compile the .jrxml file to .jasper
        ClassPathResource jrxmlResource = new ClassPathResource("Accession/AcessionReport.jrxml");
        InputStream jrxmlInputStream = jrxmlResource.getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
        System.out.println("SK11 ");

        // No parameters
        Map<String, Object> params = new HashMap<>();
        System.out.println("SK12 ");

        // Fill the report using the data source
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource.getConnection());
        System.out.println("SK13 ");

        // Export the report to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.out.println("SK14 ");
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        System.out.println("SK15 ");

        return outputStream;
    }
}
