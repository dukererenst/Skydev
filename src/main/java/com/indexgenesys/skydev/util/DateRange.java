/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexgenesys.skydev.util;



import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;

/**
 *
 * @author Edwin
 */
public class DateRange implements Serializable
{

    
    static DateRange getNextFromOrdinal(int ordinal)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Date fromDate;
    private Date toDate;
    private int year;
    private Period period;
    
    private String rangeDescription;
    
    public static DateRange sameDayRange(Date valueDate)
    {
        return new DateRange(valueDate, valueDate);
    }
    public static DateRange yearToDate(Date valueDate)
    {
        int year = DateTimeUtils.getYearInDate(valueDate);
        return new DateRange(DateTimeUtils.parseDate("0101"+year, "ddMMyyyy"), valueDate);
    }
    
    public void cleanTime()
    {
        fromDate = DateTimeUtils.removeTimeFromDate(fromDate);
        toDate = DateTimeUtils.removeTimeFromDate(toDate);
    }
    
    public Date previousDateOfFromDate()
    {
        if(fromDate == null)
        {
            return null;
        }
        
        return new DateTime(fromDate).minusDays(1).toDate();
    }
    
    
    public boolean validRangeSet()
    {
        return fromDate!= null && toDate !=null;
    }
    
    public DateRange()
    {
    }
    
    public DateRange(Period period)
    {
        updateWithPeriod(period);
    }
    
     private static Date getCalendarFromTime(int calField, int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(calField, value);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);

