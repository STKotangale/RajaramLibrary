package com.raja.lib.invt.report.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class AcessionReportService {

    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateReport() throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Accession/AcessionReport.jrxml"));

        Map<String, Object> parameters = new HashMap<>();
        InputStream logoStream = this.getClass().getResourceAsStream("/Images/RajaramLogo.png");
        if (logoStream == null) {
            logoStream = this.getClass().getResourceAsStream("./Images/RajaramLogo.png");
            if (logoStream == null) {
                throw new RuntimeException("Logo image not found in both paths.");
            }
        }
        parameters.put("LOGO_PATH", logoStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream;
    }
}
