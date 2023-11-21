package org.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;


@WebServlet(urlPatterns = "/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String timezone = req.getParameter("timezone");

        if (timezone == null){
            timezone = "UTC";
        }


        try {
            // Отримуємо поточний час в заданому часовому поясі

            ZoneId zone = ZoneId.of(timezone);
            ZonedDateTime zdt = ZonedDateTime.now(zone);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String time = zdt.format(formatter);

            // Відправляємо HTML сторінку з часом
            resp.setHeader("refresh", "1");
            resp.getWriter().println("<html><body><h1>" + time + "</h1></body></html>");

        } catch (DateTimeException e) {
            // Якщо переданий часовий пояс недійсний, повертаємо помилку

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("<html><body><h1>Invalid timezone</h1></body></html>");
        }

    }
}

