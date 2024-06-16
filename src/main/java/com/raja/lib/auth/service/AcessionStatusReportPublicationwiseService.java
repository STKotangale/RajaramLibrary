package com.raja.lib.auth.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class AcessionStatusReportPublicationwiseService {

    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateAcessionStatusReportPublicationwise(String publicationName) throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/AcessionStatusReportPublicationwise.jrxml"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("publicationName", publicationName);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream;
    }
}
