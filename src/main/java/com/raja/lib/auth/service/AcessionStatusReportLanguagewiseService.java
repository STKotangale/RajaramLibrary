package com.raja.lib.auth.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class AcessionStatusReportLanguagewiseService {
    
    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateAcessionStatusReportPublicationwise(String publicationName) throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/AcessionStatusReportLanguagewise.jrxml"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("publicationName", publicationName);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream;
    }
}
