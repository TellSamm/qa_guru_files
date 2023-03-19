package sammtell.ru.parsing;
import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class FileParsingTest {

    private ClassLoader cl = FileParsingTest.class.getClassLoader();

    @Test
    void pdfParseTest() throws Exception {
        Selenide.open("https://junit.org/junit5/docs/current/user-guide/");
        File dowload = $("a[href^='junit-user-guide']").download(); // для того что бы парсить нужно добавить зависимость 'com.codeborne:pdf-test:1.5.0' -супер простая библиотечка
        PDF pdf = new PDF(dowload);
        Assertions.assertEquals("Stefan Bechtold, " +
                "Sam Brannen, Johannes Link, Matthias Merdes, " +
                "Marc Philipp, Juliette de Rancourt, Christian Stein", pdf.author);
        // сравниваем авторов из файла с актуальным результатом pdf.author используя метод author
    }


    @Test
    void xlsParseTest() throws Exception {
        Selenide.open("https://excelvba.ru/programmes/Teachers");
        File dowload = $("a[href*='teachers.xls']").download(); // для того что бы парсить нужно добавить зависимость 'com.codeborne:xls-test:1.4.3'
        XLS xls = new XLS(dowload);

        Assertions.assertTrue(xls.excel.getSheetAt(2).getRow(4).getCell(1).getStringCellValue().startsWith("Белый Владимир Михайлович"));
        // сравниваем данные из таблицы
    }

    @Test //как нам прочитать файл book2.csv ---> private ClassLoader cl = FileParsingTest.class.getClassLoader();
    void csvParseTest() throws Exception{
      try( InputStream is = cl.getResourceAsStream("book2.csv");
           InputStreamReader isr = new InputStreamReader(is)){
          CSVReader csvReader = new CSVReader(isr);
          List<String[]> content =  csvReader.readAll();
          Assertions.assertArrayEquals(new String[]{"Bethesda", "Skyrim"},content.get(3));
      }
    }

    @Test
    void (){

    }
}
