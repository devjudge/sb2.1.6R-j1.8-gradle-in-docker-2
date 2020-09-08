package org.codejudge.sb.common.util.security;

import org.codejudge.sb.common.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil
{
    public static User getUser()
    {
        User user = null;

        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null)
        {
            Authentication auth = ctx.getAuthentication();
            if (auth != null)
            {
                Object principal = auth.getPrincipal();
                if (principal != null && principal instanceof User)
                {
                    user = (User) principal;
                }
            }
        }

        return user;
    }
}