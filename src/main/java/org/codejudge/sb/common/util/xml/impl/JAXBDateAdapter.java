package org.codejudge.sb.common.util.xml.impl;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * By default, JAXB expects the date format to be: yyyy-MM-dd'T'HH:mm:ss (i.e. compatible with ISO-8601).
 *
 * This is an adapter, which accepts any date format that our app supports (see: {@link F58DateUtils} for supported
 * formats).
 *
 * User: rmarquez Date: 3/3/12 Time: 1:58 PM
 */
public class JAXBDateAdapter extends XmlAdapter<String, Date> {

    @Override
    public String marshal(Date date) throws Exception {
        return date.toString();
    }

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
    }
}

