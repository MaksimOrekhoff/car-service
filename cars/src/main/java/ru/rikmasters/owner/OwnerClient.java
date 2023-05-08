package ru.rikmasters.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;


@Service
public class OwnerClient extends BaseClient {
    private final String PATH = "/owners";
    @Autowired
    public OwnerClient(@Value("${owners-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(SimpleClientHttpRequestFactory.class)
                        .build()
        );
    }


    public void setOwner(Long ownerId, Long carId) {
        setOwner(PATH + "/" + ownerId + "/" + carId);
    }

    public void deleteAuto(Long ownerId) {
        delete(PATH + "/deleteCar/" + ownerId);
    }

    public void changeOwner(Long ownerId, Long carId) {
        patch(PATH + "/changeOwner/" + ownerId + "/" + carId);
    }
}
