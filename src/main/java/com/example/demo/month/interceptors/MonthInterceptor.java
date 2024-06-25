package com.example.demo.month.interceptors;

import com.example.demo.month.models.Month;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MonthInterceptor implements HandlerInterceptor {

    private List<Month> monthList = new ArrayList<>(Arrays.asList(
            new Month(1, "January", "Gennaio", "jan"),
            new Month(2, "February", "Febbraio", "Feb"),
            new Month(3, "March", "Marzo", "Mar"),
            new Month(4, "April", "Aprile", "Apr"),
            new Month(5, "May", "Maggio", "Mag"),
            new Month(6, "June", "Giugno", "Jun")));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String monthNumberString = request.getHeader("monthNumber");

        if (monthNumberString == null || monthNumberString.isEmpty()) {
            response.setStatus(400);
            return false;
        }

        int monthNumber = Integer.parseInt(monthNumberString);


        Month month = monthList.stream().
                filter(listedMonth -> listedMonth.getMonthNumber() == monthNumber)
                .findFirst().
                        orElseGet(() -> {
                    Month listedMonth = new Month();
                    listedMonth.setEnglishName("nope");
                    listedMonth.setItalianName("nope");
                    listedMonth.setGermanName("nope");
                    return listedMonth;
                });
        request.setAttribute("month", month);
        response.setStatus(200);
        return true;
    }
}
