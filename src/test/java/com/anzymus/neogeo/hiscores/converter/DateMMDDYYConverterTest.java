package com.anzymus.neogeo.hiscores.converter;

import java.util.Calendar;
import java.util.Date;
import junit.framework.Assert;
import org.junit.Test;

public class DateMMDDYYConverterTest {

    @Test
    public void should_format_correctly() {
        DateMMDDYYConverter converter = new DateMMDDYYConverter();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 7);
        cal.set(Calendar.MONTH, 2);
        cal.set(Calendar.YEAR, 2010);
        
        Date date = cal.getTime();
        
        String strDate =converter.getAsString(null, null, date);
        
        Assert.assertEquals("03/07/10", strDate);
    }
    
}
