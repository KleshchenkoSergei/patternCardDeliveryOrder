package ru.netology.delivery.data;

import com.github.javafaker.Faker;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.lang.String;
import java.util.Random;
import java.time.LocalDate;


public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy"); //set date format

        public static String generateCity(String locale) throws IOException {
            int firstCityNum = 1;
            int lastCityNum = 85;
            int numCity = firstCityNum + (int) (Math.random() * lastCityNum);
            String city = generateCity(numCity); //get city from csv
            return city;
        }

        public static String generateDate(int daysToFirstMeeting, int daysToSecondMeeting) {
            Faker faker = new Faker(new Locale("ru"));
            LocalDate currentDate = LocalDate.now(); // current date
            LocalDate firstDate = currentDate.plusDays(daysToFirstMeeting);
            LocalDate secondDate = currentDate.plusDays(daysToSecondMeeting);

            LocalDate startDate = firstDate;
            LocalDate endDate = secondDate;
            Date d1 = convertToDateViaInstant(startDate);
            Date d2 = convertToDateViaInstant(endDate);

            Date fakeDate = faker.date().between(d1, d2);

            return String.valueOf(dateFormat.format(fakeDate));
        }

        public static Date convertToDateViaInstant(LocalDate dateToConvert) {
            return java.util.Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }

        public static String generateName(String locale) throws IOException {
            Faker faker = new Faker(new Locale(locale));
            String name = faker.name().fullName().replace("ё", "е");// ё replace

            return name;
        }

        public static String generatePhone(String locale) throws IOException {
            Faker faker = new Faker(new Locale(locale));
            String name = faker.numerify("+79#########");

            return name;
        }

        public static String generateCity(int num) throws IOException {
            String pathToCsv = "./artifacts/regionCapital.csv";
            File csvFile = new File(pathToCsv);
            String cityName = null;
            if (csvFile.isFile()) {
                BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv));
                String line;
                while ((line = csvReader.readLine()) != null) {
                    boolean findNumber = line.startsWith(num + ";");
                    if (findNumber) {
                        cityName = line.replace(num + ";", "");
                    }
                }
                csvReader.close();
            }
            return cityName;
        }

    }

}





