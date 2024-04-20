package br.com.alura.consultfipe.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);

    <T>List<T> obterlista (String json, Class<T> classe);
}
