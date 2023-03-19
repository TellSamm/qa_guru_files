package sammtell.ru;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.FileDownloadMode;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

//CSV PDF ZIP JSON XLS - работа с файлами
public class FileDowloadTest {

    static { //!!!эту конфигурацию нужно использовать только тогда когда без этого жить нельзя образно)!!!
        Configuration.fileDownload = FileDownloadMode.PROXY; //С английского proxy можно
        // перевести как «заместитель» или «доверенное лицо».
        //Действительно, схема похожа на то, как по доверенности получают деньги или совершают
        // сделки за других людей. Только прокси-сервер может общаться с сайтами,
        // приложениями и сервисами от своего имени — не сообщать, кто конечный пользователь.
    }



    @Test
    void dowloadTest() throws Exception { //Ключевое слово throws используется, чтобы в сигнатуре метода указать, что он выбрасывает исключение.
        Selenide.open("https://github.com/autotests-cloud/qa_guru_first_course/blob/master/README.md");
        File dowload = $("#raw-url").download();
        //dowload работает только с кнопками и ссылками которые содержат атрибут href=['***'], бывает что есть кнопка dowload
        //но в ней нет href=['***'], это бывает в 5% случаев из 100% но это нужно знать, но даже в таком случае Selenid может скачивать
        // файлы если сделать конфигурацию Configuration.fileDownload = FileDownloadMode.PROXY;


        //чтение файлов в java inputstream и reader

        //InputStream — абстрактный класс, описывающий входной поток байт.
        //Все методы класса предназначены для чтения байт, при возникновении ошибки они возбуждают исключение IOException.

        //Reader - это класс в package java.io, который является базовым классом,
        // представляющим поток символов (stream of characters),
        // полученных при чтении определенного источника данных, например текстового файла.

        //!Reader! - можно использовать тогда когда мы работаем с какими то закодированными в известными нам кодировками файлами типа json и т.д.
        //!InputStream! - если мы не уверены что за файл перед нами то лучше использовать InputStream он прочитает всё, это более универсально.

        //Как пишется:
        try(InputStream readmefile = new FileInputStream(dowload)){
            byte [] bytes = readmefile.readAllBytes();//прочитать содержимое файла
            String fileAsString = new String(bytes, StandardCharsets.UTF_8); // передаем массив bytes и передаем кодировку UTF-8
            Assertions.assertTrue(fileAsString.contains("qa_guru_first_course")); //проверяем что в файле содержится определенный текст
        }

        //Как только открываем InputStream или Reader нужно сразу подумать что их надо закрыть, потому что они забирают файловые дискрипторы и если не закрывать
        //то они скоро закончатся try(InputStream is = new FileInputStream(dowload)){
        // byte [] bytes = readmefile.readAllBytes(); //прочитать содержимое файла
        //}
    }

    @Test
    void uploadTest() throws Exception {
        Selenide.open("https://tus.io/demo.html");
        $("input[type='file']").uploadFromClasspath("book.png");
        $("#js-upload-container").shouldHave(text("The upload is complete!"));
    }


}
