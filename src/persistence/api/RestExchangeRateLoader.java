package persistence.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import model.Currency;
import model.ExchangeRate;
import persistence.ExchangeRateLoader;

public class RestExchangeRateLoader implements ExchangeRateLoader{

    @Override
    public ExchangeRate load(Currency from, Currency to) {
        try {
            return new ExchangeRate(from, to, read(from.getCode(), to.getCode()));
        } catch (IOException ex) {
            return null;
        }
    }

    private double read(String from, String to) throws IOException {
        String line = read(new URL("https://raw.githubusercontent.com/fawazahmed0/currency-api/1/latest/currencies/"+from+"/"+to+".json"));
        String line2 = line.substring(line.indexOf(",")+13, line.indexOf("}")-1);
        return Double.parseDouble(line2);
    }
    
    private String read(URL url) throws IOException{
        InputStream is = url.openStream();
        byte[] buffer = new byte[1024];
        int length = is.read(buffer);
        return new String(buffer, 0, length);
    }
    
}
