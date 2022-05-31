package przelew;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws IOException {
        Map<String,String> nadawcaDane = new HashMap<>();
        nadawcaDane.put("kod", "1234");
        nadawcaDane.put("nr konta","96 1160 2202 0000 0003 5630 0252");
        nadawcaDane.put("nazwa odbiorcy","Adam");
        nadawcaDane.put("nazwa odbiorcy cd","Kowalski");
        nadawcaDane.put("miejscowosc","Kraków");
        nadawcaDane.put("kod pocztowy","30-348");
        nadawcaDane.put("ulica","Grota-Roweckiego");
        nadawcaDane.put("nr domu","37/17");

        Transfer p = new Transfer(new MainFrame(),nadawcaDane,1376.49);
    }
}
