package datemanipulation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DiasUteisJavaUtil {

    public static void main(String[] args) {
        try {
            Date dataSolicitacao = new Date(); // Variável a ser enviada como parâmetro obrigatório
            int diasAcrescentados = 5; // Variável a ser enviada como parâmetro obrigatório
            int horaEncerramento = 17; // Variável opcional que define o horário útil de fechamento do dia, definir default = 0

            Date dataCalculada = adicionaDiasUteis(dataSolicitacao, diasAcrescentados, horaEncerramento);

            System.out.println();
            System.out.println("Data Calculada: " + dataCalculada);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Date adicionaDiasUteis (Date dataSolicitacao, int diasAcrescentados, int horaEncerramento) {
        Calendar dataInicial = Calendar.getInstance();
        dataInicial.setTime(dataSolicitacao);

        System.out.println();
        System.out.println("Data Atual: " + dataInicial.getTime());

        // Inicializa uma lista de feriados e add feriados apenas para teste
        List<Date> feriados = new ArrayList<>();
        Calendar feriado1 = Calendar.getInstance();
        Calendar feriado2 = Calendar.getInstance();
        feriado1.set(2023, Calendar.JANUARY, 23);
        feriado2.set(2023, Calendar.JANUARY, 25);
        feriados.add(trimHour(feriado1.getTime())); // É necessário garantir que a hora esteja truncada para posterior comparação de datas
        feriados.add(trimHour(feriado2.getTime())); // É necessário garantir que a hora esteja truncada para posterior comparação de datas

        // TODO: Obter a lista de feriados via API e substituir a lista criada para o teste
        System.out.println("Lista de Feriados: " + feriados);

        // Considera um horário para o corte para o movimento do dia útil
        // Se horaEncerramento = 0 o dia útil considerou o movimento do dia anterior e não add mais 1 dia
        if (horaEncerramento != 0 && dataInicial.get(Calendar.HOUR_OF_DAY) >= horaEncerramento) {
            ++diasAcrescentados;
        }

        System.out.println("Dias Úteis Acrescentados: " + diasAcrescentados);

        // Realiza o cálculo da data útil considerando os parâmetros e regras informados
        // A hora da dataInicial deve ser truncada para comparar com os valores da lista de feriados
        while (diasAcrescentados > 0) {
            dataInicial.add(Calendar.DAY_OF_MONTH, 1);
            int diaDaSemana = dataInicial.get(Calendar.DAY_OF_WEEK);
            if (diaDaSemana != Calendar.SATURDAY && diaDaSemana != Calendar.SUNDAY
                    && !feriados.contains(trimHour(dataInicial.getTime()))) { // É necessário garantir que a hora da dataInicial esteja truncada para comparar com os valores da lista
                --diasAcrescentados;
            }
        }

        return dataInicial.getTime();
    }

    private static Date trimHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return calendar.getTime();
    }

}
