package org.codejudge.sb.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateEditor extends F58PropertyEditorSupport {

    private static final Logger log = LoggerFactory.getLogger(CustomDateEditor.class);

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(new Date(Long.parseLong(text)));
        } catch (NumberFormatException nfe) {
            for (String format : new String[]{"dd/MM/yyyy HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "EEE dd/MM/yyyy HH:mm:ss",
                    "EEE dd/MM/yyyy HH:mm:ss z", "dd/MM/yyyy", "dd/MM/yy"}) {
                try {
                    setValue(new SimpleDateFormat(format).parse(text));
                    return;
                } catch (Exception e) {
                    if (log.isDebugEnabled()) {
                        log.debug("failed parsing '" + text + "' as date because: " + e.getMessage());
                    }
                }
            }
        }
    }
}
