package org.apereo.cas.web.flow.actions;

import org.apereo.cas.authentication.device.MultifactorAuthenticationDeviceManager;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.web.flow.util.MultifactorAuthenticationWebflowUtils;
import org.apereo.cas.web.support.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * This is {@link DefaultMultifactorAuthenticationDeviceProviderAction}.
 *
 * @author Misagh Moayyed
 * @since 6.6.0
 */
@RequiredArgsConstructor
public class DefaultMultifactorAuthenticationDeviceProviderAction extends BaseCasWebflowAction
    implements MultifactorAuthenticationDeviceProviderAction {

    private final MultifactorAuthenticationDeviceManager multifactorAuthenticationDeviceManager;

    @Override
    protected Event doExecuteInternal(final RequestContext requestContext) {
        val authentication = WebUtils.getAuthentication(requestContext);
        val principal = authentication.getPrincipal();
        val accounts = multifactorAuthenticationDeviceManager.findRegisteredDevices(principal);
        val currentAccounts = MultifactorAuthenticationWebflowUtils.getMultifactorAuthenticationRegisteredDevices(requestContext);
        FunctionUtils.doIfNotNull(currentAccounts, __ -> accounts.addAll(currentAccounts));
        MultifactorAuthenticationWebflowUtils.putMultifactorAuthenticationRegisteredDevices(requestContext, accounts);
        return null;
    }
}
