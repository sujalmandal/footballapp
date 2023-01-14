package s.m.learning.footballapp.security;

import com.nimbusds.jwt.JWTClaimsSet;

import java.util.List;

import static s.m.learning.footballapp.security.JWTHelper.CLAIM_ATTR_ROLES;

public class ThreadLocalUserContext {
    private static ThreadLocal<CurrentUser> currentUserThreadLocal;

    public static List<String> getRoles(){
        return currentUserThreadLocal.get().roles;
    }

    public static String getUserName(){
        return currentUserThreadLocal.get().username;
    }

    public static void set(JWTClaimsSet verifiedClaims){
        currentUserThreadLocal = new ThreadLocal<>();
        String username = verifiedClaims.getSubject();
        final List<String> roles = (List<String>) verifiedClaims.getClaims().get(CLAIM_ATTR_ROLES);
        currentUserThreadLocal.set(new CurrentUser(username, roles));
    }

    public static void remove(){
        currentUserThreadLocal.remove();
    }

    private static class CurrentUser{
        String username;
        List<String> roles;

        public CurrentUser(String username, List<String> roles) {
            this.username = username;
            this.roles = roles;
        }
    }
}
