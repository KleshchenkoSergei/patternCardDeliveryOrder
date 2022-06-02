import com.github.javafaker.Faker;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.lang.String;


public class DataGenerator {
    DataGenerator() {
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //set date format

    public static RegistrationInfo generate(String locale) throws IOException {
        Faker faker = new Faker(new Locale(locale));
        Date currentDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DATE, 3); // actual date +3
        Date actualDate = cal.getTime();
        cal.add(Calendar.DATE, 7); // end date +7 (current date + 10)
        Date actualDatePlus = cal.getTime();

        Date startDate = actualDate;
        Date endDate = actualDatePlus;

        Date fakeDate = faker.date().between(startDate, endDate);

        int numCity = faker.number().numberBetween(1, 85);//generate number of city;
        String city = generateCity(numCity); //get city from csv

        String name = faker.name().fullName().replace("ё", "е");// ё replace

        return new RegistrationInfo(
                city,
                String.valueOf(dateFormat.format(fakeDate)),
                name,
                faker.numerify("+79#########")

        );


    }

    public static String generateCity(int num) throws IOException {

        String pathToCsv = "./artifacts/regionCapital.csv";
        File csvFile = new File(pathToCsv);
        String cityName = null;
        if (csvFile.isFile()) {
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
            String line;
            while ((line = csvReader.readLine()) != null) {
                boolean findNumber = line.startsWith(num +";");
                if (findNumber) {
                    cityName = line.replace(num +";","");
                }

            }
            csvReader.close();
        }

        return cityName;
    }

}





