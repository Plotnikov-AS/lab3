package ru.pis.lab3.reader;

import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import ru.pis.lab3.dao.CourseDao;
import ru.pis.lab3.entity.Course;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Double.valueOf;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseDao courseDao;

    public void calculateMidCourseVal() {
        List<Course> allCourses = courseDao.getAllCourses();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("S&P middle");
        CreationHelper creationHelper = workbook.getCreationHelper();
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("m/d/yy"));
        row.createCell(0).setCellValue(creationHelper.createRichTextString("Date"));
        row.createCell(1).setCellValue(creationHelper.createRichTextString("Middle"));
        row.createCell(2).setCellValue(creationHelper.createRichTextString("Open"));
        row.createCell(3).setCellValue(creationHelper.createRichTextString("Close"));
        for (int i = 0; i < allCourses.size(); i++) {
            Course course = allCourses.get(i);
            row = sheet.createRow(i + 1);
            Cell cell = row.createCell(0);
            cell.setCellValue(course.getDate());
            cell.setCellStyle(cellStyle);

            double middle = (course.getOpen() + course.getClose()) / 2;
            row.createCell(1).setCellValue(middle);

            row.createCell(2).setCellValue(course.getOpen());
            row.createCell(3).setCellValue(course.getClose());

        }
        try (OutputStream fileOut = new FileOutputStream("files/output.xls")) {
            workbook.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void readCoursesInputFile() {
        try (CSVReader reader = new CSVReader(new FileReader("files/input.csv"))) {
            List<String[]> lines = reader.readAll();
            lines.forEach(line -> {
                if (lines.indexOf(line) != 0) {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                        Date date = sdf.parse(line[0]);
                        Course course = new Course();
                        course.setDate(date);
                        course.setOpen(valueOf(line[1].replaceAll(",", "")));
                        course.setHigh(valueOf(line[2].replaceAll(",", "")));
                        course.setLow(valueOf(line[3].replaceAll(",", "")));
                        course.setClose(valueOf(line[4].replaceAll(",", "")));
                        courseDao.addCourse(course);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
