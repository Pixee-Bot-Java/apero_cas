package org.apereo.cas.oidc.claims;

import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.oidc.AbstractOidcTests;
import org.apereo.cas.oidc.OidcConstants;
import org.apereo.cas.services.ChainingAttributeReleasePolicy;
import org.apereo.cas.services.RegisteredServiceAttributeReleasePolicyContext;
import org.apereo.cas.services.util.RegisteredServiceJsonSerializer;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link OidcOpenIdScopeAttributeReleasePolicyTests}.
 *
 * @author Misagh Moayyed
 * @since 6.1.0
 */
@Tag("OIDC")
class OidcOpenIdScopeAttributeReleasePolicyTests extends AbstractOidcTests {
    @Test
    void verifyOperation() {
        val policy = new OidcOpenIdScopeAttributeReleasePolicy();
        assertEquals(OidcConstants.StandardScopes.OPENID.getScope(), policy.getScopeType());
        assertTrue(policy.getAllowedAttributes().isEmpty());

        val context = RegisteredServiceAttributeReleasePolicyContext.builder()
            .registeredService(CoreAuthenticationTestUtils.getRegisteredService())
            .service(CoreAuthenticationTestUtils.getService())
            .principal(CoreAuthenticationTestUtils.getPrincipal())
            .build();
        assertTrue(policy.determineRequestedAttributeDefinitions(context).isEmpty());
    }

    @Test
    void verifySerialization() {
        val appCtx = new StaticApplicationContext();
        appCtx.refresh();
        val policy = new OidcOpenIdScopeAttributeReleasePolicy();
        val chain = new ChainingAttributeReleasePolicy();
        chain.addPolicies(policy);
        val service = getOidcRegisteredService();
        service.setAttributeReleasePolicy(chain);
        val serializer = new RegisteredServiceJsonSerializer(appCtx);
        val json = serializer.toString(service);
        assertNotNull(json);
        assertNotNull(serializer.from(json));
    }
}
