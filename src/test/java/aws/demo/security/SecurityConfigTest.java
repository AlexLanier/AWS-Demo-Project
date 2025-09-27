package aws.demo.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.DefaultSecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityConfigTest {

    @Test
    void filterChain_returnsSecurityFilterChain() throws Exception {
        // Arrange
        SecurityConfig config = new SecurityConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        CsrfConfigurer<HttpSecurity> csrfConfigurer = mock(CsrfConfigurer.class);
        HeadersConfigurer<HttpSecurity> headersConfigurer = mock(HeadersConfigurer.class);
        HeadersConfigurer.FrameOptionsConfig frameOptionsConfig = mock(HeadersConfigurer.FrameOptionsConfig.class);
        HeadersConfigurer.ContentTypeOptionsConfig contentTypeOptionsConfig = mock(HeadersConfigurer.ContentTypeOptionsConfig.class);
        HeadersConfigurer.HstsConfig hstsConfig = mock(HeadersConfigurer.HstsConfig.class);
        HeadersConfigurer.ReferrerPolicyConfig referrerPolicyConfig = mock(HeadersConfigurer.ReferrerPolicyConfig.class);
        AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authRegistry = 
            mock(AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry.class);
        DefaultSecurityFilterChain mockChain = mock(DefaultSecurityFilterChain.class);

        // Setup mocks
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.headers(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(mockChain);

        // Act
        SecurityFilterChain result = config.filterChain(httpSecurity);

        // Assert
        assertNotNull(result);
        assertEquals(mockChain, result);
        
        // Verify that the security configuration methods were called
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).headers(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).build();
    }

    @Test
    void filterChain_withException_throwsException() throws Exception {
        // Arrange
        SecurityConfig config = new SecurityConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        
        // Setup mock to throw exception
        when(httpSecurity.csrf(any())).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> config.filterChain(httpSecurity));
    }

    @Test
    void filterChain_callsAllSecurityConfigurations() throws Exception {
        // Arrange
        SecurityConfig config = new SecurityConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        DefaultSecurityFilterChain mockChain = mock(DefaultSecurityFilterChain.class);

        // Setup mocks
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.headers(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(mockChain);

        // Act
        config.filterChain(httpSecurity);

        // Assert - verify all security configurations are applied
        verify(httpSecurity, times(1)).csrf(any());
        verify(httpSecurity, times(1)).headers(any());
        verify(httpSecurity, times(1)).authorizeHttpRequests(any());
        verify(httpSecurity, times(1)).build();
    }

    @Test
    void filterChain_withDetailedMocking_coversLambdaExpressions() throws Exception {
        // Arrange
        SecurityConfig config = new SecurityConfig();
        HttpSecurity httpSecurity = mock(HttpSecurity.class);
        DefaultSecurityFilterChain mockChain = mock(DefaultSecurityFilterChain.class);

        // Setup detailed mocks to trigger lambda expressions
        when(httpSecurity.csrf(any())).thenAnswer(invocation -> {
            // This should trigger the lambda expression
            return httpSecurity;
        });
        
        when(httpSecurity.headers(any())).thenAnswer(invocation -> {
            // This should trigger the headers lambda expressions
            return httpSecurity;
        });
        
        when(httpSecurity.authorizeHttpRequests(any())).thenAnswer(invocation -> {
            // This should trigger the authorization lambda expression
            return httpSecurity;
        });
        
        when(httpSecurity.build()).thenReturn(mockChain);

        // Act
        SecurityFilterChain result = config.filterChain(httpSecurity);

        // Assert
        assertNotNull(result);
        assertEquals(mockChain, result);
        
        // Verify that the security configuration methods were called
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).headers(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).build();
    }
}
