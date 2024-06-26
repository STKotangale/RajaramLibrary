package com.raja.lib.invt.report.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateReport() throws Exception {
        System.out.println("SK10 ");
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Issue/IssueRegister.jrxml"));
        System.out.println("SK11 ");

        Map<String, Object> parameters = new HashMap<>();
        System.out.println("SK12 ");

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        System.out.println("SK13 ");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.out.println("SK14 ");
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        System.out.println("SK15 ");

        return outputStream;
    }
}
