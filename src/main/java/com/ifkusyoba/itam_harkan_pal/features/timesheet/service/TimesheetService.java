package com.ifkusyoba.itam_harkan_pal.features.timesheet.service;

import com.ifkusyoba.itam_harkan_pal.core.exception.DataNotFoundException;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PostTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.GetTimesheetResponse;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.dto.timesheet.PutTimesheetRequest;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Job;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.Timesheet;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.entity.WorkOrder;
import com.ifkusyoba.itam_harkan_pal.features.timesheet.repository.TimesheetRepository;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TimesheetService {

        private final TimesheetRepository timesheetRepository;

        @Autowired
        public TimesheetService(TimesheetRepository timesheetRepository) {
                this.timesheetRepository = timesheetRepository;
        }

        public List<GetTimesheetResponse> getAllTimesheet() {
                List<Timesheet> timesheets = timesheetRepository.findAll();

                return timesheets.stream()
                                .map(timesheet -> GetTimesheetResponse.builder()
                                                .idTimesheet(timesheet.getIdTimesheet())
                                                .timesheetName(timesheet.getTimesheetName())
                                                .timesheetDate(timesheet.getTimesheetDate())
                                                .build())
                                .collect(Collectors.toList());
        }

        public GetTimesheetResponse getTimesheetById(Integer id) {
                Timesheet timesheet = timesheetRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));
                return GetTimesheetResponse.builder()
                                .idTimesheet(timesheet.getIdTimesheet())
                                .timesheetName(timesheet.getTimesheetName())
                                .timesheetDate(timesheet.getTimesheetDate())
                                .build();
        }

        @Transactional
        public GetTimesheetResponse createTimesheet(PostTimesheetRequest request) {
                Timesheet timesheet = new Timesheet();
                timesheet.setTimesheetName(request.getTimesheetName());
                timesheet.setTimesheetDate(request.getTimesheetDate());
                timesheetRepository.save(timesheet);

                return GetTimesheetResponse.builder()
                                .idTimesheet(timesheet.getIdTimesheet())
                                .timesheetName(timesheet.getTimesheetName())
                                .timesheetDate(timesheet.getTimesheetDate())
                                .build();
        }

        @Transactional
        public GetTimesheetResponse updateTimesheet(Integer id, PutTimesheetRequest request) {
                Timesheet timesheet = timesheetRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));

                timesheet.setTimesheetName(request.getTimesheetName());
                timesheet.setTimesheetDate(request.getTimesheetDate());
                timesheetRepository.save(timesheet);

                return GetTimesheetResponse.builder()
                                .idTimesheet(timesheet.getIdTimesheet())
                                .timesheetName(timesheet.getTimesheetName())
                                .timesheetDate(timesheet.getTimesheetDate())
                                .build();
        }

        public byte[] exportTimesheetToExcel(Integer id) throws IOException {
                Timesheet timesheet = timesheetRepository.findById(id).orElseThrow(
                                () -> new DataNotFoundException("Timesheet with id " + id + " not found"));
                XSSFWorkbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Timesheet");

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setBorderBottom(BorderStyle.THIN);
                headerStyle.setBorderTop(BorderStyle.THIN);
                headerStyle.setBorderLeft(BorderStyle.THIN);
                headerStyle.setBorderRight(BorderStyle.THIN);

                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerStyle.setFont(headerFont);

                Row headerRow = sheet.createRow(0);
                String[] headers = { "Tanggal", "Nama Pekerjaan", "Kode WorkOrder", "Jam", "Nama Work Order" };
                for (int i = 0; i < headers.length; i++) {
                        Cell cell = headerRow.createCell(i);
                        cell.setCellValue(headers[i]);
                        cell.setCellStyle(headerStyle);
                }

                CellStyle[] workOrderStyles = new CellStyle[3];
                IndexedColors[] colors = {
                                IndexedColors.LIGHT_YELLOW,
                                IndexedColors.LIGHT_GREEN,
                                IndexedColors.LIGHT_TURQUOISE
                };

                for (int i = 0; i < workOrderStyles.length; i++) {
                        workOrderStyles[i] = workbook.createCellStyle();
                        workOrderStyles[i].setBorderBottom(BorderStyle.THIN);
                        workOrderStyles[i].setBorderTop(BorderStyle.THIN);
                        workOrderStyles[i].setBorderLeft(BorderStyle.THIN);
                        workOrderStyles[i].setBorderRight(BorderStyle.THIN);
                        workOrderStyles[i].setFillForegroundColor(colors[i].getIndex());
                        workOrderStyles[i].setFillPattern(FillPatternType.SOLID_FOREGROUND);
                }

                Locale localeIndonesia = new Locale("id", "ID");
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", localeIndonesia);

                int rowNum = 1;
                int colorIndex = 0;

                for (WorkOrder workOrder : timesheet.getWorkOrders()) {
                        CellStyle currentStyle = workOrderStyles[colorIndex % workOrderStyles.length];

                        for (Job job : workOrder.getJobs()) {
                                Row row = sheet.createRow(rowNum++);

                                Cell dateCell = row.createCell(0);
                                String formattedDate = job.getJobDate()
                                                .toLocalDateTime()
                                                .toLocalDate()
                                                .format(dateFormatter);
                                dateCell.setCellValue(formattedDate);
                                dateCell.setCellStyle(currentStyle);

                                Cell jobNameCell = row.createCell(1);
                                jobNameCell.setCellValue(job.getJobName());
                                jobNameCell.setCellStyle(currentStyle);

                                Cell workOrderCodeCell = row.createCell(2);
                                workOrderCodeCell.setCellValue(workOrder.getWorkOrderCode());
                                workOrderCodeCell.setCellStyle(currentStyle);

                                Cell durationCell = row.createCell(3);
                                durationCell.setCellValue(job.getJobDuration());
                                durationCell.setCellStyle(currentStyle);

                                Cell workOrderNameCell = row.createCell(4);
                                workOrderNameCell.setCellValue(workOrder.getWorkOrderName());
                                workOrderNameCell.setCellStyle(currentStyle);
                        }
                        colorIndex++;
                }

                for (int i = 0; i < headers.length; i++) {
                        sheet.autoSizeColumn(i);
                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                workbook.write(outputStream);
                workbook.close();

                return outputStream.toByteArray();
        }

        @Transactional
        public void deleteTimesheet(Integer id) {
                Timesheet timesheet = timesheetRepository.findById(id)
                                .orElseThrow(() -> new DataNotFoundException("Timesheet with id " + id + " not found"));
                timesheetRepository.delete(timesheet);

                GetTimesheetResponse.builder()
                                .idTimesheet(timesheet.getIdTimesheet())
                                .timesheetName(timesheet.getTimesheetName())
                                .timesheetDate(timesheet.getTimesheetDate())
                                .build();
        }
}
