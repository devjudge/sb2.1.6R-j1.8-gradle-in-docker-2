package org.codejudge.sb.common.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;


/**
 * This singleton class provides globally unique ids.
 * Usually, just get a handle with IdManager.getIdManger() and call getUniqueId().
 * IdManager.java,v 1.10 2007/07/11 20:34:41 tgee Exp
 */
public class IdManager
{
    //FYI: The new epoch June 29, 2016 at about 9:20am
    @SuppressWarnings({ "FieldCanBeLocal" })
    private static long millisToNewEpoch = 1467210000000L;
    private static long maxPerMilli = 100000;
    private static AtomicLong counter = new AtomicLong();
    private static long longIpSuffix = 0L;

    private static volatile IdManager manager = null;

    private IdManager()
    {
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            String host = localhost.getHostAddress();
            IdManager.longIpSuffix = Long.parseLong(host.substring(host.lastIndexOf(".") + 1), 16);
        }
        catch (UnknownHostException e)
        {
            //eat it!
        }
    }

    /**
     * Returns reference to the singleton IdManager.
     */
    public static IdManager getIdManager()
    {
        if (manager == null) {
            synchronized (IdManager.class) {
                if (manager == null) {
                    manager = new IdManager();
                }
            }
        }
        return manager;
    }

    /**
     * Returns a globally unique ID String from the ID cache.
     */
    public String getUniqueId()
    {
        return getId();
    }

    /**
     * Returns a globally unique BigDecimal from the ID cache.
     * This is only used by al_scale_factor and al_scale_factor_template, which aren't used in production.
     */
    @Deprecated
    public BigDecimal getUniqueBigDecimalId()
    {
        return Base62Encoder.decodeBase62ToBigDecimal(getId());
    }

    private String getId()
    {
        return  new Long((longIpSuffix + System.currentTimeMillis() - millisToNewEpoch) * maxPerMilli +
                         (counter.getAndIncrement() % maxPerMilli)).toString();
    }
}
