package sammtell.ru;
//{
//        "name": "Semen",
//        "age": 32,
//        "isClever": true,
//        "hobbies": ["sport", "games", "automationtesting"],
//        "passport": {
//        "number": 223344б,
//        "issueDate": "14 MAY 2023"
//        }

import java.util.List;

public class Human {

    public String name;
    public Integer age;
    public Boolean isClever;
    public List<String> hobbies;
    public Passport passport;



    public static class Passport{
        public Integer number;
        public String issueDate;

    }

}
