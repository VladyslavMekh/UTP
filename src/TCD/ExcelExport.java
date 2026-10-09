package TCD;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelExport {

    public static void exportResults(
            String[] threadTypes,
            long[] averageTimes,
            long[] percentile50,
            long[] percentile90,
            long[] percentile99
    ) throws IOException {

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Results");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Thread type");
            header.createCell(1).setCellValue("Average time (ns)");
            header.createCell(2).setCellValue("P50");
            header.createCell(3).setCellValue("P90");
            header.createCell(4).setCellValue("P99");

            for (int i = 0; i < threadTypes.length; i++) {
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(threadTypes[i]);
                row.createCell(1).setCellValue(averageTimes[i]);
                row.createCell(2).setCellValue(percentile50[i]);
                row.createCell(3).setCellValue(percentile90[i]);
                row.createCell(4).setCellValue(percentile99[i]);
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);

            try (FileOutputStream out =
                         new FileOutputStream("src/TCD/Output/benchmark_result.xlsx")) {
                workbook.write(out);
            }
        }
    }
}
