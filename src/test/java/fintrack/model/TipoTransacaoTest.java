package fintrack.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TipoTransacaoTest {

    @Test
    void deveTerRotuloParaExibicao() {
        assertEquals("Entrada", TipoTransacao.ENTRADA.getRotulo());
        assertEquals("Saída", TipoTransacao.SAIDA.getRotulo());
    }
}