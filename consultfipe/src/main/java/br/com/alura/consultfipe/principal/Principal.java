package br.com.alura.consultfipe.principal;

import br.com.alura.consultfipe.models.Dados;
import br.com.alura.consultfipe.models.DadosVeiculo;
import br.com.alura.consultfipe.models.Modelos;
import br.com.alura.consultfipe.service.ConnectAPI;
import br.com.alura.consultfipe.service.ConverterDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scannerLeitura = new Scanner(System.in);
    private ConnectAPI connect = new ConnectAPI();
    private ConverterDados convertedor = new ConverterDados();

    private final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){

        System.out.println(
                "\ncarros" +
                "\nmotos" +
                "\ncaminhoes" +
                "\nQual opcao deseja escolher? ");

        String menuMarcas = scannerLeitura.nextLine().toLowerCase();
        var jsonMarcas = connect.obterDados(ENDERECO + menuMarcas.replace(" ", "+") + "/marcas/");
        var marcas = convertedor.obterlista(jsonMarcas, Dados.class);

        marcas.stream().sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Que marca de veiculo gostaria de selecionar? [Codigo]");
        var menuModelos = scannerLeitura.nextLine();

        var jsonModelo = connect.obterDados(ENDERECO + menuMarcas + "/marcas/" + menuModelos + "/modelos");
        var modeloLista = convertedor.obterDados(jsonModelo, Modelos.class);

        modeloLista.modelos().stream().sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite um trecho para filtrar: ");
        var menuCarro = scannerLeitura.nextLine();

        List<Dados> modeloFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(menuCarro.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos Filtrados");
        modeloFiltrados.forEach(System.out::println);

        System.out.println("Digite por favor o codigo do modelo para buscar os valores de avaliacao");
        var codigoModelo = scannerLeitura.nextLine();

        var jsonCarro = connect.obterDados(ENDERECO + menuMarcas + "/marcas/" + menuModelos + "/modelos/" +
                codigoModelo + "/anos");
        List<Dados> anos = convertedor.obterlista(jsonCarro, Dados.class);
        List<DadosVeiculo> dadosVeiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = ENDERECO + menuMarcas + "/marcas/" + menuModelos + "/modelos/" +
                    codigoModelo + "/anos/" + anos.get(i).codigo();

            var jsonAnos = connect.obterDados(enderecoAnos);
            DadosVeiculo veiculo = convertedor.obterDados(jsonAnos, DadosVeiculo.class);
            dadosVeiculos.add(veiculo);

        }

        System.out.println("\nTodos os veiculos filtrados com avaliacoes por ano: ");
        dadosVeiculos.forEach(System.out::println);


    } //exibeMenu

}
