package com.codigo.msregistro.infraestructure.adapters;

import com.codigo.msregistro.domain.aggregates.response.ResponseReniec;
import com.codigo.msregistro.domain.ports.out.RestSunatOut;
import com.codigo.msregistro.infraestructure.rest.client.ClienteReniec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestSunatAdapter implements RestSunatOut {

    private final ClienteReniec reniec;

    @Value("${token.api}")
    private String tokenApi;

    @Override
    public ResponseReniec getInfoSunat(String numDoc) {
        String authorization = "Bearer " + tokenApi;
        ResponseReniec responseSunat = reniec.getInfoReniec(numDoc, authorization);
        return responseSunat;
    }
}
