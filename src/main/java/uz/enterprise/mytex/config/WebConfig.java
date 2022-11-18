package uz.enterprise.mytex.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uz.enterprise.mytex.security.interceptor.AuthorizationInterceptor;
import uz.enterprise.mytex.service.PropertyService;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuthorizationInterceptor authorizationInterceptor;
    private final PropertyService propertyService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor);
    }

    @Bean(name = "minioClient")
    public MinioClient minioClient(){
        return MinioClient
                .builder()
                .endpoint(propertyService.getMinioEndpoint())
                .credentials(propertyService.getMinioAccessKey(), propertyService.getMinioSecretKey())
                .httpClient(new OkHttpClient())
                .build();
    }

    @Bean
    public GitHub gitHub() throws Exception {
        return new GitHubBuilder()
                .withOAuthToken(propertyService.getGithubToken())
                .build();
    }
}
