package uk.ac.ed.inf;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.ed.inf.ilp.data.*;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A very simple client to GET JSON data from a remote server
 */
public class TestClient
{
    public static void main(String[] args )
    {
        if (args.length != 2){
            System.err.println("Testclient Base-URL Echo-Parameter");
            System.err.println("you must supply the base address of the ILP REST Service\n" +
                    " e.g. http://restservice.somewhere and a string to be echoed");
            System.exit(1);
        }

        try {
            String baseUrl = args[0];
            String echoBasis = args[1];

            if (! baseUrl.endsWith("/")){
                baseUrl += "/";
            }

            // we call the test endpoint and pass in some test data which will be echoed
            URL url = new URL(baseUrl + "test/" + echoBasis);

            /**
             * the Jackson JSON library provides helper methods which can directly
             * take a URL, perform the GET request convert the result to the specified class
             */
            TestItem response = new ObjectMapper().readValue(
                    new URL(baseUrl + "test/" + echoBasis), TestItem.class);

            /**
             * some error checking - only needed for the sample (if the JSON data is
             * not correct usually an exception is thrown)
             */
            if (! response.greeting().endsWith(echoBasis)){
                throw new RuntimeException("wrong echo returned");
            }

            System.out.println("The server responded as JSON-greeting: \n\n"
                    + response.greeting());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
