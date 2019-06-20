import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.util.Map;

/**
 * Class GenIPTest
 *
 * @author 张麒 2019-6-18.
 * @version Description:
 */
public class GenIPTest {

    public static void main(String[] args) throws Exception {
        String url = "H:\\Tool Programming\\matomo\\地图\\GeoLite2-Country_20190319\\GeoLite2-Country.mmdb";
        FileInputStream database = new FileInputStream(url);
        DatabaseReader reader = new DatabaseReader.Builder(database).build();
        String ip = "211.23.73.209";
        InetAddress ipAddress = InetAddress.getByName(ip);
        CountryResponse response = reader.country(ipAddress);
        Country country = response.getCountry();
        String name = country.getName();
        Integer nameId = country.getGeoNameId();
        Integer confidence = country.getConfidence();
        Map<String, String> names = country.getNames();
        for (Map.Entry<String, String> map : names.entrySet()) {
            System.out.println("key:" + map.getKey() + " , value:" + map.getValue());
        }
        String countryCode = country.getIsoCode();
        System.out.println("name : " + name + " , nameId : " + nameId + " , countryCode : " + countryCode + " , confidence : " + confidence);

    }
}
