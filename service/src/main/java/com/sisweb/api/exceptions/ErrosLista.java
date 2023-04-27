package com.sisweb.api.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrosLista extends MensagemPadrao{

    private List<ErrosCampos> erros = new ArrayList<>();
}
