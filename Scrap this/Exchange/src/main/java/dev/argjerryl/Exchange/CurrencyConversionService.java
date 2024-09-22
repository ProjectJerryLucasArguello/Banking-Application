package dev.argjerryl.Exchange;

import org.springframework.stereotype.Service;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
@RestController
@Service
public class CurrencyConversionService {
    private static final String BASE_API_URL = "https://v6.exchangerate-api.com/v6/41fe4a04165d1858835d9c40/latest/";

    // No-argument constructor (only one allowed)
    public CurrencyConversionService() {

    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) throws IOException {
        String apiUrl = BASE_API_URL + fromCurrency;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(apiUrl);
            String result = EntityUtils.toString(httpClient.execute(request).getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(result);

            JsonNode rateNode = jsonNode.path("conversion_rates").path(toCurrency);
            if (rateNode.isMissingNode()) {
                throw new IllegalArgumentException("Exchange rate not found for currency pair: " + fromCurrency + " to " + toCurrency);
            }

            return rateNode.decimalValue();
        } catch (IOException e) {
            throw new IOException("Failed to retrieve exchange rate from API", e);
        }
    }

    public BigDecimal convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) throws IOException {
        BigDecimal exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        return amount.multiply(exchangeRate);
    }
}


