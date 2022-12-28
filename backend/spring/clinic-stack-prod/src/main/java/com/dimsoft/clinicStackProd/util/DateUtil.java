package com.dimsoft.clinicStackProd.util;


//import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    private static ThreadLocal formatDayMonthYearThread = new ThreadLocal() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "dd/MM/yyyy");
        }
    };

    private static ThreadLocal formatMonthYearThread = new ThreadLocal() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "MM/yyyy");
        }
    };

    private static ThreadLocal formatYearThread = new ThreadLocal() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "yyyy");
        }
    };

    private static ThreadLocal formatMonthThread = new ThreadLocal() {
        protected synchronized SimpleDateFormat initialValue() {
            return new SimpleDateFormat(
                    "MM");
        }
    };

    // todo make it thread save
    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static SimpleDateFormat getSimpleDateFormatDayMonthYear() {
        return (SimpleDateFormat) formatDayMonthYearThread.get();
    }

    private static SimpleDateFormat getSimpleDateFormatMonthYear() {
        return (SimpleDateFormat) formatMonthYearThread.get();
    }

    private static SimpleDateFormat getSimpleDateFormatYear() {
        return (SimpleDateFormat) formatYearThread.get();
    }

    private static SimpleDateFormat getSimpleDateFormatMonth() {
        return (SimpleDateFormat) formatMonthThread.get();
    }

    public static final Date parse(String date) {
        try {
            return getSimpleDateFormatDayMonthYear().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String formatDayMonthYear(Date date) /*throws ParseException*/ {  // todo
        return getSimpleDateFormatDayMonthYear().format(date);
    }

    public static final String formatMonthYear(Date date) /*throws ParseException*/ {  // todo
        return getSimpleDateFormatMonthYear().format(date);
    }

    public static final String formatYear(Date date) /*throws ParseException*/ {  // todo
        return getSimpleDateFormatYear().format(date);
    }

    public static final String formatMonth(Date date) /*throws ParseException*/ {  // todo
        return getSimpleDateFormatMonth().format(date);
    }

    public static String getMonth(Date dateOperation) {
        return DateUtil.formatMonth(dateOperation);
    }

    public static String getYear(Date dateOperation) {
        return DateUtil.formatYear(dateOperation);
    }

    /**
     * Calculer le nombre de jours entre deux dates.<br/>
     * Le résultat retourné est toujours positif et la date de début doit être supérieure à la date de fin.
     *
     * @param dateDebut La date de début non null
     * @param dateFin La date de fin non null
     * @return Un nombre positif correspondant à la différence de jours séparant les deux dates.
     * @throws IllegalArgumentException, si la date de début n'est pas supérieure à la date de fin.
     */
   /* public static int getDiffDay(@Nonnull Date dateDebut, @Nonnull Date dateFin) {
       *//* if (dateDebut.after(dateFin)) {
            throw new IllegalArgumentException("La date de début doit être supérieure à la date de fin");
        }*//*
        // Periode de temps entre deux dates en comptant le nombre de jours pour calculer la différence en nombre de jours.
        Period period = new Period(new DateTime(dateDebut), new DateTime(dateFin), PeriodType.days());
        // La comparaison retourne un nombre positif.
        return period.getDays();
    }*/



}