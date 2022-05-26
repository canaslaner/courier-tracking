package tr.com.migros.tracking.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import tr.com.migros.tracking.util.Constants;
import tr.com.migros.tracking.util.HttpRequestUtils;

import java.util.Optional;

// source of createdBy and lastModifiedBy
@Component
public class IpAddressAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(HttpRequestUtils.getClientIpByContext()
                .map(ip -> Constants.Security.ANONYMOUS_USER + "@" + ip)
                .orElse(Constants.Security.ANONYMOUS_USER));
    }
}
