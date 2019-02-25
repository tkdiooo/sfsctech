package com.sfsctech.core.auth.sso.filter;

//import com.sfsctech.core.auth.base.sso.properties.SSOProperties;
//import com.sfsctech.core.auth.base.sso.util.SessionKeepUtil;
//import com.sfsctech.core.web.tools.cookie.CookieHelper;
import com.sfsctech.support.common.util.StringUtil;
import com.sfsctech.support.common.util.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class CertificateFilter
 *
 * @author 张麒 2019-2-20.
 * @version Description:
 */
public class CertificateFilter {//extends AbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(CertificateFilter.class);

//    @Autowired
//    private SSOProperties properties;

//    protected CertificateFilter(RequestMatcher matcher) {
//        super(matcher);
//    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
//        System.out.println(SecurityContextHolder.getContext().getAuthentication());
//        CookieHelper helper = CookieHelper.getInstance(request, response);
//        // 请求路径
//        String requestURI = request.getRequestURI();
//        logger.info("请求路径:{}", requestURI);
//        try {
//            String certificate;
//            logger.info("Session保持类型:{}", properties.getAuth().getSessionKeep());
//            // session凭证
//            if (properties.getAuth().getSessionKeep().equals(SSOProperties.SessionKeep.Cookie)) {
//                certificate = SessionKeepUtil.getCertificateByCookie(helper);
//            } else {
//                certificate = SessionKeepUtil.getCertificateByHeader(request);
//            }
//            if (StringUtil.isBlank(certificate)) {
//                throw new AuthenticationCredentialsNotFoundException("Access Certificate is not provided");
//            }
//            logger.info("session凭证:{}", certificate);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(certificate, null);
//            return getAuthenticationManager().authenticate(authentication);
//        } catch (Exception e) {
//            logger.error("异常:{}", ThrowableUtil.getRootMessage(e), e);
//            throw new AuthenticationCredentialsNotFoundException(ThrowableUtil.getRootMessage(e), e);
//        }
//    }
}
