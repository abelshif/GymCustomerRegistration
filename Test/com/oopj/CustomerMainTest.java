package com.oopj;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class CustomerMainTest {

    File file=new File("src/com/oopj/customers.txt");

    @Test
    public final void testFetchDateByPersonNummer() {
        String personnummer = "7605021234";
        assertTrue(CustomerMain.fetchDateByPersonNummer(personnummer,file).equals("2010-04-07"));
        personnummer = "7605021232";
        assertNull(CustomerMain.fetchDateByPersonNummer(personnummer,file));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public final void testNullPointerExceptionForEmptyPersonNummer() {
        String personnummer = null;
        assertNull(CustomerMain.fetchDateByPersonNummer(personnummer,file));
    }

    @Test
    public final void testFetchDateByNamn() {
        String namn = "Alhambra Aromes";
        assertEquals(CustomerMain.fetchDateByNamn(namn,file), "2019-07-01");
        namn = "James Jim";
        assertNull(CustomerMain.fetchDateByNamn(namn,file));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public final void testNullPointerExceptionForEmptyNamn() {
        String namn = null;
        assertNull(CustomerMain.fetchDateByNamn(namn,file));
    }

    @Test
    public final void testWriteToFile() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("8384849320").append(", ").append("Mr X ").append("2018-09-23");
        File file = new File("trackCustomers.txt");
        CustomerMain.writeToFile(sb.toString());
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        assertEquals("8384849320, Mr X 2018-09-23", reader.readLine().toString());
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public final void testIOExceptionForIncorrectFileName() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("8384849320").append(", ").append("Mr X ").append("2018-09-23");
        File file=new File("trackcustom.txt");
        CustomerMain.writeToFile(sb.toString());
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        assertEquals("8384849320, Mr X 2018-09-23", reader.readLine().toString());
    }

}