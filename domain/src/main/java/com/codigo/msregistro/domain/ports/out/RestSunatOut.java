package com.codigo.msregistro.domain.ports.out;

import com.codigo.msregistro.domain.aggregates.response.ResponseReniec;

public interface RestSunatOut {
    ResponseReniec getInfoSunat(String numDoc);
}
