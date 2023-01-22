package datemanipulation;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class DiasUteisJavaTime {

    public static void main(String[] args) {
        try {
            LocalDateTime dataSolicitacao = LocalDateTime.now(); // Variável a ser recebida como parâmetro da função
            int diasAcrescentados = 5; // Variável a ser recebida como parâmetro da função
            int horaEncerramento = 17; // Variável opcional que define o horário útil de fechamento do dia, definir default = 0

            LocalDate dataCalculada = adicionaDiasUteis(dataSolicitacao, diasAcrescentados, horaEncerramento);

            System.out.println();
            System.out.println("Data Calculada: " + dataCalculada);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static LocalDate adicionaDiasUteis(LocalDateTime dataSolicitacao, int diasAcrescentados, int horaEncerramento) {
        System.out.println();
        System.out.println("Data Atual: " + dataSolicitacao);

        // Inicializa uma lista de feriados e add feriados apenas para teste
        List<LocalDate> feriados = new ArrayList<>();
        LocalDate feriado1 = Year.of(2023).atMonth(Month.JANUARY).atDay(23);
        LocalDate feriado2 = Year.of(2023).atMonth(Month.JANUARY).atDay(25);
        feriados.add(feriado1);
        feriados.add(feriado2);

        // TODO: Obter a lista de feriados via API e substituir a lista criada para o teste
        System.out.println("Lista de Feriados: " + feriados);

        // Considera um horário para o corte para o movimento do dia útil
        // Se horaEncerramento = 0 o dia útil considerou o movimento do dia anterior e não add mais 1 dia
        if (horaEncerramento != 0 && dataSolicitacao.getHour() >= horaEncerramento) {
            ++diasAcrescentados;
        }

        System.out.println("Dias Úteis Acrescentados: " + diasAcrescentados);

        // Realiza o cálculo da data útil considerando os parâmetros e regras informados
        while (diasAcrescentados > 0) {
            dataSolicitacao = dataSolicitacao.plusDays(1);
            DayOfWeek diaDaSemana = dataSolicitacao.getDayOfWeek();
            if (diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY
                    && !feriados.contains(dataSolicitacao.toLocalDate())) {
                --diasAcrescentados;
            }
        }

        return dataSolicitacao.toLocalDate();
    }

}
