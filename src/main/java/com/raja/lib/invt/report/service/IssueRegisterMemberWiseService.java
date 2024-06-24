package com.raja.lib.invt.report.service;

import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class IssueRegisterMemberWiseService {

    @Autowired
    private DataSource dataSource;

    public ByteArrayOutputStream generateReport(String startDate, String endDate, String memberId) throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Issue/IssueRegisterMemberWise.jrxml"));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", startDate);
        parameters.put("endDate", endDate);
        parameters.put("memberId", memberId);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream;
    }
}
	