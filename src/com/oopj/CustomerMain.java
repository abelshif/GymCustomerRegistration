package com.oopj;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Created by Abel Shiferaw Mamo
 * Date: 2020-10-14
 * Time: 10:17
 * Project name: InlämningUppgift2
 */

public class CustomerMain {

    /**
     * Metoden "main" gör fundament för olika val angående vad man kan skriva på terminalen
     * liksom "l" för att läsa från filen eller "s" för att skriva till filen; "p" för att skriva
     * personnummer och "n" för att skriva namn.
     * Klassen "LokalDate" är importerad för att få registreringsdatum och aktuellt datum som blir
     * jämföras mellan varandra genom att använda metoden "DAYS.between( , )".
     * Klassen "DateTimeFormatter" är importerad för att formatera datum samma som i filen.
     * Klasen "StringBuilder" är används att skappa samma filstruktur som "customer.txt"  medan skapar ny fil.
     *
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(System.in);
        String registrationDate = null;
        StringBuilder sb = new StringBuilder();
        boolean bool = true;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        File file = new File("customers.txt");

        while (bool) {
            try {
                System.out.println("\n Vill du läsa från eller skriva till fil?[l/s],exit[e]");
                String response = scan.nextLine();

                if (response.equalsIgnoreCase("l")) {
                    System.out.println("Tryck 'p' för att läsa personnummer eller 'n' för namn.");
                    String input = scan.nextLine();

                    if (input.equalsIgnoreCase("p")) {
                        System.out.println("ange personnummer");
                        String personnummer = scan.nextLine();
                        registrationDate = fetchDateByPersonNummer(personnummer, file);

                    } else if (input.equalsIgnoreCase("n")) {
                        System.out.println("ange namn");
                        String namn = scan.nextLine();
                        registrationDate = fetchDateByNamn(namn, file);

                    }

                    LocalDate registerDate = LocalDate.parse(registrationDate, dateTimeFormatter);
                    LocalDate currentDate = LocalDate.now();
                    long daysBetween = DAYS.between(registerDate, currentDate);

                    if (daysBetween <= 365) {
                        System.out.println("kunden är en nuvarande medlem");
                    } else if (daysBetween > 365) {
                        System.out.println("en före detta kund ");
                    } else {
                        System.out.println("obehörig");
                    }
                } else if (response.equalsIgnoreCase("s")) {
                    System.out.println("Ange personnummer");
                    String personnummer = scan.nextLine();
                    System.out.println("Ange namn");
                    String namn = scan.nextLine();
                    System.out.println("Ange date");
                    String date = scan.nextLine();
                    StringBuilder customerInfo = sb.append(personnummer).append(", ").append(namn).
                            append("\n").append(date).append("\n");
                    writeToFile(customerInfo.toString());
                    System.out.println("Framgångsrikt skriven");
                    bool = true;

                } else if (response.equalsIgnoreCase("e")) {
                    System.exit(0);
                }
            } catch (NullPointerException e) {
                System.out.println("kunden hittades inte");
                bool = true;
            }
        }
    }

    /**
     * Metoden "fetchDateByPersonNummer" läser kundens registreringsdatum från "customer.txt" filen.
     * om man ange personnummer.
     * klassen "BufferedReader" är används att läsa från filen.
     * Filen anses som Sträng dvs båda pesonnummer och datum anses som Sträng.
     * Klasen "ArrayList" är importerad för att få sträng av kundlista.
     *
     * @param personnummer
     * @param file
     * @return
     */

    public static String fetchDateByPersonNummer(String personnummer, File file) {
        if (personnummer != null) {
            List<String> listOfCustomers = new ArrayList<>();
            String customerline;

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(file))) {

                while ((customerline = reader.readLine()) != null) {
                    String line = customerline.substring(0, 10);
                    listOfCustomers.add(line);
                }
                for (int i = 0; i < listOfCustomers.size(); i += 2) {
                    if (listOfCustomers.get(i) != null) {
                        if (listOfCustomers.get(i).equals(personnummer)) {
                            return listOfCustomers.get(i + 1);
                        }
                    } else {
                        throw new NullPointerException();
                    }
                }
            } catch (IOException e) {
                System.out.println("IO Excepion " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("kund hittades ej " + e.getMessage());
            }
        } else
            throw new NullPointerException();
        return null;
    }

    /**
     * Metoden "fetchDateByNamn" läser kundens registreringsdatum från "customer.txt" fil när man anges namn.
     * Gör samma som föregående metoden "fetchDateByPersonNummer" utan ändra personummer till namn.
     * Dagslistan är används självstandig från filen genom att använda klassen "ArrayList" att få
     * registreringsdatum när man skriver namn.
     *
     * @param namn
     * @param file
     * @return
     */

    public static String fetchDateByNamn(String namn, File file) {
        if (namn != null) {
            List<String> listOfCustomers = new ArrayList<>();
            List<String> listOfDays = new ArrayList<>();
            String customerline;

            try (BufferedReader reader = new BufferedReader(
                    new FileReader(file))) {

                while ((customerline = reader.readLine()) != null) {
                    String line = customerline.substring(12);
                    listOfCustomers.add(line);
                    String next = reader.readLine();
                    listOfDays.add(next);

                }
                for (int i = 0; i < listOfCustomers.size(); i++) {
                    if (listOfCustomers.get(i) != null) {
                        if (listOfCustomers.get(i).equals(namn)) {
                            return listOfDays.get(i);
                        }
                    } else {
                        throw new NullPointerException();
                    }
                }

            } catch (IOException e) {
                System.out.println("IO Excepion " + e.getMessage());
            } catch (NullPointerException e) {
                System.out.println("kund hittades ej " + e.getMessage());
            }
        } else
            throw new NullPointerException();
        return null;
    }

    /**
     * Metoden "writeToFile" skapar en sträng fil "trackCustomers.txt" genom att använda klassen "BufferedWriter".
     * "writer.write(customer)" är används att skappa samma filformat som "customer.txt" medan att skriva kundens
     * personnummer, namn och närvarande datum och gör ny registrering.
     *
     * @param customer
     * @throws IOException
     */

    public static void writeToFile(String customer) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("trackCustomers.txt"))) {
            writer.write(customer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