        return cal.getTime();
    }

    public static Date getEarliestTimeForDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);

        return cal.getTime();
    }

    private static Date getCalendarToTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR));
        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.get(Calendar.SECOND));

        return cal.getTime();
    }

    
    public DateRange(Date fromDate, Date toDate)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }
    
    public DateRange(DateRange dateRange)
    {
        fromDate = dateRange.fromDate;
        toDate = dateRange.toDate;
    }
    
    public DateRange(Period rangeOption, int year , Date fromDate, Date toDate)
    {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.period = rangeOption;
        this.year = year;
    }
    
    public void setDateRange(DateRange dateRange)
    {
        fromDate = dateRange.fromDate;
        toDate = dateRange.toDate;
    }
    
    public String getDateRangeNarration()
    {
        if(fromDate == null && toDate == null)
            return "";
        
        if(fromDate == null)
            return " Inception to "+DateTimeUtils.formatDate(toDate, "dd/MM/yyyy");
        
        if(toDate == null)
            return "From "+DateTimeUtils.formatDate(fromDate, "dd/MM/yyyy");
        
        String narration = DateTimeUtils.formatDate(fromDate, "dd/MM/yyyy")
                +" to " + DateTimeUtils.formatDate(toDate, "dd/MM/yyyy");
        
        
//        fromDate = DateTimeUtils.removeTimeFromDate(fromDate);
//        toDate = DateTimeUtils.removeTimeFromDate(toDate);
        
        if(fromDate.equals(toDate))
        {
            return DateTimeUtils.formatDate(fromDate, "dd/MM/yyyy");
        }
        
        
        return narration;
    }

    @Override
    public String toString()
    {
        return getDateRangeNarration();
    }

    public String getRangeDescription()
    {
        return rangeDescription;
    }

    public void setRangeDescription(String rangeDescription)
    {
        this.rangeDescription = rangeDescription;
    }

    
    
    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }
    
    public void updateWithPeriod(Period period)
    {
        if(period == null)
        {
            return;
        }
        setDateRange(getDateRange(period));
    }
    
    public static DateRange getDateRange(Period rangeOption)
    {
        return getDateRange(rangeOption, new Date());
    }
    
    
    public static DateRange getDateRange(Period rangeOption, Date valueDate)
     {
         if(valueDate == null)
         {
             valueDate = new Date();
         }
         
        DateRange dateRange = new DateRange();
        DateMidnight midnight = null;
        DateTime dateTime = new DateTime(valueDate);
//        int year = dateTime.getYear();
        
        switch (rangeOption) 
        {
//            case TODAY:
//                dateRange.setFromDate(new DateMidnight().toDate());
//                dateRange.setToDate(new DateTime().hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue().secondOfMinute().withMaximumValue().toDate());
//                break;
//            case YESTERDAY:
//                dateRange.setFromDate(new DateMidnight().minusDays(1).toDate());
//                dateRange.setToDate(new DateTime().minusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
//                break;
//            case TOMORROW:
//                dateRange.setFromDate(new DateMidnight().plusDays(1).toDate());
//                dateRange.setToDate(new DateTime().plusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
//                break;
//            case THIS_WEEK:
//                //for Ghana reporting, deduct 1 day so it starts from Sunday instead of Monday
//                dateRange.setFromDate(new DateMidnight().withDayOfWeek(1).minusDays(1).toDate());
//                dateRange.setToDate(new Date());
//                break;
//            case LAST_WEEK:
//                midnight = new DateMidnight();
//                //for Ghana reporting, deduct 1 day so it starts from Sunday instead of Monday
//                midnight = midnight.minusWeeks(1).withDayOfWeek(1).minusDays(1);
//                dateRange.setFromDate(midnight.toDate());
//                dateRange.setToDate(midnight.plusDays(6).toDate());
//                break;
//            case THIS_MONTH:
//                midnight = new DateMidnight();
//                midnight = midnight.withDayOfMonth(1);
//                dateRange.setFromDate(midnight.toDate());
//                dateRange.setToDate(new Date());
//                break;
//            case LAST_MONTH:
//                midnight = new DateMidnight();
//                midnight = midnight.minusMonths(1);
//                midnight = midnight.withDayOfMonth(1);
//
//                int interval = midnight.dayOfMonth().getMaximumValue();
//                dateRange.setFromDate(midnight.toDate());
//                dateRange.setToDate(midnight.withDayOfMonth(interval).toDate());
//                break;
//            case FIRST_QUARTER:
//                dateRange = getQuarterRange(1, year);
//                break;
//            case SECOND_QUARTER:
//                dateRange = getQuarterRange(2, year);
//                break;
//            case THIRD_QUARTER:
//                dateRange = getQuarterRange(3, year);
//                break;
//            case LAST_QUARTER:
//               dateRange = getQuarterRange(4, year);
//                break;
//            case THIS_YEAR:
//                midnight = new DateMidnight();
//                midnight = midnight.withMonthOfYear(1).withDayOfYear(1);
//                dateRange.setFromDate(midnight.toDate());
//                dateRange.setToDate(new Date());
//                break;
//            case LAST_YEAR:
//                midnight = new DateMidnight();
//                midnight = midnight.minusYears(1).withMonthOfYear(1).withDayOfYear(1);
//
//                dateRange.setFromDate(midnight.toDate());
//                dateRange.setToDate(midnight.plusMonths(11).withDayOfMonth(31).toDate());
//                break;
            
            case TODAY:
                dateRange.setFromDate(new DateMidnight(valueDate).toDate());
                dateRange.setToDate(new DateTime(valueDate).hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue().secondOfMinute().withMaximumValue().toDate());
                break;
            case YESTERDAY:
                dateRange.setFromDate(new DateMidnight(valueDate).minusDays(1).toDate());
                dateRange.setToDate(new DateTime(valueDate).minusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
                break;
            case TOMORROW:
                dateRange.setFromDate(new DateMidnight(valueDate).plusDays(1).toDate());
                dateRange.setToDate(new DateTime().plusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate());
                break;
            case THIS_WEEK:
                //for Ghana reporting, deduct 1 day so it starts from Sunday instead of Monday
                dateRange.setFromDate(new DateMidnight(valueDate).withDayOfWeek(1).minusDays(1).toDate());
                dateRange.setToDate(new Date());
                break;
            case THIS_WEEK_FUTURE:
                dateRange.setFromDate(valueDate);
                dateRange.setToDate(new DateMidnight(valueDate).dayOfWeek().withMaximumValue().toDate());
                break;
            case LAST_WEEK:
                midnight = new DateMidnight(valueDate);
                //for Ghana reporting, deduct 1 day so it starts from Sunday instead of Monday
                midnight = midnight.minusWeeks(1).withDayOfWeek(1).minusDays(1);
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(midnight.plusDays(6).toDate());
                break;
            case NEXT_WEEK:
                midnight = new DateMidnight(valueDate);
                //for Ghana reporting, deduct 1 day so it starts from Sunday instead of Monday
                midnight = midnight.plusWeeks(1).dayOfWeek().withMinimumValue();
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(new DateTime(valueDate).plusWeeks(1).dayOfWeek().withMaximumValue().hourOfDay().withMaximumValue().toDate());
                break;
            case THIS_MONTH:
                midnight = new DateMidnight(valueDate);
                midnight = midnight.withDayOfMonth(1);
                
                int interva = midnight.dayOfMonth().getMaximumValue();
                
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(midnight.withDayOfMonth(interva).toDate());
//                dateRange.setToDate(valueDate);
                break;
            case THIS_MONTH_PAST:
                midnight = new DateMidnight(valueDate);
                midnight = midnight.withDayOfMonth(1);
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(valueDate);
                break;
            case THIS_MONTH_FUTURE:
                midnight = new DateMidnight(valueDate);
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(new DateTime(valueDate).dayOfMonth().withMaximumValue().toDate());
                break;
            case LAST_MONTH:
                midnight = new DateMidnight(valueDate);
                midnight = midnight.minusMonths(1);
                midnight = midnight.withDayOfMonth(1);

                int interval = midnight.dayOfMonth().getMaximumValue();
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(midnight.withDayOfMonth(interval).toDate());
                break;
            case NEXT_MONTH:
                dateRange.setFromDate(new DateMidnight(valueDate).plusMonths(1).dayOfMonth().withMinimumValue().toDate());
                dateRange.setToDate(new DateTime(valueDate).plusMonths(1).dayOfMonth().withMaximumValue().hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue().toDate());
                break;
            case THIS_QUARTER:
//                DateRange currentQuarter = PeriodUtils.getQuarterDate(valueDate);
                dateRange = PeriodUtils.getQuarterDate(valueDate);
                if(dateRange.getToDate().after(valueDate))
                {
                    dateRange.setToDate(valueDate);
                }
//                dateRange.setFromDate(currentQuarter.getFromDate());
//                dateRange.setToDate(new Date());
                break;
            case LAST_QUARTER:
                DateRange previousQuarter = PeriodUtils.getPreviousQuarter(valueDate);
                dateRange.setFromDate(previousQuarter.getFromDate());
                dateRange.setToDate(previousQuarter.getToDate());
                break;
            case THIS_YEAR:
                midnight = new DateMidnight(valueDate);
                midnight = midnight.withMonthOfYear(1).withDayOfYear(1);
                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(valueDate);
                break;
            case THIS_YEAR_FUTURE:
                dateRange.setFromDate(valueDate);
                dateRange.setToDate(new DateTime(valueDate).monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue().hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue().toDate());
                break;
            case LAST_YEAR:
                midnight = new DateMidnight(valueDate);
                midnight = midnight.minusYears(1).withMonthOfYear(1).withDayOfYear(1);

                dateRange.setFromDate(midnight.toDate());
                dateRange.setToDate(midnight.plusMonths(11).withDayOfMonth(31).toDate());
                break;
            case NEXT_YEAR:               
                dateRange.setFromDate(new DateMidnight(valueDate).plusYears(1).monthOfYear().withMinimumValue().dayOfMonth().withMinimumValue().toDate());
                dateRange.setToDate(new DateTime(valueDate).plusYears(1).monthOfYear().withMaximumValue().dayOfMonth().withMaximumValue().hourOfDay().withMaximumValue().minuteOfDay().withMaximumValue().toDate());
                break;
                
                
                //extras
            case A_WEEK_AGO:
                dateRange.setToDate(dateTime.toDate());
                dateRange.setFromDate(dateTime.minusWeeks(1).toDate());
                break;
            case A_MONTH_AGO:
                dateRange.setToDate(dateTime.toDate());
                dateRange.setFromDate(dateTime.minusMonths(1).toDate());
                break;
        }
        return dateRange;
     }
    
    
    
    public String getDateRangeQuery(String field, DateRange dateRange)
    {
        String ss = "";
        try
        {
            if(dateRange != null)
            {
                if(dateRange.getFromDate() != null)
                {
                    ss += " AND " + field + " >= '" + DateTimeUtils.formatDate(dateRange.getFromDate(), "yyyy-MM-dd") + "' ";
                }
                if(dateRange.getToDate() != null)
                {
                    ss += " AND " + field + " <= '" + DateTimeUtils.formatDate(dateRange.getToDate(), "yyyy-MM-dd") + "' ";
                }
            }
        } 
        catch (Exception e) 
        {
        }
        return ss;
    }
    public String getDateRangeQuery(String field)
    {
        String ss = "";
        try
        {
            
                if(getFromDate() != null)
                {
                    ss += field + " >= '" + DateTimeUtils.formatDate(getFromDate(), "yyyy-MM-dd") + "' ";
                }
                if(getToDate() != null)
                {
                    if(fromDate != null)
                    {
                        ss += " AND ";
                    }
                    
                    ss += field + " <= '" + DateTimeUtils.formatDate(getToDate(), "yyyy-MM-dd") + "' ";
                }
                
        } 
        catch (Exception e) 
        {
        }
        return ss;
    }
    
    public static DateRange getQuarterRange(int quarter, int refYear)
    {
        DateMidnight refDate = new DateMidnight();
        
        if(refYear > 0)
        {
            refDate = refDate.withYear(refYear);
        }
        
        int add = 1;
        
        switch(quarter)
        {
            case 1 : add = 1; break;
            case 2 : add = 4; break;
            case 3 : add = 7; break;
            case 4 : add = 10; break;
        }
        
        refDate = refDate.withMonthOfYear(add).withDayOfMonth(1);
        Date startDate = refDate.toDate();
//        refDate = refDate.plusMonths(2).withDayOfMonth(refDate.dayOfMonth().getMaximumValue());
        refDate = refDate.withDayOfMonth(refDate.dayOfMonth().getMaximumValue()).plusMonths(2);
        Date endDate = new DateTime(refDate.toDateTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
        
        return new DateRange(startDate, endDate);
    }

    public boolean isWithinRange(Date valueDate)
    {
        if(valueDate == null)
        {
            return false;
        }
        
        Date date = DateTimeUtils.removeTimeFromDate(valueDate);
        
        if(date.equals(fromDate) || date.equals(toDate))
        {
            return true;
        }
        
        return date.after(fromDate) && date.before(toDate);
    }
    
    public DateRange previous()
    {
        DateRange previousDateRante = new DateRange();
        previousDateRante.setFromDate(fromDate);
        previousDateRante.setToDate(new DateTime(getToDate()).minusDays(1).toDate());
        
        return previousDateRante;
    }
    
    public Period getPeriod()
    {
        return period;
    }

    public void setPeriod(Period period)
    {
        this.period = period;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }
    
    
}
