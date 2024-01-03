package backend.adapters.driving.transformer;


import backend.adapters.driving.exception.DataTypeNotSupportedException;
import backend.domain.model.enums.AppointmentReply;
import backend.domain.model.enums.SessionType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DataFormatter {

    public static String formatSessionType(SessionType sessionType) {
        return switch (sessionType) {
            case ONLINE -> "Online";
            case IN_PERSON -> "In person";
            default -> throw new DataTypeNotSupportedException("Not supported Session type " + sessionType);
        };
    }

    public static String formatAppointmentReply(AppointmentReply appointmentReply) {
        return switch (appointmentReply) {
            case PENDING -> "PENDING";
            case ACCEPTED -> "ACCEPTED";
            case DECLINED -> "DECLINED";
            case CANCELED -> "CANCELED";
            default -> throw new DataTypeNotSupportedException("Not supported Appointment Reply type " + appointmentReply);
        };
    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        return date.format(dateFormatter);
    }

    public static LocalDate stringToDate(String date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(date.contains(".")) {
            dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        }
        return LocalDate.parse(date, dateFormatter);
    }

    public static String timeToString(LocalTime time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(timeFormatter);
    }

    public static LocalTime stringToTime(String time) {
        return LocalTime.parse(time);
    }

}

