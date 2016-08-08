import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;

import javax.imageio.IIOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by joydeb barman on 30/7/16.
 */
public class WeatherController extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IIOException {
        response.setContentType("text/html");
        try {
            PrintWriter out = response.getWriter();
            String s = request.getParameter("cityname").trim();
            float temp = 0;
            int flag=1;
            try {

                OwmClient owmClient = new OwmClient();
                WeatherStatusResponse statusResponse = owmClient.currentWeatherAtCity(s);
                if (statusResponse.hasWeatherStatus()) {
                    WeatherData weather = statusResponse.getWeatherStatus().get(0);
                    temp = weather.getTemp() - 273.15f;
                    flag=0;
                    //System.out.println(statusResponse);
                    // System.out.println(weather.);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(flag==0){
                out.println("<html><body bgcolor=\"#F5F5DC\">");
                out.println("<center>");
                out.println("<br><br><h1>" + "Current Temperature of  is:" + "</h1>");
                out.println("<h2>" + temp + "&deg;C"+"</h2>");
                out.println("</center>");
                out.println("</html></body>");

            }
            else{

                out.println("<html><body bgcolor=\"#F5F5DC\">");
                out.println("<center>");
                out.println("<br><br><br><h2>" + "Invalid city,<br> please check the city name" + "</h2>");
                out.println("</center>");
                out.println("</html></body>");

            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
