package application.outros.cardapio;

import application.system.restaurante.*;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class CardapioApp extends Application {

    private static final String BG_APP        = "#121417";
    private static final String BG_PANEL      = "#1B1F24";
    private static final String BG_CARD       = "#232A31";
    private static final String BG_CARD_HOVER = "#2C353D";
    private static final String ACCENT        = "#F5A524";
    private static final String ACCENT_HOVER  = "#FFB74A";
    private static final String TEXT_PRIMARY  = "#F1F1F1";
    private static final String TEXT_MUTED    = "#B0B8C1";
    private static final String DANGER        = "#E5484D";

    private final Cardapio cardapio = new Cardapio();

    private final ObservableList<ItemPedido> pedido = FXCollections.observableArrayList();
    private final SimpleDoubleProperty total = new SimpleDoubleProperty(0.0);

    // --- INÍCIO DA APLICAÇÃO ---
    @Override
    public void start(Stage stage) {
        popularCardapio();

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BG_APP + ";");
        root.setPadding(new Insets(16));

        root.setTop(criarHeader());
        root.setCenter(criarCentro());
        root.setRight(criarPainelPedido());

        Scene scene = new Scene(root, 1180, 720);
        stage.setTitle("Cardápio Digital - Restaurante");
        stage.setScene(scene);
        stage.setMinWidth(980);
        stage.setMinHeight(620);
        stage.show();
    }

    // --- CABEÇALHO ---
    private HBox criarHeader() {
        Label lblTitulo = new Label("🍽  Cardápio Digital");
        lblTitulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        lblTitulo.setTextFill(Color.web(TEXT_PRIMARY));

        Label lblSubtitulo = new Label("Escolha seus itens favoritos");
        lblSubtitulo.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
        lblSubtitulo.setTextFill(Color.web(TEXT_MUTED));

        VBox vbxTextos = new VBox(2, lblTitulo, lblSubtitulo);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(vbxTextos, spacer);
        header.setPadding(new Insets(4, 8, 16, 8));
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    // --- PAINEL CENTRAL COM TABS DE CATEGORIAS ---
    private TabPane criarCentro() {
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle(
                "-fx-background-color: " + BG_PANEL + ";" +
                "-fx-tab-min-width: 140px;"
        );

        Tab tabTodos = new Tab("Todos", criarGridItens(cardapio.buscarDisponiveis()));
        tabPane.getTabs().add(tabTodos);

        for (CategoriaItem cat : CategoriaItem.values()) {
            List<ItemCardapio> itens = cardapio.buscarPorCategoria(cat)
                    .stream().filter(ItemCardapio::isDisponivel).toList();
            if (!itens.isEmpty()) {
                Tab tab = new Tab(cat.getDescricao(), criarGridItens(itens));
                tabPane.getTabs().add(tab);
            }
        }

        tabPane.getStylesheets().add("data:text/css," + String.join("",
                ".tab-pane { -fx-background-color: " + BG_PANEL + "; }",
                ".tab-pane .tab-header-area .tab-header-background { -fx-background-color: " + BG_APP + "; }",
                ".tab-pane .tab { -fx-background-color: " + BG_CARD + "; -fx-background-radius: 6 6 0 0; -fx-padding: 6 14; }",
                ".tab-pane .tab:selected { -fx-background-color: " + ACCENT + "; }",
                ".tab-pane .tab .tab-label { -fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-weight: bold; }",
                ".tab-pane .tab:selected .tab-label { -fx-text-fill: #121417; }"
        ).replace("#", "%23"));

        return tabPane;
    }

    // --- GRID DE ITENS DO CARDÁPIO ---
    private ScrollPane criarGridItens(List<ItemCardapio> itens) {
        TilePane tile = new TilePane();
        tile.setHgap(14);
        tile.setVgap(14);
        tile.setPadding(new Insets(16));
        tile.setPrefColumns(3);
        tile.setStyle("-fx-background-color: " + BG_PANEL + ";");

        for (ItemCardapio item : itens) {
            tile.getChildren().add(criarCardItem(item));
        }

        ScrollPane sp = new ScrollPane(tile);
        sp.setFitToWidth(true);
        sp.setStyle(
                "-fx-background: " + BG_PANEL + ";" +
                "-fx-background-color: " + BG_PANEL + ";" +
                "-fx-border-color: transparent;"
        );
        return sp;
    }

    // --- CARD DE ITEM DO CARDÁPIO ---
    private VBox criarCardItem(ItemCardapio item) {
        Label lblNome = new Label(item.getNome());

        lblNome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblNome.setTextFill(Color.web(TEXT_PRIMARY));
        lblNome.setWrapText(true);

        Label lblCategoria = new Label(item.getCategoria().getDescricao().toUpperCase());

        lblCategoria.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        lblCategoria.setTextFill(Color.web(ACCENT));

        Label lblDescricao = new Label(item.getDescricao().isBlank()
                ? "Sem descrição disponível."
                : item.getDescricao());

        lblDescricao.setFont(Font.font("Segoe UI", 12));
        lblDescricao.setTextFill(Color.web(TEXT_MUTED));
        lblDescricao.setWrapText(true);
        lblDescricao.setMaxHeight(60);

        Label lblDetalhes = new Label(detalhesCurto(item));

        lblDetalhes.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 11));
        lblDetalhes.setTextFill(Color.web(TEXT_MUTED));
        lblDetalhes.setWrapText(true);

        Label lblPreco = new Label(String.format("R$ %.2f", item.calcularPrecoFinal()));

        lblPreco.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        lblPreco.setTextFill(Color.web(ACCENT));

        Label lblTempo = new Label("⏱ " + item.getTempoPreparo() + " min");

        lblTempo.setFont(Font.font("Segoe UI", 11));
        lblTempo.setTextFill(Color.web(TEXT_MUTED));

        HBox rodape = new HBox(lblPreco, spacerH(), lblTempo);
        rodape.setAlignment(Pos.CENTER_LEFT);

        Button btnAdicionar = new Button("+ Adicionar");

        btnAdicionar.setMaxWidth(Double.MAX_VALUE);
        btnAdicionar.setStyle(estiloBotaoAccent());
        btnAdicionar.setOnMouseEntered(e -> btnAdicionar.setStyle(estiloBotaoAccentHover()));
        btnAdicionar.setOnMouseExited(e -> btnAdicionar.setStyle(estiloBotaoAccent()));
        btnAdicionar.setOnAction(e -> adicionarAoPedido(item));

        VBox card = new VBox(6, lblCategoria, lblNome, lblDescricao, lblDetalhes, rodape, btnAdicionar);

        card.setPadding(new Insets(14));
        card.setPrefWidth(260);
        card.setPrefHeight(260);
        card.setStyle(estiloCard(BG_CARD));
        card.setOnMouseEntered(e -> card.setStyle(estiloCard(BG_CARD_HOVER)));
        card.setOnMouseExited(e -> card.setStyle(estiloCard(BG_CARD)));

        return card;
    }

    // --- DETALHES CURTOS PARA EXIBIÇÃO NO CARD ---
    private String detalhesCurto(ItemCardapio item) {
        if (item instanceof Prato p) {
            return "Ingrediente: " + p.getIngredientePrincipal()
                    + "  •  Tamanho: " + p.getTamanho().getDescricao()
                    + (p.isVegetariano() ? "  •  Veg" : "")
                    + (p.isSemGluten()   ? "  •  Sem glúten" : "");
        } else if (item instanceof Bebida b) {
            return b.getVolumeMl() + "ml  •  " + (b.isGelada() ? "Gelada" : "Natural")
                    + (b.isAlcoolica() ? String.format("  •  Álcool %.1f%%", b.getTeorAlcoolico()) : "");
        } else if (item instanceof Entrada e) {
            return (e.isParaCompartilhar() ? "Para compartilhar" : "Individual")
                    + "  •  Serve " + e.getPorcoes();
        } else if (item instanceof Sobremesa s) {
            return "Sabor: " + s.getSabor() + "  •  " + s.getCalorias() + " kcal"
                    + (s.isContemLactose() ? "  •  Lactose" : "")
                    + (s.isContemNozes()   ? "  •  Nozes" : "");
        } else if (item instanceof Acompanhamento a) {
            return "Tipo: " + a.getTipo()
                    + (a.isAdicionalGratuito() ? "  •  Grátis como adicional" : "");
        }
        return "";
    }

    // --- PAINEL DE PEDIDO ---
    private VBox criarPainelPedido() {
        Label titulo = new Label("  Seu Pedido");
        titulo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        titulo.setTextFill(Color.web(TEXT_PRIMARY));

        ListView<ItemPedido> lista = new ListView<>(pedido);
        lista.setStyle(
                "-fx-control-inner-background: " + BG_CARD + ";" +
                "-fx-background-color: " + BG_CARD + ";" +
                "-fx-background-radius: 8;"
        );
        lista.setPlaceholder(new Label("Nenhum item adicionado."));
        ((Label) lista.getPlaceholder()).setTextFill(Color.web(TEXT_MUTED));

        lista.setCellFactory(v -> new ListCell<>() {
            @Override
            protected void updateItem(ItemPedido ip, boolean empty) {
                super.updateItem(ip, empty);
                if (empty || ip == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("-fx-background-color: " + BG_CARD + ";");
                } else {
                    Label lblNome = new Label(ip.item.getNome());
                    lblNome.setTextFill(Color.web(TEXT_PRIMARY));
                    lblNome.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));

                    Label lblSub = new Label(String.format("%dx  •  R$ %.2f",
                            ip.quantidade, ip.item.calcularPrecoFinal() * ip.quantidade));
                    lblSub.setTextFill(Color.web(TEXT_MUTED));
                    lblSub.setFont(Font.font("Segoe UI", 11));

                    VBox vbxInfo = new VBox(2, lblNome, lblSub);

                    Button btnMenos = pequenoBotao("-");
                    btnMenos.setOnAction(e -> {
                        if (ip.quantidade > 1) {
                            ip.quantidade--;
                            pedido.set(pedido.indexOf(ip), ip); // refresh
                        } else {
                            pedido.remove(ip);
                        }
                        recalcularTotal();
                    });

                    Button btnMais = pequenoBotao("+");
                    btnMais.setOnAction(e -> {
                        ip.quantidade++;
                        pedido.set(pedido.indexOf(ip), ip);
                        recalcularTotal();
                    });

                    Button btnRemover = pequenoBotao("✕");

                    btnRemover.setStyle(estiloBotaoPequeno(DANGER));
                    btnRemover.setOnAction(e -> {
                        pedido.remove(ip);
                        recalcularTotal();
                    });

                    HBox hbxControles = new HBox(4, btnMenos, btnMais, btnRemover);
                    hbxControles.setAlignment(Pos.CENTER_RIGHT);

                    HBox hbxLinha = new HBox(10, vbxInfo, spacerH(), hbxControles);

                    hbxLinha.setAlignment(Pos.CENTER_LEFT);
                    hbxLinha.setPadding(new Insets(8, 10, 8, 10));
                    hbxLinha.setStyle("-fx-background-color: " + BG_PANEL + "; -fx-background-radius: 6;");

                    setGraphic(hbxLinha);
                    setText(null);
                    setStyle("-fx-background-color: " + BG_CARD + "; -fx-padding: 4 0;");
                }
            }
        });

        Label lblTotal = new Label();

        lblTotal.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        lblTotal.setTextFill(Color.web(ACCENT));
        lblTotal.textProperty().bind(total.asString("Total: R$ %.2f"));

        Button btnFinalizarPedido = new Button("Finalizar Pedido");

        btnFinalizarPedido.setMaxWidth(Double.MAX_VALUE);
        btnFinalizarPedido.setStyle(estiloBotaoAccent());
        btnFinalizarPedido.setOnMouseEntered(e -> btnFinalizarPedido.setStyle(estiloBotaoAccentHover()));
        btnFinalizarPedido.setOnMouseExited(e -> btnFinalizarPedido.setStyle(estiloBotaoAccent()));
        btnFinalizarPedido.setOnAction(e -> finalizarPedido());

        Button btnLimpar = new Button("Limpar");

        btnLimpar.setMaxWidth(Double.MAX_VALUE);
        btnLimpar.setStyle(estiloBotaoSecundario());
        btnLimpar.setOnAction(e -> {
            pedido.clear();
            recalcularTotal();
        });

        VBox vbxRodape = new VBox(10, lblTotal, btnFinalizarPedido, btnLimpar);
        vbxRodape.setPadding(new Insets(12, 0, 0, 0));

        VBox vbxPainel = new VBox(12, titulo, lista, vbxRodape);
        VBox.setVgrow(lista, Priority.ALWAYS);
        vbxPainel.setPadding(new Insets(16));
        vbxPainel.setPrefWidth(320);
        vbxPainel.setStyle(
                "-fx-background-color: " + BG_PANEL + ";" +
                "-fx-background-radius: 10;"
        );
        BorderPane.setMargin(vbxPainel, new Insets(0, 0, 0, 12));
        return vbxPainel;
    }

    // --- AÇÕES ---
    private void adicionarAoPedido(ItemCardapio item) {
        for (ItemPedido ip : pedido) {
            if (ip.item.getId() == item.getId()) {
                ip.quantidade++;
                pedido.set(pedido.indexOf(ip), ip);
                recalcularTotal();
                return;
            }
        }
        pedido.add(new ItemPedido(item, 1));
        recalcularTotal();
    }

    // --- RECALCULA O VALOR TOTAL DOS ITENS DO PEDIDO ---
    private void recalcularTotal() {
        double soma = pedido.stream()
                .mapToDouble(ip -> ip.item.calcularPrecoFinal() * ip.quantidade)
                .sum();
        total.set(soma);
    }

    // --- FINALIZA O PEDIDO E EXIBE UM RESUMO DOS ITENS ---
    private void finalizarPedido() {
        if (pedido.isEmpty()) {
            alerta(Alert.AlertType.WARNING, "Pedido vazio",
                    "Adicione ao menos um item ao pedido antes de finalizar.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        for (ItemPedido ip : pedido) {
            sb.append(String.format("• %dx %-25s R$ %.2f%n",
                    ip.quantidade,
                    ip.item.getNome(),
                    ip.item.calcularPrecoFinal() * ip.quantidade));
        }
        sb.append(String.format("%n%s%n", "─".repeat(42)));
        sb.append(String.format("TOTAL: R$ %.2f", total.get()));

        alerta(Alert.AlertType.INFORMATION, "Pedido confirmado", sb.toString());

        pedido.clear();
        recalcularTotal();
    }

    // --- CRIA E EXIBE UM ALERTA PERSONALIZADO ---
    private void alerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(msg);
        DialogPane pane = alerta.getDialogPane();

        pane.setStyle("-fx-background-color: " + BG_PANEL + ";");
        pane.lookupAll(".label").forEach(n -> n.setStyle("-fx-text-fill: " + TEXT_PRIMARY + ";"));
        alerta.showAndWait();
    }

    // --- ESTILOS UTILITÁRIOS ---
    private String estiloCard(String cor) {
        return "-fx-background-color: " + cor + ";" +
                "-fx-background-radius: 10;" +
                "-fx-border-radius: 10;" +
                "-fx-border-color: #2E3740;" +
                "-fx-border-width: 1;" +
                "-fx-cursor: hand;";
    }

    // --- ESTILOS DE BOTÕES ---
    private String estiloBotaoAccent() {
        return "-fx-background-color: " + ACCENT + ";" +
                "-fx-text-fill: #121417;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 8 14;";
    }

    // --- ESTILO DE BOTÃO ACCENT HOVER ---
    private String estiloBotaoAccentHover() {
        return estiloBotaoAccent().replace(ACCENT, ACCENT_HOVER);
    }

    // --- ESTILO DE BOTÃO SECUNDÁRIO ---
    private String estiloBotaoSecundario() {
        return "-fx-background-color: transparent;" +
                "-fx-text-fill: " + TEXT_MUTED + ";" +
                "-fx-border-color: " + TEXT_MUTED + ";" +
                "-fx-border-radius: 6;" +
                "-fx-background-radius: 6;" +
                "-fx-cursor: hand;" +
                "-fx-padding: 6 14;";
    }

    // --- ESTILO DE BOTÃO PEQUENO ---
    private String estiloBotaoPequeno(String cor) {
        return "-fx-background-color: " + cor + ";" +
                "-fx-text-fill: #121417;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 4;" +
                "-fx-min-width: 28; -fx-min-height: 28;" +
                "-fx-cursor: hand;";
    }

    // --- BOTÃO PEQUENO COM TEXTO ---
    private Button pequenoBotao(String txt) {
        Button btn = new Button(txt);
        btn.setStyle(estiloBotaoPequeno(ACCENT));
        return btn;
    }

    // --- ESPAÇADOR HORIZONTAL ---
    private Region spacerH() {
        Region regiao = new Region();
        HBox.setHgrow(regiao, Priority.ALWAYS);
        return regiao;
    }

    // --- POPULAÇÃO DO CARDÁPIO ---
    private void popularCardapio() {
        // Entradas
        cardapio.adicionarItem(new Entrada(1, "Bruschetta",
                "Fatias de pão italiano com tomate, manjericão e azeite.", 22.90, 10, true, 2));
        cardapio.adicionarItem(new Entrada(2, "Bolinho de Bacalhau",
                "Porção com 6 unidades, crocantes por fora e macios por dentro.", 34.50, 15, true, 3));
        cardapio.adicionarItem(new Entrada(3, "Salada Caprese",
                "Tomate, muçarela de búfala, manjericão e azeite extra virgem.", 28.00, 8, false, 1));

        // Pratos principais
        cardapio.adicionarItem(new Prato(4, "Filé Mignon ao Molho Madeira",
                "Filé grelhado ao ponto, molho madeira e batatas rústicas.", 89.90, 25,
                "Filé Mignon", false, true, TamanhoPorcao.MEDIA));
        cardapio.adicionarItem(new Prato(5, "Risoto de Cogumelos",
                "Arroz arbóreo com mix de cogumelos frescos e parmesão.", 62.00, 20,
                "Arroz arbóreo", true, true, TamanhoPorcao.MEDIA));
        cardapio.adicionarItem(new Prato(6, "Salmão Grelhado",
                "Salmão grelhado com legumes salteados e purê de batata baroa.", 78.50, 22,
                "Salmão", false, true, TamanhoPorcao.MEDIA));
        cardapio.adicionarItem(new Prato(7, "Lasanha à Bolonhesa",
                "Camadas de massa fresca, molho bolonhesa e queijos gratinados.", 54.00, 30,
                "Massa fresca", false, false, TamanhoPorcao.GRANDE));

        // Acompanhamentos
        cardapio.adicionarItem(new Acompanhamento(8, "Batata Frita",
                "Porção generosa de batata frita crocante.", 18.00, 10, "Fritura", false));
        cardapio.adicionarItem(new Acompanhamento(9, "Arroz Branco",
                "Arroz soltinho preparado na hora.", 0.00, 8, "Cereal", true));
        cardapio.adicionarItem(new Acompanhamento(10, "Legumes Grelhados",
                "Mix de legumes frescos grelhados com ervas.", 14.50, 12, "Vegetal", false));

        // Sobremesas
        cardapio.adicionarItem(new Sobremesa(11, "Petit Gateau",
                "Bolinho quente de chocolate com sorvete de creme.", 26.00, 12,
                420, true, false, "Chocolate"));
        cardapio.adicionarItem(new Sobremesa(12, "Cheesecake de Frutas Vermelhas",
                "Cheesecake cremoso com calda de frutas vermelhas.", 22.50, 5,
                380, true, false, "Frutas Vermelhas"));
        cardapio.adicionarItem(new Sobremesa(13, "Pudim de Leite",
                "Pudim tradicional com calda de caramelo.", 16.00, 5,
                310, true, false, "Baunilha"));

        // Bebidas
        cardapio.adicionarItem(new Bebida(14, "Suco de Laranja",
                "Suco natural feito na hora.", 12.00, 4, 400, true, false, 0));
        cardapio.adicionarItem(new Bebida(15, "Refrigerante Lata",
                "Coca-Cola, Guaraná ou Sprite.", 8.00, 2, 350, true, false, 0));
        cardapio.adicionarItem(new Bebida(16, "Água Mineral",
                "Com ou sem gás.", 5.00, 1, 500, true, false, 0));

        // Bebidas alcoólicas
        cardapio.adicionarItem(new Bebida(17, "Chopp Pilsen",
                "Tulipa de chopp gelado.", 14.00, 3, 300, true, true, 4.8));
        cardapio.adicionarItem(new Bebida(18, "Taça de Vinho Tinto",
                "Vinho tinto seco da casa.", 32.00, 3, 150, false, true, 12.5));
        cardapio.adicionarItem(new Bebida(19, "Caipirinha",
                "Cachaça, limão, açúcar e gelo.", 22.00, 5, 300, true, true, 15.0));
    }

    public static void main(String[] args) {
        launch(args);
    }
}

