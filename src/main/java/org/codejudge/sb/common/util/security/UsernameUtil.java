package org.codejudge.sb.common.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created with IntelliJ IDEA.
 * User: dpurdy
 * Date: 3/25/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsernameUtil
{
    public static String getUsername()
    {
        UserDetails user = null;
        String username = null;

        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null)
        {
            Authentication auth = ctx.getAuthentication();
            if (auth != null)
            {
                Object principal = auth.getPrincipal();
                if (principal != null && principal instanceof UserDetails)
                {
                    user = (UserDetails) principal;
                    username = user.getUsername();
                }
            }
        }

        return username;
    }
}
